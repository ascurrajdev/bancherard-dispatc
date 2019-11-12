package py.com.sodep.bancard.web.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.rest.PaymentRequest;
import py.com.sodep.bancard.services.MicroServicesBiller;

@RestController
public class PaymentController extends BancardMSController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	public static final String PAYMENT_MAPPING = "/pagos/brands/{serviceKey}/product/{product}/payment";
	
	@Autowired
	private MicroServicesBiller invoiceService;
	
	@ApiOperation(value = "Realiza una operación de pago", notes = "Realiza una operación de pago")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message= "PaymentNotAuthorized, HostTransactionError, NoResponseFromHost, MissingParameters, UnknownError" ),
            @ApiResponse(code = 404, message = "SubscriberNotFound"),
            @ApiResponse(code = 422, message = "InvalidParameters"),
            @ApiResponse(code = 200, message = "PaymentProcessed, PaymentAuthorized, SubscriberWithoutDebt")})
    @RequestMapping(value = PAYMENT_MAPPING, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<BancardResponse> payment(
			@ApiParam(name = "serviceKey", value = "Nombre del servicio al que queremos hacer consultas", required = true) @PathVariable String serviceKey, 
			@ApiParam(name = "product", value = "Nombre del producto que el servicio ofrece para realizar consultas", required = true) @PathVariable String product,
			@ApiParam(name = "payment", value = "JSON con la información del pago a realizar", required = true) @Valid @RequestBody PaymentRequest request) throws BancardAPIException, MissingServletRequestParameterException {
		logger.info("PAYMENT REQUEST: {}", request);
		BancardResponse response = invoiceService.doPayment(request, serviceKey, product);
		logger.info("PAYMENT RESPONSE: {}", response);
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}
}
