package py.com.sodep.bancard.ms.dummy;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.Invoice;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

public class DummyFacturas implements BancardMS {

	private Logger logger = LoggerFactory.getLogger(DummyFacturas.class);

	@Override
	public BancardResponse listInvoices(InvoiceQuery query) {
		ArrayList<Invoice> invoicesList = new ArrayList<Invoice>();
		Invoice invoice = new Invoice();
		invoice.setAmount(10000);
		invoice.setCommissionAmount(100);
		invoice.setCurrency("3333");
		invoice.setDescription("adfafd");
		invoice.setDue("2015-05-30");
		ArrayList<String> invIds = new ArrayList<String>();
		invIds.add("33");
		invoice.setInvId(invIds);
		invoice.setMinAmount(500);
		invoicesList.add(invoice);
		logger.debug("Listando facturas");
		return BancardResponse.getInvoicesSuccess(invoicesList, query.getTransactionId());
	}

	@Override
	public BancardResponse doPayment(Payment payment) {
		BancardResponse response = BancardResponse.getSuccess(GenericMessageKey.PAYMENT_PROCESSED, payment.getTransactionId());
		response.setTransactionId(payment.getTransactionId());
		logger.debug("Pago realizado");
		return response;
	}

	@Override
	public BancardResponse doReverse(Reverse reverse) {
		BancardResponse response = BancardResponse.getSuccess(GenericMessageKey.TRANSACTION_REVERSED, reverse.getTransactionId());
		response.setTransactionId(reverse.getTransactionId());
		logger.debug("Haciendo el reverso");
		return response;

	}

	@Override
	public void initialize(Map<String, String> initParams) {
		logger.debug("Inicializando");
	}
}
