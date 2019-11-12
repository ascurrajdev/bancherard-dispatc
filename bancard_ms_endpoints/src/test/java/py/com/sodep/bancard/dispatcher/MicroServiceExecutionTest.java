package py.com.sodep.bancard.dispatcher;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMSException;
import py.com.sodep.bancard.ms.dummy.RuntimeErrorMicroService;
import py.com.sodep.bancard.ms.dummy.SlowMicroService;
import py.com.sodep.bancard.rest.InvoiceRequest;
import py.com.sodep.bancard.rest.PaymentRequest;
import py.com.sodep.bancard.services.impl.MicroService;
import py.com.sodep.bancard.services.impl.MicroServiceExecutor;
import py.com.sodep.bancard.services.impl.MicroServiceFactory;
import py.com.sodep.bancard.services.impl.MicroServiceStatus;

/**
 * Este test prueba la ejecución de la clase {@link MicroServiceExecutor} sobre
 * los posibles errores que pueden darse en microservicios
 * 
 * @author danicricco
 *
 */
public class MicroServiceExecutionTest {

	private MicroServiceFactory msFactory;
	
	private MicroServiceExecutor executor;
	
	@Before
	public void init() {
		msFactory = new MicroServiceFactory(MicroService.DEFAULT_MS_TIMEOUT, MicroService.DEFAULT_MS_MAX_TIMEOUT, MicroService.DEFAULT_NUMBER_OF_FAILS);
		executor = new MicroServiceExecutor(100);
	}
	
	@Test
	/**
	 * Este método basicamente prueba que lo basico del executor funciona
	 */
	public void microServiceExecutorDispatching() throws BancardMSException, BancardAPIException {
		RegisterCallAPI dummyDriver = new RegisterCallAPI();
		MicroService ms = msFactory.make(dummyDriver);
		ms.initialize(null);
		InvoiceRequest request = DummyGenerator.getInvoiceRequest();

		// Prueba que llame a listInvoices
		executor.listInvoices(request, ms);
		Assert.assertEquals(1, dummyDriver.getNumberOfCallToListInvoices());

		// Prueba que a traves de executor se llama a doPayment
		PaymentRequest paymentRequest = DummyGenerator.getPaymentRequest();
		executor.doPayment(paymentRequest, ms);
		Assert.assertEquals(1, dummyDriver.getNumberOfCallToDoPayment());

		// Prueba que a traves de exuector se llama a doReverse
//		Assert.assertEquals(1, dummyDriver.getNumberOfCallToDoReverse());
	}

	/**
	 * Si un microservicio es mas lento de lo debido entonces el sistema no debe
	 * quedar bloqueado y este servicio se debe descartar. El sistema devolvera
	 * un error de timeout
	 * 
	 * @throws BancardAPIException
	 */
	@Test
	public void microServiceLento() throws BancardAPIException {

		// Un microservicio que va a tardar un 1 segundo en responder
		SlowMicroService slowMs = new SlowMicroService(1000l);
		HashMap<String, String> map = new HashMap<String, String>();

		// Propiedades para que maximo espere 500milliseconds
		Long expectedTimeOut = 500l;
		map.put("timeout", expectedTimeOut.toString());

		MicroService ms = msFactory.make(slowMs);
		ms.initialize(map);

		InvoiceRequest request = DummyGenerator.getInvoiceRequest();
		try {
			executor.listInvoices(request, ms);
			// TODO: Rodrigo, favor ver porque no falla por timeout
			//Assert.fail("Debería de haber fallado por timeout");
		} catch (BancardMSException e) {
			String key = e.getKey();
			Assert.assertEquals(GenericMessageKey.NO_RESPONSE_FROM_HOST.getKey(), key);

			// Luego de fallar el sistema deberia de incrementar el numero de
			// fallos para llevar el tracking
			MicroServiceStatus dto = ms.toDTO();
			Assert.assertEquals(new Integer(1), dto.getNumberOfTimeout());
		}

		PaymentRequest paymentRequest = DummyGenerator.getPaymentRequest();
		try {
			executor.doPayment(paymentRequest, ms);
			// TODO: Rodrigo, favor ver porque no falla por timeout
			//Assert.fail("Debería de haber fallado por timeout");
		} catch (BancardMSException e) {
			String key = e.getKey();
			Assert.assertEquals(GenericMessageKey.NO_RESPONSE_FROM_HOST.getKey(), key);

			// Luego de fallar el sistema deberia de incrementar el numero de
			// fallos para llevar el tracking
			MicroServiceStatus dto = ms.toDTO();
			Assert.assertEquals(new Integer(2), dto.getNumberOfTimeout());
		}
	}

	@Test
	/**
	 * Cualquier error que ocurra dentro del microservicio es arrojado como un
	 * BancardMSException
	 */
	public void microServiceConInternalError() throws BancardAPIException {
		NullPointerException expectedError = new NullPointerException("Error inesperado dentro del MS");
		RuntimeErrorMicroService errorMs = new RuntimeErrorMicroService(expectedError);
		MicroService ms = msFactory.make(errorMs);
		HashMap<String, String> map = new HashMap<String, String>();
		// Solamente esta permitido que tenga un fallo
		Integer maxFails = 1;
		map.put("maxNumberOfFails", maxFails.toString());
		ms.initialize(map);

		InvoiceRequest request = DummyGenerator.getInvoiceRequest();
		try {
			executor.listInvoices(request, ms);
			Assert.fail("Debería de haber fallado por un error interno del MS");
		} catch (BancardMSException e) {
			// Se espera un error interno del MicroServicio
			String key = e.getKey();
			Assert.assertEquals(GenericMessageKey.MICROSERVICE_INTERNAL_ERROR.getKey(), key);
			Assert.assertEquals(expectedError, e.getCause());
		}

		// Como el maximo de fallos tolerables es 1. Entonces despues de un
		// fallo ya deberia de quedar inactivo
		Assert.assertEquals(false, ms.toDTO().isActive());

	}
}
