package py.com.sodep.bancard.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.com.sodep.bancard.api.dto.TransactionDTO;
import py.com.sodep.bancard.api.objects.BancardMessage;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.TxInformedRequest;
import py.com.sodep.bancard.api.utils.DateHelper;
import py.com.sodep.bancard.services.ITransactionService;

import com.wordnik.swagger.annotations.ApiParam;

@RestController
public class TransactionController {

	@Autowired
	private ITransactionService transactionService;
	
	@RequestMapping(value="/admin/transactions/{serviceKey}/{product}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionDTO>> get(
			@PathVariable String serviceKey,
			@PathVariable String product,
			@ApiParam(value="Fecha y hora desde (incluyente)") @RequestParam(value = "fromDate") @DateTimeFormat(pattern=DateHelper.DEFAULT_DATE_TIME_FORMAT ) Date fromDate,
			@ApiParam(value="Fecha y hora hasta (excluyente)") @RequestParam(value = "toDate") @DateTimeFormat(pattern=DateHelper.DEFAULT_DATE_TIME_FORMAT) Date toDate,
			@ApiParam(value="Transacciones aún no generadas") @RequestParam(value = "informed", required=false, defaultValue="false") Boolean informed,
			@ApiParam(value="Columna por la cual se quiere ordenar el resultado de la consulta") @RequestParam(value = "orderBy", required = false, defaultValue="created") String orderBy,
			@ApiParam(value="Tipo de orden del resultado de la consulta", allowableValues="asc, desc") @RequestParam(value = "order", required = false, defaultValue="asc") String order) {
		boolean ascending = true;
		if (order != null) {
			ascending = order.equalsIgnoreCase("asc");
		}
		List<TransactionDTO> list = transactionService.findAll(serviceKey, product, fromDate, toDate, informed, orderBy, ascending);
		return new ResponseEntity<List<TransactionDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/transactions/informed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BancardResponse> informed(
			@ApiParam(value="Transacciones informadas por el generador de archivos") @RequestBody TxInformedRequest request) {
		transactionService.inform(request.getTransactions());
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_SUCCESS);
		response.setMessages(BancardMessage.getOneSuccessList("TransactionsInformed", "Transactions informed"));
		return new ResponseEntity<BancardResponse>(response , HttpStatus.OK);
	}
}
