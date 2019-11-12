package py.com.sodep.bancard.ms.dummy;

import java.util.Map;

import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

/***
 * Este es un microservicio que simplemente tarda un tiempo antes de devolver.
 * Se puede utilizar para probar que el sistema reacciona a tiempo con los
 * timeout
 * 
 * @author danicricco
 *
 */
public class SlowMicroService implements BancardMS {

	private final Long timeout; 

	public SlowMicroService() {
		this.timeout = 10000L; // 10 segundos
	}
	
	
	public SlowMicroService(Long timeout) {

		this.timeout = timeout;
	}

	@Override
	public BancardResponse listInvoices(InvoiceQuery query) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return null;
	}

	@Override
	public BancardResponse doPayment(Payment payment) {
		BancardResponse response = new BancardResponse();
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return response;
	}

	@Override
	public BancardResponse doReverse(Reverse paymentToReverse) {
		BancardResponse response = new BancardResponse();
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return response;
	}

	@Override
	public void initialize(Map<String, String> initParams) {
	}
}
