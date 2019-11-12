package py.com.sodep.bancard.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.services.impl.MicroService.Builder;


/**
 * Esta clase crea instancias de {@link MicroService} con parámetros de timeout
 * y fallas por defecto. Estos parámetros son obtenidos del
 * application.properties.
 *
 */
@Component
public class MicroServiceFactory {
	private Long timeout;
	private Integer maxTimeout;
	private Integer numberOfFails;

	@Autowired
	public MicroServiceFactory(@Value("${ms.default.timeout}") Long timeout, 
			@Value("${ms.default.max_timeout}") Integer maxTimeout,
			@Value("${ms.default.number_of_fails}") Integer numberOfFails) {
		this.timeout = timeout;
		this.maxTimeout = maxTimeout;
		this.numberOfFails = numberOfFails;
	}

	public MicroService make(BancardMS clientDriver) {
		Builder builder = new MicroService.Builder();
		builder.clientMS(clientDriver);
		builder.defaultTimeout(timeout);
		builder.defaultMaxNumberOfFails(numberOfFails);
		builder.defaultMaxNumberOfTimeout(maxTimeout);
		return builder.build();
	}
}
