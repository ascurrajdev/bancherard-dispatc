package py.com.sodep.bancard.api.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice {

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
    private List<String> additionalData;

    @JsonProperty("next_dues")
    private List<NextDue> nextDues;

    @JsonProperty("cm_amt")
    private Integer commissionAmount;

    @JsonProperty("cm_curr")
    private String commissionCurrency;

    @JsonProperty("dsc")
    private String description;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The due
     */
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

    /**
     * @return The amt
     */
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

    /**
     * @return The minAmt
     */
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

    /**
     * @return The invId
     */
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

    /**
     * @return The curr
     */
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

    /**
     * @return The addl
     */
    @JsonProperty("addl")
    public List<String> getAdditionalData() {
        return additionalData;
    }

    /**
     * @param addl The addl
     */
    @JsonProperty("addl")
    public void setAdditionalData(List<String> additionalData) {
        this.additionalData = additionalData;
    }

    /**
     * @return The nextDues
     */
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

    /**
     * @return The cmAmt
     */
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

    /**
     * @return The cmCurr
     */
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

    /**
     * @return The dsc
     */
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

	@Override
	public String toString() {
		return "Invoice [due=" + due + ", amount=" + amount + ", minAmount="
				+ minAmount + ", invId=" + invId + ", currency=" + currency
				+ ", additionalData=" + additionalData + ", nextDues="
				+ nextDues + ", commissionAmount=" + commissionAmount
				+ ", commissionCurrency=" + commissionCurrency
				+ ", description=" + description + ", additionalProperties="
				+ additionalProperties + "]";
	}   
}