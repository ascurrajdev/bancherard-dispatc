package py.com.sodep.bancard.services.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardMSException;
import py.com.sodep.bancard.api.microservices.BancardMS;

/**
 * Esta es una clase que representa a un microservicio activado dentro del
 * Dispatcher. Su objectivo es guardar estadisticas y procesos genericos de
 * todos los servicios
 * 
 * @author danicricco
 *
 */
public class MicroService {

	// FIXME capaz es buena idea poner los default en una configuración del
	// sistema.
	public static final long DEFAULT_MS_TIMEOUT = 5000L;// 5 segundos
	public static final Integer DEFAULT_MS_MAX_TIMEOUT = 10;
	public static final Integer DEFAULT_NUMBER_OF_FAILS = 2;

	private Long defaultTimeout;
	private Integer defaultMaxNumberOfTimeout;
	private Integer defaultMaxNumberOfFails;

	private Logger logger = LoggerFactory.getLogger(MicroService.class);
	/**
	 * El microservicio implementado por el cliente
	 */
	private final BancardMS clientMS;

	private final MicroServiceStatus microServiceStatus;

	public MicroService(BancardMS clientMS) {
		this.clientMS = clientMS;
		microServiceStatus = new MicroServiceStatus();
		defaultTimeout = DEFAULT_MS_TIMEOUT;
		defaultMaxNumberOfTimeout = DEFAULT_MS_MAX_TIMEOUT;
		defaultMaxNumberOfFails = DEFAULT_NUMBER_OF_FAILS;
	}

	public static class Builder {
		private Long defaultTimeout;
		private Integer defaultMaxNumberOfTimeout;
		private Integer defaultMaxNumberOfFails;
		private BancardMS clientMS;
		
		public Builder defaultTimeout(Long defaultTimeout) {
			this.defaultTimeout = defaultTimeout;
			return this;
		}

		public Builder defaultMaxNumberOfTimeout(
				Integer defaultMaxNumberOfTimeout) {
			this.defaultMaxNumberOfTimeout = defaultMaxNumberOfTimeout;
			return this;
		}

		public Builder defaultMaxNumberOfFails(Integer defaultMaxNumberOfFails) {
			this.defaultMaxNumberOfFails = defaultMaxNumberOfFails;
			return this;
		}
		
		public Builder clientMS(BancardMS clientMS) {
			this.clientMS = clientMS;
			return this;
		}

		public MicroService build() {
			return new MicroService(this);
		}
	}

	private MicroService(Builder builder) {
		this.defaultTimeout = builder.defaultTimeout;
		this.defaultMaxNumberOfTimeout = builder.defaultMaxNumberOfTimeout;
		this.defaultMaxNumberOfFails = builder.defaultMaxNumberOfFails;
		this.clientMS = builder.clientMS;
		this.microServiceStatus = new MicroServiceStatus();
	}
	
	
	public BancardMS getClientMS() {
		return clientMS;
	}

	public String getName() {
		return clientMS.getClass().getName();
	}

	public void initialize(Map<String, String> initParams) {
		logger.debug("Inicializando driver " + clientMS.getClass().getName());
		try {
			clientMS.initialize(initParams);
		} catch (Throwable e) {
			throw new BancardMSException(GenericMessageKey.MICROSERVICE_INITIALIZATION_ERROR.getDesc(), e, GenericMessageKey.MICROSERVICE_INITIALIZATION_ERROR.getKey());
		}

		// Busca la propiedad "timeout" o utiliza el default si no se creo
		// ninguna
		Long timeout = this.defaultTimeout;
		Integer maxNumberOfTimeout = this.defaultMaxNumberOfTimeout;
		Integer maxNumberOfFails = this.defaultMaxNumberOfFails;

		if (initParams != null) {
			String timeoutStr = initParams.get("timeout");
			if (timeoutStr != null) {
				timeout = Long.parseLong(timeoutStr);
			}

			// Busca la propiedad maxNumberOfTimeout y guarda la cantidad maxima
			String maxNumberOfTimeoutStr = initParams.get("maxNumberOfTimeout");

			if (maxNumberOfTimeoutStr != null
					&& maxNumberOfTimeoutStr.equals("infinite")) {
				// infinite es el keyword para no poner limites finales
				maxNumberOfTimeout = null;
			} else if (maxNumberOfTimeoutStr != null) {
				maxNumberOfTimeout = Integer.parseInt(maxNumberOfTimeoutStr);
			}

			String maxNumberOfFailsStr = initParams.get("maxNumberOfFails");
			if (maxNumberOfFailsStr != null) {
				maxNumberOfFails = Integer.parseInt(maxNumberOfFailsStr);
			}
		}
		microServiceStatus.setTimeoutInMilliseconds(timeout);
		microServiceStatus.setMaxTimeOut(maxNumberOfTimeout);
		microServiceStatus.setMaxNumberOfFails(maxNumberOfFails);

	}

	public void incrementNumberOfTimeout() {
		microServiceStatus.incrementNumberOfTimeout();
	}

	public void incrementNumberOfFail() {
		microServiceStatus.incrementFails();
	}

	// FIXME ver el momento que sería bueno llamar a esto.
	// (danicricco) Yo pienso que sería una interfaz JMX o un REST API de
	// administración
	/**
	 * Deja el estado de los contadores de timeout y fallo a cero
	 */
	public void resetStatus() {
		microServiceStatus.reset();
	}

	public MicroServiceStatus toDTO() {
		return microServiceStatus;
	}
}
