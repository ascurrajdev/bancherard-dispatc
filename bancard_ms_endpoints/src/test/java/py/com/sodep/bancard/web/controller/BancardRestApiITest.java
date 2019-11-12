package py.com.sodep.bancard.web.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import py.com.sodep.bancard.Application;
import py.com.sodep.bancard.BancardTestBase;
import py.com.sodep.bancard.api.enums.Currency;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.objects.BancardMessage;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.dto.MicroServiceDTO;
import py.com.sodep.bancard.ms.dummy.RejectionMicroService;
import py.com.sodep.bancard.ms.dummy.RuntimeErrorMicroService;
import py.com.sodep.bancard.rest.InvoiceRequest;
import py.com.sodep.bancard.rest.PaymentRequest;
import py.com.sodep.bancard.rest.ReverseRequest;
import py.com.sodep.bancard.services.MicroServiceEntityService;
import py.com.sodep.bancard.services.MicroServiceManager;

/**
 * Clase que hace un test de integración del API REST de Bancard:
 * <ul>
 * <li>/invoices</li>
 * <li>/payment</li>
 * <li>/reverse</li>
 * </ul>
 * 
 * Esta clase debería detectar cualquier modificación que se haga en los
 * RestControllers del dispatcher, ya sea en los path de las URL, verbos HTTP,
 * parámetros, respuestas y códigos de respuestas.
 * 
 * <p>
 * Esta clase <b>NO</b> verifica correctitud en ejecución de ninguno de
 * los facturadores, esta no es responsabilidad del dispatcher.
 * 
 * <p>
 * Se ponen todos los métodos en una sola clase por motivos de performance,
 * para que Spring Boot levante una sola vez el contexto para el primer método
 * con '@Test' y lo cachee para la ejecución de los siguientes métodos.
 * 
 * <p>
 * Created by rodrigo on 25/05/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest("server.port:9000")
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BancardRestApiITest extends BancardTestBase {

    private static final MediaType contentTypeJsonUTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    
    //private static final MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());
    
    private final static Long TID = 3949L;
	
	private final static String SUB_ID = "1234567";
	
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MicroServiceEntityService msService;
    
    @Autowired
    private MicroServiceManager msManager;
    
	private MicroServiceDTO msDto;

	private InvoiceRequest invoiceRequest;

	private PaymentRequest paymentRequest;
	
	private ReverseRequest reverseRequest;
	
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        
        // cargar un MS de prueba en la BD
        this.msDto = this.readMockMS();
        
        // request para pruebas de consulta de facturas
        List<String> subId = new ArrayList<String>();
        subId.add(SUB_ID);
        
        this.invoiceRequest = InvoiceRequest.builder()
        		.productId(123)
        		.subId(subId)
        		.transactionId(TID).build();
        
        // request para pago
        List<String> invId = new ArrayList<String>();
        this.paymentRequest = PaymentRequest.builder()
				.productId(123)
				.transactionId(TID)
				.subId(subId)
				.amt(123000)
				.curr(Currency.PYG.toString())
				.commissionCurr(Currency.PYG.toString())
				.transactionDate("20151129")
				.transactionHour("133001")
				.invId(invId).build();
        
        // request para reversa
        this.reverseRequest = ReverseRequest.builder()
				.productId(123)
				.transactionId(TID)
				.subId(subId)
				.amount(123000)
				.currency(Currency.PYG.toString())
				.commissionCurrency(Currency.PYG.toString())
				.transactionDate("20151129")
				.transactionHour("133001")
				.invId(invId).build();
        
    }

    // <InvoiceController Integration Test>--------------------------------------------------------------//
    @Test
    public void readTestInvoice() throws Exception {
        mockMvc.perform(get("/test/invoices" )
                    .param("tid", "3949")
                    .param("prd_id", "1")
                    .param("sub_id[]", "021")
                    .param("sub_id[]", "621044")
                    .param("addl", "{\"cmr_bra\":76, \"cmr_id\":232}")
                    .accept(contentTypeJsonUTF8)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJsonUTF8))
                .andExpect(jsonPath("$.tid", is(3949)))
                .andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_SUCCESS)))
                .andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_SUCCESS)))
        		.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.QUERY_PROCESSED.getKey())));
    }
    
    @Test
    public void readTestNotFoundInvoice() throws Exception {
        mockMvc.perform(get("/test/invoices" )
                    .param("tid", "3949")
                    .param("prd_id", "1")
                    .param("sub_id[]", "021") // numero de abonado negativo
                    .param("sub_id[]", "-621044")
                    .param("addl", "{\"cmr_bra\":76, \"cmr_id\":232}")
                    .accept(contentTypeJsonUTF8)
                ).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentTypeJsonUTF8))
                .andExpect(jsonPath("$.tid", is(3949)))
                .andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_SUCCESS)))
                .andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_INFO)))
        		.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.SUBSCRIBER_NOT_FOUND.getKey())));
    }
    
    @Test
    public void testInvoices() throws Exception {
    	MicroServiceDTO create = msService.create(msDto);
        Assert.assertNotNull(create);
        
        // cargar en memoria
        msManager.reloadMicroService(msDto.getServiceName(), msDto.getProduct());
        
        String tId = String.valueOf(invoiceRequest.getTransactionId());
        Integer tIdValue = invoiceRequest.getTransactionId().intValue();
        
        mockMvc.perform(get(InvoiceController.INVOICES_MAPPING, msDto.getServiceName(), msDto.getProduct())
        		    .param("tid", tId)
                    .param("prd_id", String.valueOf(invoiceRequest.getProductId()))
                    .param("sub_id[]", invoiceRequest.getSubId().get(0))
                    .param("addl", "{\"cmr_bra\":76, \"cmr_id\":232}")
                    .accept(contentTypeJsonUTF8)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJsonUTF8))
                .andExpect(jsonPath("$.tid", is(tIdValue)))
                .andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_SUCCESS)))
                .andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_SUCCESS)))
        		.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.QUERY_PROCESSED.getKey())));
    }
  
    // <PaymentController Integration Test>--------------------------------------------------------------------------------//
	@Test
	public void testPayment() throws Exception {
		MicroServiceDTO create = msService.create(msDto);
		Assert.assertNotNull(create);

		// cargar en memoria
		msManager.reloadMicroService(msDto.getServiceName(), msDto.getProduct());

		Integer tIdValue = paymentRequest.getTransactionId().intValue();
		String json = toJsonString(paymentRequest);
		
		mockMvc.perform(post(PaymentController.PAYMENT_MAPPING, msDto.getServiceName(), msDto.getProduct())
						.accept(contentTypeJsonUTF8)
						.contentType(contentTypeJsonUTF8)
						.content(json))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeJsonUTF8))
				.andExpect(jsonPath("$.tid", is(tIdValue)))
				.andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_SUCCESS)))
				.andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_SUCCESS)))
				.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.PAYMENT_PROCESSED.getKey())));
	}
	
	@Test
	public void testPaymentWithError() throws Exception {
		msDto.setClassName(RuntimeErrorMicroService.class.getName());
		MicroServiceDTO create = msService.create(msDto);
		Assert.assertNotNull(create);

		// cargar en memoria
		msManager.reloadMicroService(msDto.getServiceName(), msDto.getProduct());

		String json = toJsonString(paymentRequest);
		
		mockMvc.perform(post(PaymentController.PAYMENT_MAPPING, msDto.getServiceName(), msDto.getProduct())
						.accept(contentTypeJsonUTF8)
						.contentType(contentTypeJsonUTF8)
						.content(json))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().contentType(contentTypeJsonUTF8))
				.andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_ERROR)))
				.andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_ERROR)))
				.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.MICROSERVICE_INTERNAL_ERROR.getKey())));
	}
	
	@Test
	public void testPaymentRejection() throws Exception {
		msDto.setClassName(RejectionMicroService.class.getName());
		MicroServiceDTO create = msService.create(msDto);
		Assert.assertNotNull(create);

		// cargar en memoria
		msManager.reloadMicroService(msDto.getServiceName(), msDto.getProduct());

		String json = toJsonString(paymentRequest);
		
		mockMvc.perform(post(PaymentController.PAYMENT_MAPPING, msDto.getServiceName(), msDto.getProduct())
						.accept(contentTypeJsonUTF8)
						.contentType(contentTypeJsonUTF8)
						.content(json))
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(content().contentType(contentTypeJsonUTF8))
				.andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_ERROR)))
				.andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_ERROR)))
				.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.PAYMENT_NOT_AUTHORIZED.getKey())));
	}
	
	
	// <ReverseController Integration Test> ---------------------------------------------------------------------------------------//
	@Test
	public void testReverse() throws Exception {
		MicroServiceDTO create = msService.create(msDto);
		Assert.assertNotNull(create);

		// cargar en memoria
		msManager.reloadMicroService(msDto.getServiceName(), msDto.getProduct());

		String json = toJsonString(reverseRequest);
		
		mockMvc.perform(post(ReverseController.REVERSE_MAPPING, msDto.getServiceName(), msDto.getProduct())
						.accept(contentTypeJsonUTF8)
						.contentType(contentTypeJsonUTF8)
						.content(json))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeJsonUTF8))
				// En la documentación del API de Bancard no se pide este parámetro
				// pero capaz conviene agregar para estandarizar el API
				//.andExpect(jsonPath("$.tid", is(tIdValue))) 
				.andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_SUCCESS)))
				.andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_SUCCESS)))
				.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.TRANSACTION_REVERSED.getKey())));
	}
	
    

	/*
	 ***************************************************************************************/ 
	@Test
	public void testDispatcherExceptionRequest() throws Exception {
				
		mockMvc.perform(get("/test/checkederror")
						.accept(contentTypeJsonUTF8)
						.contentType(contentTypeJsonUTF8))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().contentType(contentTypeJsonUTF8))
				.andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_ERROR)))
				.andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_ERROR)))
				.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.UNKNOWN_ERROR.getKey())));
	}
	
	@Test
	public void testDispatcherRuntimeExceptionRequest() throws Exception {
				
		mockMvc.perform(get("/test/uncheckederror")
						.accept(contentTypeJsonUTF8)
						.contentType(contentTypeJsonUTF8))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().contentType(contentTypeJsonUTF8))
				.andExpect(jsonPath("$.status", is(BancardResponse.RESPONSE_STATUS_ERROR)))
				.andExpect(jsonPath("$.messages[0].level", is(BancardMessage.LEVEL_ERROR)))
				.andExpect(jsonPath("$.messages[0].key", is(GenericMessageKey.UNKNOWN_ERROR.getKey())));
	}
}

