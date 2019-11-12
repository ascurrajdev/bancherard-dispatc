package py.com.bancard.microservice.cit.microservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.bancard.microservice.cit.dto.Const;
import py.com.bancard.microservice.cit.dto.InvResponseDto;
import py.com.bancard.microservice.cit.dto.TxResponseDto;
import py.com.bancard.microservice.cit.util.DateHelper;
import py.com.bancard.microservice.cit.util.CitUtil;
import py.com.sodep.bancard.api.enums.Currency;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.enums.ParticularMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.Invoice;

public class CitValidator {
	static final Logger logger = LoggerFactory.getLogger(CitValidator.class);

	public static List<Invoice> validateInvoices(List<InvResponseDto> response) throws BancardAPIException, Exception {
		try {
			if (response == null)
				throw new BancardAPIException(ParticularMessageKey.QUERY_NOT_PROCESSED, "La consulta no pudo ser procesada");
			if (response.isEmpty() || response.size() == 0)
				throw new BancardAPIException(ParticularMessageKey.SUBSCRIBER_WITHOUT_DEBT, "Usuario sin deudas");
			List<Invoice> invoicesList = new ArrayList<>();
			Iterator<InvResponseDto> itr = response.iterator();
			while(itr.hasNext()) {
				InvResponseDto factura = itr.next();
				if (Integer.valueOf(factura.getTotalDeuda()).intValue() > 0) {
					Invoice invoice = new Invoice();
					invoice.setDue(DateHelper.fromDate(factura.getFechaVencimiento(), DateHelper.SIMPLE_DAY_FORMAT_MOTOR));
					invoice.setAmount(Integer.valueOf(factura.getTotalDeuda()).intValue());
					invoice.setMinAmount(Integer.valueOf(factura.getTotalDeuda()).intValue());
					List<String> invArray = new ArrayList<>();
					/* ELEMENTOS DEL inv_id
					 * 1) nro de Operacion (nro de socio)
					 * 2) nro de cuota
					 * 3) Descripcion Operacion
					 * 4) moneda
					 * 5) anhomes
					 */
					invArray.add(String.valueOf(factura.getNroOperacion()));
					invArray.add(String.valueOf(factura.getNroCuota()));
					invArray.add(factura.getDesOperacion());
					invArray.add(String.valueOf(factura.getMoneda()));
					invArray.add(CitUtil.extractDigits(factura.getDesOperacion()));
					invoice.setInvId(invArray);
					invoice.setDescription("Socio: "+factura.getApenom().trim()+ ", Descripcion: "+factura.getDesOperacion()+ ", Nro de Cuota: "+factura.getNroCuota());
					invoice.setCurrency(Currency.PYG.name());
					List<String> addlArray = new ArrayList<>();
					addlArray.add(factura.getApenom().trim());
					addlArray.add(String.valueOf(factura.getNroCuota()));
					invoice.setAdditionalData(addlArray);
					invoicesList.add(invoice);
				}
		    }
			logger.info(String.valueOf(invoicesList));
			return invoicesList;
		} catch (BancardAPIException e) {
			logger.error("BancardAPIException: " + Arrays.toString(e.getStackTrace()));
			throw new BancardAPIException(ParticularMessageKey.fromKey(e.getKey()), e.getMessage());
		} catch (Exception e) {
			logger.error("Exception: " + Arrays.toString(e.getStackTrace()));
			throw new BancardAPIException(e.getMessage(), e.getCause());
		}
	}

	public static BancardResponse validatePaymentResponse(TxResponseDto responseBD, long tid) {
		BancardResponse response = BancardResponse.getSuccess(GenericMessageKey.PAYMENT_PROCESSED, tid);
		logger.info("responseBD = " + responseBD);

		if(responseBD == null || responseBD.getCodigoError() == null)
			response = BancardResponse.getPaymentError(new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "Null Response"), tid);
		else if (!Const.CIT_TX_SUCCESS.equals(responseBD.getCodigoError()))
			response = BancardResponse.getPaymentError(new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, responseBD.getCodigoError()+":"+responseBD.getDescripcionError()), tid);
		
		return response;
	}

	public static BancardResponse validateReverseResponse(TxResponseDto responseBD, Long tid) {
		BancardResponse response = BancardResponse.getSuccess(GenericMessageKey.TRANSACTION_REVERSED, tid);
		logger.info("responseBD = " + responseBD);

		if(responseBD == null || responseBD.getCodigoError() == null)
			response = BancardResponse.getReverseError(new BancardAPIException(ParticularMessageKey.TRANSACTION_NOT_REVERSED, "Null Response"), tid);
		else if (!Const.CIT_TX_SUCCESS.equals(responseBD.getCodigoError()))
			response = BancardResponse.getReverseError(new BancardAPIException(ParticularMessageKey.TRANSACTION_NOT_REVERSED, responseBD.getCodigoError()+":"+responseBD.getDescripcionError()), tid);
			
		return response;
	}
	
	
}