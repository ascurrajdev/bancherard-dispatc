package py.com.sodep.bancard.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import py.com.sodep.bancard.api.enums.TransactionStatus;
import py.com.sodep.bancard.api.objects.ITransaction;
import py.com.sodep.bancard.api.utils.StringUtils;

@JsonPropertyOrder({ "id", "serviceName", "product", "created", "transactionId", "productUid", "subId", "invId",
		"additionalData", "amt", "curr", "transactionDate", "transactionHour", "commissionAmt", "commissionCurr",
		"type", "status", "informed", "billerData", "billId" })
public class TransactionDTO implements Serializable{
	private static final long serialVersionUID = -9038742748193190618L;

	private static final ObjectMapper mapper = new ObjectMapper();

	private Long id;
	private String serviceName;
	private String product;
	private String created;
	private Long transactionId;
	private Integer productUid;
	private String subId;
	private String invId;
	private String additionalData;
	private Integer amt;
	private String curr;
	private String transactionDate;
	private String transactionHour;
	private Integer commissionAmt;
	private String commissionCurr;
	private String type;
	private TransactionStatus status;
	private Boolean informed;
	private String billerData;
	private Long billId;
	private Long cashierOpening;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getProductUid() {
		return productUid;
	}

	public void setProductUid(Integer productUid) {
		this.productUid = productUid;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public Integer getAmt() {
		return amt;
	}

	public void setAmt(Integer amt) {
		this.amt = amt;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
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

	public Integer getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(Integer commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	public String getCommissionCurr() {
		return commissionCurr;
	}

	public void setCommissionCurr(String commissionCurr) {
		this.commissionCurr = commissionCurr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public Boolean getInformed() {
		return informed;
	}

	public void setInformed(Boolean informed) {
		this.informed = informed;
	}

	public String getBillerData() {
		return billerData;
	}

	public void setBillerData(String billerData) {
		this.billerData = billerData;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCashierOpening() {
		return cashierOpening;
	}

	public void setCashierOpening(Long cashierOpening) {
		this.cashierOpening = cashierOpening;
	}

	public static TransactionDTO fromPayment(String serviceName, String product, ITransaction payment) {
		return fromPayment(serviceName, product, payment, null);
	}

	public static TransactionDTO fromPayment(String serviceName, String product, ITransaction payment, Long billId) {
		TransactionDTO dto = new TransactionDTO();
		dto.setServiceName(serviceName);
		dto.setProduct(product);
		dto.setAmt(payment.getAmount());
		dto.setCurr(payment.getCurrency());
		dto.setCommissionAmt(payment.getCommissionAmount());
		dto.setCommissionCurr(payment.getCommissionCurrency());
		String subIdAsStr = StringUtils.toStringWithSeparator(payment.getSubscriberIds(), ",");
		dto.setSubId(subIdAsStr);
		String invIdAsStr = StringUtils.toStringWithSeparator(payment.getInvId(), ",");
		dto.setInvId(invIdAsStr);
		dto.setProductUid(payment.getProductId());
		dto.setTransactionDate(payment.getTransactionDate());
		dto.setTransactionHour(payment.getTransactionHour());
		dto.setTransactionId(payment.getTransactionId());
		dto.setType(payment.getType());
		dto.setBillId(billId);
		try {
			String additionalData = mapper.writeValueAsString(payment.getAdditionalProperties());
			dto.setAdditionalData(additionalData);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return dto;
	}

	@Override
	public String toString() {
		return "TransactionDTO [id=" + id + ", serviceName=" + serviceName + ", product=" + product + ", created="
				+ created + ", transactionId=" + transactionId + ", productUid=" + productUid + ", subId=" + subId
				+ ", invId=" + invId + ", additionalData=" + additionalData + ", amt=" + amt + ", curr=" + curr
				+ ", transactionDate=" + transactionDate + ", transactionHour=" + transactionHour + ", commissionAmt="
				+ commissionAmt + ", commissionCurr=" + commissionCurr + ", type=" + type + ", status=" + status
				+ ", informed=" + informed + ", billerData=" + billerData + ", billId=" + billId + "]";
	}
}