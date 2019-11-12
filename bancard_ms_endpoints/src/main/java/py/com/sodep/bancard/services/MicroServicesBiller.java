package py.com.sodep.bancard.services;

import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.rest.InvoiceRequest;
import py.com.sodep.bancard.rest.PaymentRequest;
import py.com.sodep.bancard.rest.ReverseRequest;

public interface MicroServicesBiller {

	/**
	 * 
	 * @param request
	 * @param serviceKey key to identify the Web Service to wich the request will be sent
	 * @param product each Web Service could have more than one product
	 * @return
	 * @throws BancardAPIException
	 */
	public BancardResponse getInvoice(InvoiceRequest request, String serviceKey, String product) throws BancardAPIException;
	public BancardResponse doPayment(PaymentRequest request, String serviceKey, String product) throws BancardAPIException;
	public BancardResponse doReverse(ReverseRequest request, String serviceKey, String product) throws BancardAPIException;
}