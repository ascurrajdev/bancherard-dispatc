package py.com.sodep.bancard.api.objects;

import java.util.List;
import java.util.Map;

public class Reverse implements ITransaction {

	private Payment payment;
	
	public Reverse(Payment payment) {
		this.payment = payment;
	}
	
	public Reverse() {
		this.payment = new Payment();
	}
    
    /**
     * @return The due
     */
    public String getDue() {
        return payment.getDue();
    }

    /**
     * @param due The due
     */
    public void setDue(String due) {
        this.payment.setDue(due);
    }

    /**
     * @return The amt
     */
    public Integer getAmount() {
        return this.payment.getAmount();
    }

    /**
     * @param amount The amount
     */
    public void setAmount(Integer amount) {
        this.payment.setAmount(amount);
    }

    /**
     * @return The minAmt
     */
    public Integer getMinAmount() {
        return this.payment.getMinAmount();
    }

    /**
     * @param minAmt The min_amt
     */
    public void setMinAmount(Integer minAmount) {
        this.payment.setMinAmount(minAmount);
    }

    /**
     * @return The invId
     */
    public List<String> getInvId() {
        return this.payment.getInvId();
    }

    /**
     * @param invId The inv_id
     */
    public void setInvId(List<String> invId) {
        this.payment.setInvId(invId);
    }

    /**
     * @return The curr
     */
    public String getCurrency() {
        return payment.getCurrency();
    }

    /**
     * @param curr The curr
     */
    public void setCurrency(String currency) {
        this.payment.setCurrency(currency);
    }

    /**
     * @return The addl
     */
    public JsonAddl getAdditionalData() {
        return payment.getAdditionalData();
    }

    /**
     * @param addl The addl
     */
    public void setAdditionalData(JsonAddl additionalData) {
        this.payment.setAdditionalData(additionalData);
    }

    /**
     * @return The nextDues
     */
    public List<NextDue> getNextDues() {
        return payment.getNextDues();
    }

    /**
     * @param nextDues The next_dues
     */
    public void setNextDues(List<NextDue> nextDues) {
        this.payment.setNextDues(nextDues);
    }

    /**
     * @return The cmAmt
     */
    public Integer getCommissionAmount() {
        return payment.getCommissionAmount();
    }

    /**
     * @param cmAmt The cm_amt
     */
    public void setCommissionAmount(Integer commissionAmount) {
        this.payment.setCommissionAmount(commissionAmount);
    }

    /**
     * @return The cmCurr
     */
    public String getCommissionCurrency() {
        return payment.getCommissionCurrency();
    }

    /**
     * @param cmCurr The cm_curr
     */
    public void setCommissionCurrency(String commissionCurrency) {
        this.payment.setCommissionCurrency(commissionCurrency);
    }

    /**
     * @return The dsc
     */
    public String getDescription() {
        return payment.getDescription();
    }

    /**
     * @param dsc The dsc
     */
    public void setDescription(String description) {
        this.payment.setDescription(description);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.payment.getAdditionalProperties();
    }

    public void setAdditionalProperty(String name, Object value) {
        this.payment.getAdditionalProperties().put(name, value);
    }
  
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    	this.payment.setAdditionalProperties(additionalProperties);
    }
    

	public List<String> getSubscriberIds() {
		return payment.getSubscriberIds();
	}

	public void setSubscriberIds(List<String> subscriberIds) {
		this.payment.setSubscriberIds(subscriberIds);
	}

	public void setTransactionId(Long transactionId) {
		this.payment.setTransactionId(transactionId);
	}
	
	public Long getTransactionId() {
		return this.payment.getTransactionId();
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return payment.getTransactionDate();
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.payment.setTransactionDate(transactionDate);
	}

	public String getTransactionHour() {
		return payment.getTransactionHour();
	}

	public void setTransactionHour(String transactionHour) {
		this.payment.setTransactionHour(transactionHour);
	}

	public Integer getProductId() {
		return payment.getProductId();
	}

	public void setProductId(Integer productId) {
		this.payment.setProductId(productId);
	}

	public String getType() {
		return payment.getType();
	}

	public void setType(String type) {
		this.payment.setType(type);
	}

	public Map<String, Object> getBillerData() {
		return this.payment.getBillerData();
	}
	
	public void setBillerData(String name, Object value) {
		this.payment.getBillerData().put(name, value);
	}
	
	public ITransaction getPayment() {
		return this.payment;
	}
	
	@Override
	public String toString() {
		return "Reverse [payment = "+ this.payment +"]";
	}
}
