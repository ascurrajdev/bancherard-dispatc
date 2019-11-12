package py.com.sodep.bancard.rest;

import java.util.List;

import py.com.sodep.bancard.api.objects.Payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest extends InvoiceRequest {
	
	public static final String PAYMENT_TYPE_MERCHANT = "merchant_payment";
	
	private List<String> invId;
	private Integer amt;
	private String curr;
	private String transactionDate;
	private String transactionHour;
	private Integer commissionAmt;
	private String commissionCurr;
	private String type;
	
	public PaymentRequest() {
		super();
	}
	
	private PaymentRequest(Builder<?> builder) {
		super(builder);
		this.invId = builder.invId;
		this.amt = builder.amt;
		this.curr = builder.curr;
		this.transactionDate = builder.transactionDate;
		this.transactionHour = builder.transactionHour;
		this.commissionAmt = builder.commissionAmt;
		this.commissionCurr = builder.commissionCurr;
		this.type = builder.type;
	}
	
	@JsonProperty("inv_id")
	public List<String> getInvId() {
		return invId;
	}

	public void setInvId(List<String> invId) {
		this.invId = invId;
	}

	@JsonProperty("amt")
	public Integer getAmt() {
		return amt;
	}

	public void setAmt(Integer amt) {
		this.amt = amt;
	}

	@JsonProperty("curr")
	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	@JsonProperty("trn_dat")
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	@JsonProperty("trn_hou")
	public String getTransactionHour() {
		return transactionHour;
	}

	public void setTransactionHour(String transactionHour) {
		this.transactionHour = transactionHour;
	}

	@JsonProperty("cm_amt")
	public Integer getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(Integer commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	@JsonProperty("cm_curr")
	public String getCommissionCurr() {
		return commissionCurr;
	}

	public void setCommissionCurr(String commissionCurr) {
		this.commissionCurr = commissionCurr;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public static abstract class Builder<T extends Builder<T>> extends InvoiceRequest.Builder<T> {
		private List<String> invId;
		private Integer amt;
		private String curr;
		private String transactionDate;
		private String transactionHour;
		private Integer commissionAmt;
		private String commissionCurr;
		private String type;

		public T invId(List<String> invId) {
			this.invId = invId;
			return self();
		}

		public T amt(Integer amt) {
			this.amt = amt;
			return self();
		}

		public T curr(String curr) {
			this.curr = curr;
			return self();
		}

		public T transactionDate(String transactionDate) {
			this.transactionDate = transactionDate;
			return self();
		}

		public T transactionHour(String transactionHour) {
			this.transactionHour = transactionHour;
			return self();
		}

		public T commissionAmt(Integer commissionAmt) {
			this.commissionAmt = commissionAmt;
			return self();
		}

		public T commissionCurr(String commissionCurr) {
			this.commissionCurr = commissionCurr;
			return self();
		}

		public T type(String type) {
			this.type = type;
			return self();
		}

		public PaymentRequest build() {
			return new PaymentRequest(this);
		}
	}
	
	private static class Builder2 extends Builder<Builder2> {
		@Override
		protected Builder2 self() {
			return this;
		}
	}

	 public static Builder<?> builder() {
		 return new Builder2();
	 }

	
	
	
	public Payment toPayment() {
		Payment payment = new Payment();
		payment.setAmount(this.amt);
		payment.setCurrency(this.curr);
		payment.setCommissionAmount(this.commissionAmt);
		payment.setCommissionCurrency(this.commissionCurr);
		payment.setTransactionDate(this.transactionDate);
		payment.setTransactionHour(this.transactionHour);
		payment.setInvId(this.invId);
		payment.setAdditionalData(super.getAdditionalData());
		payment.setType(this.type);
		payment.setSubscriberIds(super.getSubId());
		payment.setTransactionId(super.getTransactionId());
		payment.setProductId(super.getProductId());
		return payment;
	}

	@Override
	public String toString() {
		return "PaymentRequest [invId=" + invId + ", amt=" + amt + ", curr="
				+ curr + ", transactionDate=" + transactionDate
				+ ", transactionHour=" + transactionHour + ", commissionAmt="
				+ commissionAmt + ", commissionCurr=" + commissionCurr
				+ ", type=" + type + ", toString()=" + super.toString() + "]";
	}
}
