package py.com.sodep.bancard.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.rest.InvoiceRequest;
import py.com.sodep.bancard.services.MicroServicesBiller;

@RestController
public class InvoiceController extends BancardMSController {

	private final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	
	public static final String INVOICES_MAPPING = "/pagos/brands/{serviceKey}/product/{product}/invoices";
	
	@Autowired
	MicroServicesBiller invoiceService;

	@ApiOperation(value = "Retorna lista de facturas", notes = "Retorna la lista de facturas correspondientes a un subscriptor")
	@ApiResponses(value = {
			@ApiResponse(code = 403, message = "HostTransactionError, NoResponseFromHost, QueryNotProcessed, MissingParameters, QueryNotAllowed, UnknownError"),
			@ApiResponse(code = 404, message = "SubscriberNotFound"),
			@ApiResponse(code = 422, message = "InvalidParameters"),
			@ApiResponse(code = 200, message = "QueryProcessed, PaymentAuthorized, SubscriberWithoutDebt") })
	@RequestMapping(value = INVOICES_MAPPING, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BancardResponse> invoices(
			@ApiParam(value = "Nombre del servicio al que queremos hacer consultas", required = true) @PathVariable String serviceKey, 
			@ApiParam(value = "Nombre del producto que el servicio ofrece para realizar consultas", required = true) @PathVariable String product,
			@ApiParam(value = "Número identificador de la transacción") @RequestParam("tid") Long transactionId, 
			@ApiParam(value = "Número identificador del producto") @RequestParam("prd_id") Integer productId,
			@ApiParam(value = "Lista de identificadores de subscriptores. Por ejemplo para la ANDE este identificador sería el NIS") @RequestParam("sub_id[]") List<String> subId,
			@ApiParam(value = "JSON con datos adicionales requeridos en la consulta") @RequestParam(value = "addl", required = false) String additionalDataJson) throws BancardAPIException {
		InvoiceRequest invoiceRequest = InvoiceRequest.builder().additionalDataJson(additionalDataJson)
				.transactionId(transactionId).productId(productId).subId(subId).build();
		logger.info("INVOICE REQUEST: " + String.valueOf(invoiceRequest));
		BancardResponse response = invoiceService.getInvoice(invoiceRequest, serviceKey, product);
		logger.info("INVOICE RESPONSE: " + String.valueOf(response));
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}
}
