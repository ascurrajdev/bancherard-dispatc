package py.com.sodep.bancard.dispatcher;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import py.com.sodep.bancard.services.impl.MicroService;
import py.com.sodep.bancard.services.impl.MicroServiceFactory;
import py.com.sodep.bancard.services.impl.MicroServiceStatus;

public class MicroServiceTest {

	private MicroServiceFactory msFactory;
	
	@Before
	public void init() {
		msFactory = new MicroServiceFactory(MicroService.DEFAULT_MS_TIMEOUT, MicroService.DEFAULT_MS_MAX_TIMEOUT, MicroService.DEFAULT_NUMBER_OF_FAILS);
		
	}
	
	@Test
	/***
	 * Incrementa el numero de timeout y prueba que funciona correctamente
	 */
	public void testIncrementNumberOfTimeout() {
		RegisterCallAPI dummyDriver = new RegisterCallAPI();
		MicroService ms = msFactory.make(dummyDriver);
		ms.incrementNumberOfTimeout();
		MicroServiceStatus dto = ms.toDTO();
		Assert.assertEquals(new Integer(1), dto.getNumberOfTimeout());

		ms.incrementNumberOfTimeout();
		Assert.assertEquals(new Integer(2), dto.getNumberOfTimeout());

		ms.resetStatus();
		Assert.assertEquals(new Integer(0), dto.getNumberOfTimeout());
	}

	@Test
	public void initializeTimeout() {
		RegisterCallAPI dummyDriver = new RegisterCallAPI();
		MicroService ms = msFactory.make(dummyDriver);
		HashMap<String, String> map = new HashMap<String, String>();

		Long expectedTimeOut = 400l;
		Integer expectedMaxTimeout = 60;
		Integer expctedMaxFails = 10;
		map.put("timeout", expectedTimeOut.toString());
		map.put("maxNumberOfTimeout", expectedMaxTimeout.toString());
		map.put("maxNumberOfFails", expctedMaxFails.toString());
		ms.initialize(map);

		MicroServiceStatus status = ms.toDTO();
		Assert.assertEquals(expectedTimeOut, status.getTimeoutInMilliseconds());
		Assert.assertEquals(expectedMaxTimeout, status.getMaxTimeOut());
		Assert.assertEquals(expctedMaxFails, status.getMaxNumberOfFails());

	}

	@Test
	/**
	 * Al inicializar con null o con vacio las propiedades por default deberian
	 * quedar asignadas
	 */
	public void initializeWithNull() {
		RegisterCallAPI dummyDriver = new RegisterCallAPI();
		MicroService ms = msFactory.make(dummyDriver);

		ms.initialize(null);
		MicroServiceStatus status = ms.toDTO();
		Assert.assertEquals(new Long(MicroService.DEFAULT_MS_TIMEOUT), status.getTimeoutInMilliseconds());
		Assert.assertEquals(new Integer(MicroService.DEFAULT_MS_MAX_TIMEOUT), status.getMaxTimeOut());
		Assert.assertEquals(new Integer(MicroService.DEFAULT_NUMBER_OF_FAILS), status.getMaxNumberOfFails());

		ms.initialize(new HashMap<String, String>());
		status = ms.toDTO();
		Assert.assertEquals(new Long(MicroService.DEFAULT_MS_TIMEOUT), status.getTimeoutInMilliseconds());
		Assert.assertEquals(new Integer(MicroService.DEFAULT_MS_MAX_TIMEOUT), status.getMaxTimeOut());
		Assert.assertEquals(new Integer(MicroService.DEFAULT_NUMBER_OF_FAILS), status.getMaxNumberOfFails());
	}
}
