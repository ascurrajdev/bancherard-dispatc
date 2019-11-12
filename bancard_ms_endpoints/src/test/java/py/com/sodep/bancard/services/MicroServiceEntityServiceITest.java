package py.com.sodep.bancard.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import py.com.sodep.bancard.Application;
import py.com.sodep.bancard.BancardTestBase;
import py.com.sodep.bancard.dto.MicroServiceDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class MicroServiceEntityServiceITest extends BancardTestBase {
	
	@Autowired
	MicroServiceEntityService microServiceEntityService;
	
	private static String MS_PROP_URL = "serviceUrl";
	
	private static String MS_PROP_URL_NEW_VALUE = "http://familiar.com";
	
	private static final String MS_PROP_URL_VALUE = "http://localhost:2000/scows/factura.asmx";

	private static final String SERVICE_NAME = "ms-ande";
	
	private static final String SERVICE_PROD = "facturas";
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testCrud() {
		// create
		MicroServiceDTO create = createOne();
		Assert.assertNotNull(create);
		
		// read
		MicroServiceDTO read = microServiceEntityService.findOne(create.getId());
		Assert.assertNotNull(read);
		Assert.assertEquals(MS_PROP_URL_VALUE, read.getParams().get(MS_PROP_URL));
		
		// update
		read.getParams().put(MS_PROP_URL, MS_PROP_URL_NEW_VALUE);
		MicroServiceDTO save = microServiceEntityService.save(read);
		Assert.assertNotNull(save);
		Assert.assertEquals(MS_PROP_URL_NEW_VALUE, save.getParams().get(MS_PROP_URL));
		
		// delete
		microServiceEntityService.delete(SERVICE_NAME, SERVICE_PROD);
		MicroServiceDTO findOne = microServiceEntityService.findOne(save.getId());
		Assert.assertNull(findOne);
	}

	private MicroServiceDTO createOne() {
		MicroServiceDTO microServiceDto = (MicroServiceDTO) this.readValue(BancardTestBase.MS_MOCK_JSON, MicroServiceDTO.class);
		microServiceDto.setServiceName(SERVICE_NAME);
		microServiceDto.setProduct(SERVICE_PROD);
		MicroServiceDTO dto = microServiceEntityService.create(microServiceDto);
		return dto;
	}
}
