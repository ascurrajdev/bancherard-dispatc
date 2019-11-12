package py.com.sodep.bancard.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import py.com.sodep.bancard.api.dto.serializer.DateTimeDeserializer;
import py.com.sodep.bancard.api.dto.serializer.DateTimeSerializer;
import py.com.sodep.bancard.api.enums.Bills;
import py.com.sodep.bancard.api.enums.Currency;
import py.com.sodep.bancard.api.enums.OffLineType;
import py.com.sodep.bancard.api.objects.NextDue;

@JsonPropertyOrder({ "id", "dueDate", "amount", "customerFields", "billIdentifier", "additionalData",
		"authorizationDate", "currency", "minimumPayment", "status", "paidPaymentId", "creatorId", "productId",
		"commissionAmount", "commissionCurrency", "description", "processorId", "offlineType", "offlineFileRow",
		"createdAt", "updatedAt", "offlineProcessorId", "nextDueList" })
public class BillsDTO implements Serializable {

	private static final long serialVersionUID = 6025871788103170925L;

	private BigInteger id;
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	private Date dueDate;
	private BigDecimal amount;
	private List<String> customerFields;
	private List<String> billIdentifier;
	private List<String> additionalData;
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	private Date authorizationDate;
	private Currency currency;
	private BigDecimal minimumPayment;
	private Date lastPaymentTime;
	private BigDecimal paymentTotal;
	private Bills status;
	private BigInteger paidPaymentId;
	private BigInteger creatorId;
	private Integer productUid;
	private BigDecimal commissionAmount;
	private Currency commissionCurrency;
	private String description;
	private BigInteger processorId;
	private OffLineType offlineType;
	private String[] offlineFileRow;
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	private Date createdAt;
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	private Date updatedAt;
	private Integer offlineProcessorId;
	private List<NextDue> nextDueList;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<String> getCustomerFields() {
		return customerFields;
	}

	public void setCustomerFields(List<String> customerFields) {
		this.customerFields = customerFields;
	}

	public List<String> getBillIdentifier() {
		return billIdentifier;
	}

	public void setBillIdentifier(List<String> billIdentifier) {
		this.billIdentifier = billIdentifier;
	}

	public List<String> getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(List<String> additionalData) {
		this.additionalData = additionalData;
	}

	public Date getAuthorizationDate() {
		return authorizationDate;
	}

	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getMinimumPayment() {
		return minimumPayment;
	}

	public void setMinimumPayment(BigDecimal minimumPayment) {
		this.minimumPayment = minimumPayment;
	}

	public Date getLastPaymentTime() {
		return lastPaymentTime;
	}

	public void setLastPaymentTime(Date lastPaymentTime) {
		this.lastPaymentTime = lastPaymentTime;
	}

	public BigDecimal getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(BigDecimal paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public Bills getStatus() {
		return status;
	}

	public void setStatus(Bills status) {
		this.status = status;
	}

	public BigInteger getPaidPaymentId() {
		return paidPaymentId;
	}

	public void setPaidPaymentId(BigInteger paidPaymentId) {
		this.paidPaymentId = paidPaymentId;
	}

	public BigInteger getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(BigInteger creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getProductUid() {
		return productUid;
	}

	public void setProductUid(Integer productUid) {
		this.productUid = productUid;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public Currency getCommissionCurrency() {
		return commissionCurrency;
	}

	public void setCommissionCurrency(Currency commissionCurrency) {
		this.commissionCurrency = commissionCurrency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigInteger getProcessorId() {
		return processorId;
	}

	public void setProcessorId(BigInteger processorId) {
		this.processorId = processorId;
	}

	public OffLineType getOfflineType() {
		return offlineType;
	}

	public void setOfflineType(OffLineType offlineType) {
		this.offlineType = offlineType;
	}

	public String[] getOfflineFileRow() {
		return offlineFileRow;
	}

	public void setOfflineFileRow(String[] offlineFileRow) {
		this.offlineFileRow = offlineFileRow;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getOfflineProcessorId() {
		return offlineProcessorId;
	}

	public void setOfflineProcessorId(Integer offlineProcessorId) {
		this.offlineProcessorId = offlineProcessorId;
	}

	public List<NextDue> getNextDueList() {
		return nextDueList;
	}

	public void setNextDueList(List<NextDue> nextDueList) {
		this.nextDueList = nextDueList;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "BillsDTO [id=" + id + ", dueDate=" + dueDate + ", amount=" + amount + ", customerFields="
				+ customerFields + ", billIdentifier=" + billIdentifier + ", additionalData=" + additionalData
				+ ", authorizationDate=" + authorizationDate + ", currency=" + currency + ", minimumPayment="
				+ minimumPayment + ", lastPaymentTime=" + lastPaymentTime + ", paymentTotal=" + paymentTotal
				+ ", status=" + status + ", paidPaymentId=" + paidPaymentId + ", creatorId=" + creatorId
				+ ", productUid=" + productUid + ", commissionAmount=" + commissionAmount + ", commissionCurrency="
				+ commissionCurrency + ", description=" + description + ", processorId=" + processorId
				+ ", offlineType=" + offlineType + ", offlineFileRow=" + Arrays.toString(offlineFileRow)
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", offlineProcessorId=" + offlineProcessorId
				+ ", nextDueList=" + nextDueList + ", additionalProperties=" + additionalProperties + "]";
	}
}