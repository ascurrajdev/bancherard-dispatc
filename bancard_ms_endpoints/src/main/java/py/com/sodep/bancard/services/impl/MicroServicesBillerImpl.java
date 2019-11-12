package py.com.sodep.bancard.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMSException;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.rest.InvoiceRequest;
import py.com.sodep.bancard.rest.PaymentRequest;
import py.com.sodep.bancard.rest.ReverseRequest;
import py.com.sodep.bancard.services.MicroServiceManager;
import py.com.sodep.bancard.services.MicroServicesBiller;

@Service
public class MicroServicesBillerImpl implements MicroServicesBiller {
	
	private final Logger logger = LoggerFactory.getLogger(MicroServicesBillerImpl.class);
	
	@Autowired
	private MicroServiceManager microServiceManager;
	
	@Autowired
	private MicroServiceExecutor executor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * py.com.sodep.bancard.services.impl.InvoiceService#getInvoice(py.com.sodep
	 * .bancard.api.objects.invoices.InvoiceRequest)
	 */
	@Override
	public BancardResponse getInvoice(InvoiceRequest request, String serviceKey, String product) throws BancardAPIException {
		MicroService microService = microServiceManager.getOrLoadMicroService(serviceKey, product);
		checkStatus(microService);
		
		BancardResponse bancardResponse = executor.listInvoices(request, microService);
		logger.debug("salio del MicroServiceExecutor: getInvoice = " + bancardResponse);
		return bancardResponse;
	}

	@Override
	public BancardResponse doPayment(PaymentRequest request, String serviceKey, String product) throws BancardAPIException {

		MicroService microService = microServiceManager.getOrLoadMicroService(serviceKey, product);
		checkStatus(microService);		
		BancardResponse response = executor.doPayment(request, microService);
		logger.debug("salio del MicroServiceExecutor: doPayment = " + response);
		return response;
	}

	@Override
	public BancardResponse doReverse(ReverseRequest reverse, String serviceKey, String product) throws BancardAPIException {
		MicroService microService = microServiceManager.getOrLoadMicroService(serviceKey, product);
		checkStatus(microService);
		BancardResponse response = executor.doReverse(reverse, microService);
		logger.debug("salio del MicroServiceExecutor: doReverse = " + response);
		return response;
	}

	/**
	 * Verifica si el microservicio está activo.
	 * <p>
	 * Si no está activo lanza un {@link BancardMSException}.
	 * 
	 * @param microService
	 */
	private void checkStatus(MicroService microService) {
		if (!microService.toDTO().isActive()) {
			logger.error(GenericMessageKey.MICROSERVICE_DOWN.getDesc());
			throw new BancardMSException(GenericMessageKey.MICROSERVICE_DOWN.getDesc(), null, GenericMessageKey.MICROSERVICE_DOWN.getKey());
		}
	}
}
