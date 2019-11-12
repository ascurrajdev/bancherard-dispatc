package py.com.sodep.bancard.services;

import java.util.List;

import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.dto.MicroServiceDTO;
import py.com.sodep.bancard.services.impl.MicroService;

/**
 * Implementaciones de esta clase tienen lógica para levantar los microservicios
 * de forma dinámica y retornar instancias de los mismos.
 * <p>
 * Los microservicios deben ser implementaciones de {@link BancardMS}.
 *
 */
public interface MicroServiceManager {

	/**
	 * Obtiene un microservicio ya cargado o lo carga por primera vez y lo
	 * retorna ya instanciado.
	 * 
	 * 
	 * 
	 * @param microServiceId
	 *            identificador único del microservicio a obtener/cargar
	 * @return
	 */
	//public MicroService getOrLoadMicroService(Long microServiceId);

	/**
	 * Obtiene un microservicio ya cargado o lo carga por primera vez y lo
	 * retorna ya instanciado.
	 * 
	 * 
	 * @param serviceName
	 *            el nombre que identifica de manera única al microservicio,
	 *            ejemplos: ande y tigo.
	 * @param product
	 *            el nombre del producto que también identifica al microservicio
	 *            de manera única en conjunto con el <code>serviceName</code>.
	 *            Ejemplos: factura, recarga, star.
	 * @return
	 */
	public MicroService getOrLoadMicroService(String serviceName, String product);

	/**
	 * Hace una recarga de todos los microservicios, cuya información ya se
	 * tiene almacenada en el sistema.
	 */
	public void reload();
	public List<MicroServiceDTO> listAll();
	public MicroService getMicroService(String serviceName, String product);
	public MicroService reloadMicroService(String serviceName, String product);
}
