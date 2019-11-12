package py.com.sodep.bancard.dispatcher;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

/***
 * Este es un driver dummy que cuenta la cantidad de veces que se llama a cada
 * uno de los metodos del API. Sirve para hacer pruebas y corroborar que los
 * métodos se llamaron la cantidad de veces esperada
 * 
 * @author danicricco
 *
 */
public class RegisterCallAPI implements BancardMS {
	private static Logger logger = LoggerFactory.getLogger(RegisterCallAPI.class);
	private int numberOfCallToListInvoices = 0;
	private int numberOfCallToDoPayment = 0;
	private int numberOfCallToDoReverse = 0;

	@Override
	public synchronized BancardResponse listInvoices(InvoiceQuery query) {
		numberOfCallToListInvoices++;
		logger.debug("Probando listInvoices");
		return null;
	}

	@Override
	public BancardResponse doPayment(Payment payment) {
		BancardResponse response = new  BancardResponse();
		logger.debug("Probando doPayment");
		numberOfCallToDoPayment++;
		return response;
	}

	@Override
	public BancardResponse doReverse(Reverse reverse) {
		BancardResponse response = new  BancardResponse();
		logger.debug("Probando doReverse");
		numberOfCallToDoReverse++;
		return response;
	}

	public synchronized int getNumberOfCallToListInvoices() {
		return numberOfCallToListInvoices;
	}

	public synchronized int getNumberOfCallToDoPayment() {
		return numberOfCallToDoPayment;
	}

	public synchronized int getNumberOfCallToDoReverse() {
		return numberOfCallToDoReverse;
	}

	@Override
	public void initialize(Map<String, String> initParams) {
		// TODO Auto-generated method stub
	}

	
}
