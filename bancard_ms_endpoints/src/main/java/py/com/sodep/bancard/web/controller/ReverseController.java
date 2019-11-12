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

import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.rest.ReverseRequest;
import py.com.sodep.bancard.services.MicroServicesBiller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
public class ReverseController extends BancardMSController {

	private static final Logger logger = LoggerFactory.getLogger(ReverseController.class);
	public static final String REVERSE_MAPPING = "/pagos/brands/{serviceKey}/product/{product}/reverse";

	@Autowired
	private MicroServicesBiller invoiceService;

    @ApiOperation(value = "Realiza una operación de reversa", notes = "Realiza una operación de reversa")
    @ApiResponses(value = {
      @ApiResponse(code = 403, message= "AlreadyReversed, MissingParameters, NoResponseFromHost, UnknownError, HostTransactionError, TransactionNotReversed" ),
      @ApiResponse(code = 422, message = "InvalidParameters"),
      @ApiResponse(code = 200, message = "TransactionReversed")})
    @RequestMapping(value = REVERSE_MAPPING, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<BancardResponse> reverse(
			@ApiParam(name = "serviceKey", value = "Nombre del servicio al que queremos hacer consultas", required = true) @PathVariable String serviceKey, 
			@ApiParam(name = "product", value = "Nombre del producto que el servicio ofrece para realizar consultas", required = true) @PathVariable String product,
			@ApiParam(name = "reverse", value = "JSON con la información del pago a reversar", required = true) @Valid @RequestBody ReverseRequest reverse) throws BancardAPIException, MissingServletRequestParameterException {
		logger.info("REVERSE REQUEST: {}",  reverse);
		BancardResponse response = invoiceService.doReverse(reverse, serviceKey, product);
		logger.info("REVERSE RESPONSE: {}", response);
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}
}
