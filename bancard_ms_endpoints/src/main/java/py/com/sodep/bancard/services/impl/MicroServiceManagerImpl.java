package py.com.sodep.bancard.services.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.api.microservices.BancardMSException;
import py.com.sodep.bancard.api.microservices.BancardTransactionProvider;
import py.com.sodep.bancard.api.microservices.ITransactionAware;
import py.com.sodep.bancard.domain.MicroServiceEntity;
import py.com.sodep.bancard.domain.MicroServiceProperty;
import py.com.sodep.bancard.dto.MicroServiceConverter;
import py.com.sodep.bancard.dto.MicroServiceDTO;
import py.com.sodep.bancard.repository.IMicroServiceRepository;
import py.com.sodep.bancard.services.MicroServiceManager;

/**
 * Clase encargada de levantar de forma dinámica los microservicios.
 * <p>
 * Obtiene la información de los microservicios de la base de datos y levanta la
 * clase Java correspondiente a cada uno.
 * <p>
 * Una vez levantado un microservicio, lo cachea dentro del manager en una
 * estructura de datos, de esta forma los mismos quedarán cargados en la memoria
 * del servidor y no hace falta volver a cargarlos cada vez que se quiera
 * re-usar un mismo MicroServicio.
 * <p>
 * Las operaciones de esta clase son ThreadSafe.
 *
 */
@Component
public class MicroServiceManagerImpl implements MicroServiceManager {

	private static final Logger logger = LoggerFactory.getLogger(MicroServiceManagerImpl.class);

	private static final String SERVICE_KEY_SEPARATOR = "_";

	@Autowired
	private IMicroServiceRepository microServiceRepository;

	@Autowired
	private BancardTransactionProvider transactionProvider;

	@Autowired
	private MicroServiceFactory microServiceFactory;
	
	private Map<String, MicroService> microServiceIdToMicroService = new ConcurrentHashMap<String, MicroService>();

	private Object lockReload = new Object();

	private String msJarLocation;
	
	@Autowired
	public MicroServiceManagerImpl(@Value("${ms.dynamic.jar.location}") String msJarLocation) {
		this.msJarLocation = msJarLocation;
	}
	
	public void setMsJarLocation(String msJarLocation) {
		this.msJarLocation = msJarLocation;
	}
	
	
	@PostConstruct
	public void init() {
		reload();
	}


	/**
	 * La información de cada microServicio se almacena en un
	 * {@link MicroServiceEntity}.
	 * <p>
	 * Este método obtiene todos los entities y carga las clases Java asociada a
	 * cada uno. Estos microservicios quedan cacheados en el manager.
	 */
	@Override
	public void reload() {
		// Este lock es para evitar dos reloads funcionando al mismo tiempo.

		synchronized (lockReload) {
			logger.debug("Loading Microservices");

			Iterable<MicroServiceEntity> services = microServiceRepository.findAllWithActiveState();

			Set<String> loadedServicesIds = microServiceIdToMicroService.keySet();
			ArrayList<String> servicesToDelete = new ArrayList<String>();
			for (String serviceId : loadedServicesIds) {
				String[] serviceArr = serviceId.split(SERVICE_KEY_SEPARATOR);
				MicroServiceEntity findOne = microServiceRepository.findByServiceNameAndProduct(serviceArr[0], serviceArr[1]);
				if (findOne == null) {
					servicesToDelete.add(serviceId);
				}
			}
			for (String serviceId : servicesToDelete) {
				logger.debug("Removing microservice " + serviceId);
				microServiceIdToMicroService.remove(serviceId);
			}

			for (MicroServiceEntity s : services) {
				logger.debug("Adding :" + s);
				MicroService microService = loadMicroService(s.getId());
				microServiceIdToMicroService.put(serviceMapKey(s), microService);

			}
			logger.info("All Microservices have been loaded");
		}
	}

	
	@Override
	public MicroService getMicroService(String serviceName, String product) {
		String serviceKey = serviceName + SERVICE_KEY_SEPARATOR + product;
		MicroService microService = microServiceIdToMicroService.get(serviceKey);
		if (microService == null) {
			throw new BancardMSException("El servicio pedido no está cargado en el Dispatcher: " + serviceKey);
		}
		return microService;
		
	}
	
	
	@Override
	public MicroService getOrLoadMicroService(String serviceName, String product) {
		MicroService microService = microServiceIdToMicroService.get(serviceMapKey(serviceName, product));
		if (microService == null) {
			microService = reloadMicroService(serviceName, product);
		}
		return microService;
		
	}
	
	@Override
	public MicroService reloadMicroService(String serviceName, String product) {
		MicroServiceEntity serviceEntity = microServiceRepository.findByServiceNameAndProduct(serviceName, product);
		if (serviceEntity == null) {
			throw new BancardMSException(
					"Could not find microservice: serviceName=" + serviceName + ", product=" + product);
		}

		if (!"A".equals(serviceEntity.getStatus())) {
			throw new BancardMSException(
					"Microservices: " + serviceName + ", product:" + product + " is not in active state");
		}

		MicroService microService = loadMicroService(serviceEntity.getId());
		microServiceIdToMicroService.put(serviceMapKey(serviceName, product), microService);
		return microService;
	}

	private String serviceMapKey(MicroServiceEntity entity) {
		return entity.getServiceName() + SERVICE_KEY_SEPARATOR + entity.getProduct();
	}
	
	private String serviceMapKey(String serviceName, String product) {
		return serviceName + SERVICE_KEY_SEPARATOR + product;
	}

	private MicroService loadMicroService(Long microServiceId) {

		try {
			MicroServiceEntity microServiceEntity = findMicroService(microServiceId);

			logger.debug("Loading: " + microServiceEntity);

			// #BANFACTURA-96 --------------------------------------------------------//
			// Se crea un classloader 'hijo'. cada vez que se llama a este método.
			// Eso puede traer problemas.
			// Ver comentario en: http://stackoverflow.com/questions/60764/how-should-i-load-jars-dynamically-at-runtime
			// TODO considerar ese problema más adelante.
			// Por ahora esto funciona OK.
			// Respuesta al problema de arriba:
			// No es un problema para nosotros por qué?, porque los objetos
			// que se cargan en el mapa, se cargan una sola vez. Y no
			// necesitamos que sean singleton.
			URL[] urls = getURLs(microServiceEntity.getClassName());
			URLClassLoader child = new URLClassLoader(urls, this.getClass().getClassLoader());
			
			Class<?> msClass = Class.forName(microServiceEntity.getClassName(), true, child);
			//--------------------------------------------------------------------------------------//
			
			Object msObj = msClass.newInstance();
			if (!(msObj instanceof BancardMS)) {
				// FIXME ¿Porque un runtimeException? Esto puede ser algo
				// esperado porque esta fuera de nuestro control.
				throw new BancardMSException("El micro service #" + microServiceId + " debe de implementar la interfaz "
						+ BancardMS.class.getName());
			}

			// los parametros de inicializacion para el microservicio
			HashMap<String, String> initParams = new HashMap<String, String>();
			for (MicroServiceProperty msProperty : microServiceEntity.getParams()) {
				initParams.put(msProperty.getPropertyName(), msProperty.getValue());
			}

			
			if (msObj instanceof ITransactionAware) {
				// es un microservicio que necesita acceso a la tabla de
				// transacciones para almacenamiento de pagos
				((ITransactionAware) msObj).setBancardTransactionProvider(microServiceEntity.getServiceName(), microServiceEntity.getProduct(), transactionProvider);
			}
			
			BancardMS clientDriver = (BancardMS) msObj;
			clientDriver.initialize(initParams);
			MicroService microService = microServiceFactory.make(clientDriver);
			microService.initialize(initParams);
			
			// Esto debería permitir borrar un JAR cargado en la memoria del Dispatcher
			//child.close();
			
			// TODO para poder borrar un JAR cargado de forma dinamica.
			// Guardar el child en el microService
			// agregar un metodo  unload() que llame a close
			// y elimine el microservice del cache del Manager
			// Este metodo se llamaria solamente en caso de
			// que el SO no te deje borrar el JAR de forma manual.
			
			return microService;
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new BancardMSException("Unable to load IBancardMS microservice with id=" + microServiceId, e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new BancardMSException("Unable to load IBancardMS microservice with id=" + microServiceId, e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new BancardMSException("Unable to load IBancardMS microservice with id=" + microServiceId, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new BancardMSException("Unable to load IBancardMS microservice with id=" + microServiceId, e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new BancardMSException("Unable to load IBancardMS microservice with id=" + microServiceId, e);
		}
	}

	
	private MicroServiceEntity findMicroService(Long microServiceId) {
		MicroServiceEntity findOne = microServiceRepository.findOne(microServiceId);
		return findOne;
	}

	/**
	 * Obtiene todos los jar (facturadores) de una localidad configurada como
	 * parámetro del sistema.
	 * <p>
	 * Devuelve un array de {@link URL}, con una URL por cada uno de los jar
	 * encontrados en esa localidad.
	 * <p>
	 * Este array puede usarse luego para crear un classloader y cargar de forma
	 * dinámica las clases que están dentro de cualquiera de los jars.
	 * @param string 
	 * 
	 * @return
	 * @throws IOException 
	 */
	private URL[] getURLs(String className) throws IOException {
		
		File location = new File(this.msJarLocation);
		File [] jarFiles = location.listFiles( new FileFilter() {
	           public boolean accept( File file ) {
	               return file.getName().endsWith(".jar");
	           }
	    });
		
		List<URL> urlsList = new ArrayList<URL>();
		for (File jarFile : jarFiles) {
			if (jarHasClass(jarFile, className)) {
				urlsList.add(jarFile.toURI().toURL());
			}
		}
					
		logger.debug("Url list: " + urlsList);
		return urlsList.toArray(new URL[urlsList.size()]);
	}



	private boolean jarHasClass(File jarFile, String className) throws IOException {
		ZipInputStream zip = null;
		try {
			zip = new ZipInputStream(new FileInputStream(jarFile.getAbsolutePath()));
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
			    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
			        // This ZipEntry represents a class. Now, what class does it represent?
			        String jarClass = entry.getName().replace('/', '.'); // including ".class"
			        jarClass = jarClass.substring(0, jarClass.length() - ".class".length()); // FQN of the class
			        if (jarClass.equals(className)) {
			        	return true;
			        }
			    }
			}
		} finally {
			zip.close();
		}
		return false;
	}

	@Override
	public List<MicroServiceDTO> listAll() {
		List<MicroServiceDTO> dtoList = new ArrayList<MicroServiceDTO>();
		Set<String> keySet = microServiceIdToMicroService.keySet();
		for (String mId : keySet) {
			MicroService microService = microServiceIdToMicroService.get(mId);
			MicroServiceStatus status = microService.toDTO();
			String[] serviceArr = mId.split(SERVICE_KEY_SEPARATOR);
			MicroServiceEntity entity = microServiceRepository.findByServiceNameAndProduct(serviceArr[0], serviceArr[1]);
			if (entity != null) {
				dtoList.add(MicroServiceConverter.toStatusDTO(entity, status));
			}
		}
		return dtoList;
	}
}
