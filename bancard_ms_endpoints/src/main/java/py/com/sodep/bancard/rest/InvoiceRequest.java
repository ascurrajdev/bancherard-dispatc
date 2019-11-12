package py.com.sodep.bancard.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.JsonAddl;

/**
 * Encapsulates data needed to request information about invoices.
 * <p>
 * Properties in this class comes in invoices request to Bancard REST API.
 */

public class InvoiceRequest {

	private Long transactionId;

	private Integer productId;

	private List<String> subId = new ArrayList<String>();

	private JsonAddl additionalData = new JsonAddl();

	protected InvoiceRequest(Builder<?> builder) {
		setTransactionId(builder.transactionId);
		setProductId(builder.productId);
		setSubId(builder.subId);
		additionalData = builder.additionalData;
	}

	/**
	 * Default constructor. Necesario para que Jackson pueda crear este objeto.
	 * 
	 */
	public InvoiceRequest() {
	}

	/**
	 *
	 * @return
	 */
	@JsonProperty("tid")
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

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 *
	 * @return The subId
	 */
	@JsonProperty("sub_id")
	public List<String> getSubId() {
		return subId;
	}

	/**
	 *
	 * @param subId
	 *            The sub_id
	 */
	public void setSubId(List<String> subId) {
		this.subId = subId;
	}

	@JsonProperty("addl")
	public JsonAddl getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(JsonAddl additionalData) {
		this.additionalData = additionalData;
	}

	public static abstract class Builder<T extends Builder<T>> {

		private Long transactionId;

		private Integer productId;

		private List<String> subId;

		private JsonAddl additionalData;

		private ObjectMapper mapper = new ObjectMapper();

		protected abstract T self();

		public T additionalDataJson(String additionalDataJson) {
			additionalDataFromString(additionalDataJson);
			return self();
		}

		private void additionalDataFromString(String additionalDataString) {
			if (additionalDataString != null) {
				try {
					this.additionalData = mapper.readValue(additionalDataString, JsonAddl.class);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		public T transactionId(Long transactionId) {
			this.transactionId = transactionId;
			return self();
		}

		public T productId(Integer productId) {
			this.productId = productId;
			return self();
		}

		public T subId(List<String> subId) {
			this.subId = subId;
			return self();
		}

		public T additionalData(JsonAddl additionalData) {
			this.additionalData = additionalData;
			return self();
		}

		public InvoiceRequest build() {
			return new InvoiceRequest(this);
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

	public InvoiceQuery toInvoiceQuery() {
		InvoiceQuery query = new InvoiceQuery();
		BeanUtils.copyProperties(this, query);
		return query;
	}

	@Override
	public String toString() {
		return "InvoiceRequest [transactionId=" + transactionId + ", productId=" + productId + ", subId=" + subId
				+ ", additionalData=" + additionalData + "]";
	}
}