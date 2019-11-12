package py.com.bancard.microservice.cit.microservices;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.bancard.microservice.cit.dto.InvRequestDto;
import py.com.bancard.microservice.cit.dto.PaymentRequestDto;
import py.com.bancard.microservice.cit.dto.ReverseRequestDto;
import py.com.bancard.microservice.cit.util.CitUtil;
import py.com.sodep.bancard.api.dto.TransactionDTO;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.enums.ParticularMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.objects.ITransaction;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

public class CitMapper {
	final static Logger logger = LoggerFactory.getLogger(CitMapper.class);

	public static InvRequestDto invoices(InvoiceQuery invoiceQuery) throws BancardAPIException {
		try {
			InvRequestDto request = new InvRequestDto();
			if (invoiceQuery == null)
				throw new BancardAPIException(ParticularMessageKey.QUERY_NOT_PROCESSED, "El objeto Invoices no puede ser nulo");
			if (invoiceQuery.getSubId() == null)
				throw new BancardAPIException(ParticularMessageKey.QUERY_NOT_PROCESSED, "El objeto Subcriber no puede ser nulo");
			if (invoiceQuery.getSubId().isEmpty())
				throw new BancardAPIException(ParticularMessageKey.QUERY_NOT_PROCESSED, "El objeto Subcriber no puede estar vacio");
			if (invoiceQuery.getSubId().get(0) == null || "".equals(invoiceQuery.getSubId().get(0)) )
				throw new BancardAPIException(ParticularMessageKey.QUERY_NOT_PROCESSED, "El dato Numero de Socio no fue agregado");
			try {
				List<String> subId = invoiceQuery.getSubId();
				request.setDocNumber(subId.get(0));
			} catch (Exception e) {
				logger.error("invoicesMapper.SubId Error: " + e.getMessage());
				throw new BancardAPIException(ParticularMessageKey.QUERY_NOT_PROCESSED, "Error procesando objeto Subcriber");
			}
			return request;
		} catch (BancardAPIException e){
			logger.error("invoicesMapper BancardAPIException: " + e.getMessage());
			throw new BancardAPIException(GenericMessageKey.fromKey(e.getKey()), e.getMessage());
		} catch (Exception e) {
			logger.error("invoicesMapper Exception: ", e);
			throw new BancardAPIException(e.getMessage(), e.getCause());
		}
	}

	public static PaymentRequestDto payment(Payment payment) throws BancardAPIException {
		try {
			PaymentRequestDto request = new PaymentRequestDto();
			if (payment == null)
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El objeto payment no puede ser nulo");
			
			if (payment.getSubscriberIds() == null)
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El objeto Subcriber no puede ser nulo");
			if (payment.getSubscriberIds().isEmpty())
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El objeto Subcriber no puede estar vacio");
			if (payment.getSubscriberIds().get(0) == null || "".equals(payment.getSubscriberIds().get(0)) )
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El dato numero de documento no fue agregado");
			
			if (payment.getInvId() == null)
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El objeto Indentificador de factura no puede ser nulo");
			if (payment.getInvId().isEmpty())
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El objeto Indentificador de factura no puede estar vacio");
			if (payment.getInvId().get(0) == null || "".equals(payment.getInvId().get(0)) )
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El dato Nro de Operacion no fue agregado");
			if (payment.getInvId().get(1) == null || "".equals(payment.getInvId().get(1)) )
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El dato Nro de Cuota no fue agregado");
			if (payment.getInvId().get(2) == null || "".equals(payment.getInvId().get(2)) )
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El dato Descripcion no fue agregado");
			if (payment.getInvId().get(3) == null || "".equals(payment.getInvId().get(3)) )
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El dato moneda no fue agregado");
			if (payment.getInvId().get(4) == null || "".equals(payment.getInvId().get(3)) )
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "El dato anhomes no fue agregado");

			try {
				request.setNroOperacion(Integer.parseInt(payment.getInvId().get(0)));
				request.setNroCuota(Integer.parseInt(payment.getInvId().get(1)));
				request.setMoneda(payment.getInvId().get(3));
				request.setAnhomes(payment.getInvId().get(4));
				
				String nroTransaccion;
				nroTransaccion = CitUtil.getConsecutivo(payment.getTransactionId());
				
				request.setCodTransaccion(Long.valueOf(nroTransaccion));
				request.setAnulado(0);
				request.setImporte(CitUtil.zerosToLeft(payment.getAmount(), 13));
				request.setFechapago(new Timestamp(System.currentTimeMillis()).toString());
			} catch (Exception e) {
				logger.error("paymentMapper Error: " + e.getMessage());
				throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "Error procesando objeto Subcriber e Identificador de Factura");
			}
			return request;
		} catch (BancardAPIException e){
			logger.error("paymentMapper BancardAPIException: " + e.getMessage());
			throw new BancardAPIException(GenericMessageKey.fromKey(e.getKey()), e.getMessage());
		} catch (Exception e) {
			logger.error("paymentMapper Exception: ", e);
			throw new BancardAPIException(e.getMessage(), e.getCause());
		}
	}

	public static ReverseRequestDto reverse(Reverse reverse) throws BancardAPIException{
		try {
			ReverseRequestDto request = new ReverseRequestDto();
			if (reverse == null)
				throw new BancardAPIException(ParticularMessageKey.TRANSACTION_NOT_REVERSED, "El objeto reverse no puede ser nulo");

			try {
				String nroTransaccion;
				nroTransaccion = CitUtil.getConsecutivoReverse(reverse.getTransactionId());
				request.setCodTransaccion(Long.valueOf(nroTransaccion));
				request.setCodTransaccionAnular(Long.valueOf(nroTransaccion));
				request.setFechaanulacion(new Timestamp(System.currentTimeMillis()).toString());
			} catch (Exception e) {
				logger.error("reverse Mapper Error: " + e.getMessage());
				throw new BancardAPIException(ParticularMessageKey.TRANSACTION_NOT_REVERSED, "Error al procesar Reversa");
			}
			return request;
		} catch (BancardAPIException e){
			logger.error("paymentMapper BancardAPIException: " + e.getMessage());
			throw new BancardAPIException(GenericMessageKey.fromKey(e.getKey()), e.getMessage());
		} catch (Exception e) {
			logger.error("paymentMapper Exception: ", e);
			throw new BancardAPIException(e.getMessage(), e.getCause());
		}
	}
	
	
	public static TransactionDTO paymentToTransaction(String token, ITransaction payment, String serviceName, String product) {
		TransactionDTO dto = TransactionDTO.fromPayment(serviceName, product, payment);
		dto.setType(token);
		return dto;
	}
}
