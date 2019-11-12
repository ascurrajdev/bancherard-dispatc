package py.com.sodep.bancard.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.BeanUtils;

import py.com.sodep.bancard.api.objects.JsonAddl;
import py.com.sodep.bancard.api.objects.Reverse;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "Reverse")
public class ReverseRequest {

	@JsonProperty("tid")
	@NotNull(message = "El identificador de transacción es requerido")
	private Long transactionId;

	@JsonProperty("prd_id")
	private Integer productId;

	@JsonProperty("sub_id")
	@NotNull(message = "Al menos un identificador de subscriptor es requerido")
	private List<String> subId = new ArrayList<String>();

	@JsonProperty("amt")
	private Integer amount;

	@JsonProperty("curr")
	private String currency;

	@JsonProperty("trn_dat")
	private String transactionDate;

	@JsonProperty("trn_hou")
	private String transactionHour;

	@JsonProperty("cm_amt")
	private Integer commissionAmount;

	@JsonProperty("cm_curr")
	private String commissionCurrency;

	@JsonProperty("inv_id")
	private List<String> invId;

	@JsonProperty("type")
	private String type;

	@JsonProperty("addl")
	private JsonAddl additionalData = new JsonAddl();

	/**
	 * Default constructor
	 */
	public ReverseRequest() {
	}

	public static class Builder {
		private Long transactionId;
		private Integer productId;
		private List<String> subId;
		private Integer amount;
		private String currency;
		private String transactionDate;
		private String transactionHour;
		private Integer commissionAmount;
		private String commissionCurrency;
		private List<String> invId;
		private String type;
		private JsonAddl additionalData;

		public Builder transactionId(Long transactionId) {
			this.transactionId = transactionId;
			return this;
		}

		public Builder productId(Integer productId) {
			this.productId = productId;
			return this;
		}

		public Builder subId(List<String> subId) {
			this.subId = subId;
			return this;
		}

		public Builder amount(Integer amount) {
			this.amount = amount;
			return this;
		}

		public Builder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public Builder transactionDate(String transactionDate) {
			this.transactionDate = transactionDate;
			return this;
		}

		public Builder transactionHour(String transactionHour) {
			this.transactionHour = transactionHour;
			return this;
		}

		public Builder commissionAmount(Integer commissionAmount) {
			this.commissionAmount = commissionAmount;
			return this;
		}

		public Builder commissionCurrency(String commissionCurrency) {
			this.commissionCurrency = commissionCurrency;
			return this;
		}

		public Builder invId(List<String> invId) {
			this.invId = invId;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder additionalData(JsonAddl additionalData) {
			this.additionalData = additionalData;
			return this;
		}

		public ReverseRequest build() {
			return new ReverseRequest(this);
		}
	}

	private ReverseRequest(Builder builder) {
		this.transactionId = builder.transactionId;
		this.productId = builder.productId;
		this.subId = builder.subId;
		this.amount = builder.amount;
		this.currency = builder.currency;
		this.transactionDate = builder.transactionDate;
		this.transactionHour = builder.transactionHour;
		this.commissionAmount = builder.commissionAmount;
		this.commissionCurrency = builder.commissionCurrency;
		this.invId = builder.invId;
		this.type = builder.type;
		this.additionalData = builder.additionalData;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	/**
	 *
	 * @return
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 *
	 * @param transactionId
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	@JsonProperty("prd_id")
	public Integer getProductId() {
		return productId;
	}

	@JsonProperty("prd_id")
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 *
	 * @return
	 * The subId
	 */
	public List<String> getSubId() {
		return subId;
	}

	/**
	 *
	 * @param subId
	 * The sub_id
	 */
	public void setSubId(List<String> subId) {
		this.subId = subId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionHour() {
		return transactionHour;
	}

	public void setTransactionHour(String transactionHour) {
		this.transactionHour = transactionHour;
	}

	public Integer getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(Integer commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getCommissionCurrency() {
		return commissionCurrency;
	}

	public void setCommissionCurrency(String commissionCurrency) {
		this.commissionCurrency = commissionCurrency;
	}

	public List<String> getInvId() {
		return invId;
	}

	public void setInvId(List<String> invId) {
		this.invId = invId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JsonAddl getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(JsonAddl additionalData) {
		this.additionalData = additionalData;
	}


	public Reverse toReverse() {
		Reverse reverse = new Reverse();
		BeanUtils.copyProperties(this, reverse);
		reverse.setSubscriberIds(this.getSubId());
		return reverse;
	}

	/**
	 * Formatea el objeto como un JSON, pero sin usar comillas dobles para
	 * propiedades y valores del tipo string.
	 * <p>
	 * Por motivos de performance se decidió adoptar este formato 'suelto' de
	 * json y no tener que hacer ningún otro parseo adicional del objeto.
	 */
	@Override
	public String toString() {
		return "{tid: " + transactionId + ", prd_id: " + productId
				+ ", sub_id: " + subId + ", amt: " + amount + ", curr: "
				+ currency + ", trn_dat: " + transactionDate + ", trn_hou: "
				+ transactionHour + ", cm_amt: " + commissionAmount
				+ ", cm_curr: " + commissionCurrency + ", inv_id: " + invId
				+ ", type: " + type + ", addl: " + additionalData + "}";
	}


}
