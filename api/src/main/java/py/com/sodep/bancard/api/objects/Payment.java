package py.com.sodep.bancard.api.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Datos de un pago.
 * <p>
 * Instancias de esta clase son construidas y enviadas a los microservicios.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment implements ITransaction {

	// TODO ver si hace falta tener estos JsonProperty, ya que esta clase no es serializada via REST
	
	@JsonProperty("tid")
	private Long transactionId;
	
	@JsonProperty("sub_id")
	private List<String> subscriberIds;
	
	@JsonProperty("due")
    private String due;

    @JsonProperty("amt")
    private Integer amount;

    @JsonProperty("min_amt")
    private Integer minAmount;

    @JsonProperty("inv_id")
    private List<String> invId;

    @JsonProperty("curr")
    private String currency;

    @JsonProperty("addl")
    private JsonAddl additionalData;

    @JsonProperty("next_dues")
    private List<NextDue> nextDues;

    @JsonProperty("cm_amt")
    private Integer commissionAmount;

    @JsonProperty("cm_curr")
    private String commissionCurrency;

    @JsonProperty("dsc")
    private String description;
    
	@JsonProperty("trn_dat")
    private String transactionDate;

	@JsonProperty("trn_hou")
	private String transactionHour;

	@JsonProperty("prd_id")
	private Integer productId;
	
	@JsonProperty("type")
	private String type;
	

	/**
	 * Additional request properties
	 */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Additional biller specific properties, like token info for example.
     * 
     */
    @JsonIgnore
	private Map<String, Object> billerData = new HashMap<String, Object>();

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getDue()
	 */
    @Override
	@JsonProperty("due")
    public String getDue() {
        return due;
    }

    /**
     * @param due The due
     */
    @JsonProperty("due")
    public void setDue(String due) {
        this.due = due;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getAmount()
	 */
    @Override
	@JsonProperty("amt")
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    @JsonProperty("amt")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getMinAmount()
	 */
    @Override
	@JsonProperty("min_amt")
    public Integer getMinAmount() {
        return minAmount;
    }

    /**
     * @param minAmt The min_amt
     */
    @JsonProperty("min_amt")
    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getInvId()
	 */
    @Override
	@JsonProperty("inv_id")
    public List<String> getInvId() {
        return invId;
    }

    /**
     * @param invId The inv_id
     */
    @JsonProperty("inv_id")
    public void setInvId(List<String> invId) {
        this.invId = invId;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getCurrency()
	 */
    @Override
	@JsonProperty("curr")
    public String getCurrency() {
        return currency;
    }

    /**
     * @param curr The curr
     */
    @JsonProperty("curr")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getAdditionalData()
	 */
    @Override
	@JsonProperty("addl")
    public JsonAddl getAdditionalData() {
        return additionalData;
    }

    /**
     * @param addl The addl
     */
    @JsonProperty("addl")
    public void setAdditionalData(JsonAddl additionalData) {
        this.additionalData = additionalData;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getNextDues()
	 */
    @Override
	@JsonProperty("next_dues")
    public List<NextDue> getNextDues() {
        return nextDues;
    }

    /**
     * @param nextDues The next_dues
     */
    @JsonProperty("next_dues")
    public void setNextDues(List<NextDue> nextDues) {
        this.nextDues = nextDues;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getCommissionAmount()
	 */
    @Override
	@JsonProperty("cm_amt")
    public Integer getCommissionAmount() {
        return commissionAmount;
    }

    /**
     * @param cmAmt The cm_amt
     */
    @JsonProperty("cm_amt")
    public void setCommissionAmount(Integer commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getCommissionCurrency()
	 */
    @Override
	@JsonProperty("cm_curr")
    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    /**
     * @param cmCurr The cm_curr
     */
    @JsonProperty("cm_curr")
    public void setCommissionCurrency(String commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getDescription()
	 */
    @Override
	@JsonProperty("dsc")
    public String getDescription() {
        return description;
    }

    /**
     * @param dsc The dsc
     */
    @JsonProperty("dsc")
    public void setDescription(String description) {
        this.description = description;
    }

    /* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getAdditionalProperties()
	 */
    @Override
	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
  
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    	this.additionalProperties = additionalProperties;
    }
    

	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getSubscriberIds()
	 */
	@Override
	public List<String> getSubscriberIds() {
		return subscriberIds;
	}

	public void setSubscriberIds(List<String> subscriberIds) {
		this.subscriberIds = subscriberIds;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getTransactionId()
	 */
	@Override
	public Long getTransactionId() {
		return this.transactionId;
	}

	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getTransactionDate()
	 */
	@Override
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getTransactionHour()
	 */
	@Override
	public String getTransactionHour() {
		return transactionHour;
	}

	public void setTransactionHour(String transactionHour) {
		this.transactionHour = transactionHour;
	}

	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getProductId()
	 */
	@Override
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.api.objects.ITransaction#getBillerData()
	 */
	@Override
	public Map<String, Object> getBillerData() {
		return this.billerData ;
	}
	
	public void setBillerData(String name, Object value) {
		this.billerData.put(name, value);
	}

	@Override
	public String toString() {
		return "Payment [transactionId=" + transactionId + ", subscriberIds="
				+ subscriberIds + ", due=" + due + ", amount=" + amount
				+ ", minAmount=" + minAmount + ", invId=" + invId
				+ ", currency=" + currency + ", additionalData="
				+ additionalData + ", nextDues=" + nextDues
				+ ", commissionAmount=" + commissionAmount
				+ ", commissionCurrency=" + commissionCurrency
				+ ", description=" + description + ", transactionDate="
				+ transactionDate + ", transactionHour=" + transactionHour
				+ ", productId=" + productId + ", type=" + type
				+ ", additionalProperties=" + additionalProperties + "]";
	}
}
