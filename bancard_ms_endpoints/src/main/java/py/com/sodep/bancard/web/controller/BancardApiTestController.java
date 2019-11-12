package py.com.sodep.bancard.web.controller;

import java.io.InputStream;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.com.sodep.bancard.api.objects.BancardResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
public class BancardApiTestController  extends BancardMSController {
	
	
	private ObjectMapper mapper = new ObjectMapper();

	private static final String INVOICE_RESPONSE_TEST = "/invoice_response_example.json";

	private static final String INVOICE_NOT_FOUND_RESPONSE_TEST = "/invoice_not_found_response_example.json";

	@ApiOperation(value = "Retorna una respuesta creada a modo de ejemplo", notes = "Solamente usado para propósitos de testing. Se eliminará más adelante")
	@RequestMapping(value = "/test/invoices", method = RequestMethod.GET)
	public ResponseEntity<BancardResponse> invoicesTest(@RequestParam("tid") Long transactionId,
			@RequestParam("prd_id") Integer productId, @RequestParam("sub_id[]") List<String> subId,
			@RequestParam(value = "addl", required = false) String additionalDataJson) {

		BancardResponse response = null;
		if (!subId.get(1).contains("-")) {
			response = testResponse(INVOICE_RESPONSE_TEST);
			return new ResponseEntity<BancardResponse>(response, statusCode(response));
		} else {
			response = testResponse(INVOICE_NOT_FOUND_RESPONSE_TEST);
			return new ResponseEntity<BancardResponse>(response, statusCode(response));
		}
	}
	
	
	
	
	protected BancardResponse testResponse(String resource) {
		BancardResponse response;
		try {
			InputStream inputStream = InvoiceController.class.getResourceAsStream(resource);
			response = mapper.readValue(inputStream, BancardResponse.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	@ApiOperation(value = "Retorna una respuesta creada a modo de ejemplo", notes = "Solamente usado para propósitos de testing. Se eliminará más adelante")
	@RequestMapping(value = "/test/checkederror", method = RequestMethod.GET)
	public ResponseEntity<BancardResponse> checkedError() throws Exception {
		throw new Exception("CHECKED ERROR");
	}

	@ApiOperation(value = "Retorna una respuesta creada a modo de ejemplo", notes = "Solamente usado para propósitos de testing. Se eliminará más adelante")
	@RequestMapping(value = "/test/uncheckederror", method = RequestMethod.GET)
	public ResponseEntity<BancardResponse> uncheckedError() {
		throw new RuntimeException("RUNTIME ERROR");
	}
}
