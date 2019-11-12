package py.com.sodep.bancard.dispatcher;

import java.util.ArrayList;

import py.com.sodep.bancard.rest.InvoiceRequest;
import py.com.sodep.bancard.rest.PaymentRequest;

public class DummyGenerator {

	public static InvoiceRequest getInvoiceRequest() {
		ArrayList<String> subids = new ArrayList<String>();
		subids.add("2");
		InvoiceRequest request = InvoiceRequest.builder().additionalDataJson(null).transactionId(800l)
				.productId(3).subId(subids).build();

		return request;
	}
	
	public static PaymentRequest getPaymentRequest(){
		ArrayList<String> l = new ArrayList<String>();
		l.add("2");
		PaymentRequest request = PaymentRequest.builder()
				.additionalData(null)
				.productId(1)
				.transactionId(1l).subId(l).invId(l)
				.amt(1).curr("a").transactionDate("2015-07-19")
				.transactionHour("09_45").commissionAmt(200)
				.commissionCurr("3").type("4").build();
		
		return request;
	}
}
