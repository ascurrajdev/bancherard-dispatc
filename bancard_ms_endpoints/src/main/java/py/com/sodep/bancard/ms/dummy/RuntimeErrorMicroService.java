package py.com.sodep.bancard.ms.dummy;

import java.util.Map;

import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

/**
 * Un microservicio que siempre tira un {@link NullPointerException}
 */
public class RuntimeErrorMicroService implements BancardMS {
	private final RuntimeException e;

	public RuntimeErrorMicroService() {
		this.e = new NullPointerException("Bad times in this microservice :(");
	}

	public RuntimeErrorMicroService(RuntimeException e) {
		this.e = e;
	}

	@Override
	public BancardResponse listInvoices(InvoiceQuery query) {
		throw e;
	}

	@Override
	public BancardResponse doPayment(Payment payment) {
		throw e;
	}

	@Override
	public BancardResponse doReverse(Reverse reverse) {
		throw e;
	}

	@Override
	public void initialize(Map<String, String> initParams) {
	}
}
