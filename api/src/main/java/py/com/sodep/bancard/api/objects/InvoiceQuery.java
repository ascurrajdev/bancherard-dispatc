package py.com.sodep.bancard.api.objects;

import java.util.ArrayList;
import java.util.List;

public class InvoiceQuery {
	private Long transactionId;
	private Long tkt;
	private List<String> subId = new ArrayList<String>();
	private Integer productId;

	/**
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the tkt
	 */
	public Long getTkt() {
		return tkt;
	}

	/**
	 * @param tkt the tkt to set
	 */
	public void setTkt(Long tkt) {
		this.tkt = tkt;
	}

	/**
	 * @return the subId
	 */
	public List<String> getSubId() {
		return subId;
	}

	/**
	 * @param subId the subId to set
	 */
	public void setSubId(List<String> subId) {
		this.subId = subId;
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
        return productId;
    }

	/**
	 * @param productId the productId to set
	 */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

	@Override
	public String toString() {
		return "InvoiceQuery [transactionId=" + transactionId + ", tkt=" + tkt + ", subId=" + subId + ", productId="
				+ productId + "]";
	}
}