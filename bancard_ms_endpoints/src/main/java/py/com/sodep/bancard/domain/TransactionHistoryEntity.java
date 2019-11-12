package py.com.sodep.bancard.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import py.com.sodep.bancard.api.enums.TransactionStatus;

@Entity
@Table(name = "transaction_history", schema = "bancard", uniqueConstraints = @UniqueConstraint(columnNames = "tid"))
@SequenceGenerator(name = "transaction_history_id_seq", sequenceName = "bancard.transaction_history_id_seq", allocationSize=1)
public class TransactionHistoryEntity {

	private Long id;
	
	private String serviceName;
	
	private String product;

	private Date created;

	private Long transactionId;

	private Integer productId;

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

	public TransactionHistoryEntity() {
	}
	
	
	@Id
	@Column(name = "id", unique=true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="transaction_history_id_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column(name = "service_name")
	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Basic
	@Column(name = "product")
	public String getProduct() {
		return product;
	}


	public void setProduct(String product) {
		this.product = product;
	}
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, nullable = false)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Column(name = "tid")
	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	

	@Column(name = "prd_id")
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "sub_id")
	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	@Basic
	@Column(name = "inv_id")
	public String getInvId() {
		return invId;
	}


	public void setInvId(String invId) {
		this.invId = invId;
	}


	@Column(name = "addl")
	public String getAdditionalData() {
		return this.additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	@Column(name = "amt")
	public Integer getAmt() {
		return amt;
	}

	public void setAmt(Integer amt) {
		this.amt = amt;
	}

	@Column(name = "curr")
	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	@Column(name = "trn_dat")
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name = "trn_hou")
	public String getTransactionHour() {
		return transactionHour;
	}

	public void setTransactionHour(String transactionHour) {
		this.transactionHour = transactionHour;
	}

	@Column(name = "cm_amt")
	public Integer getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(Integer commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	@Column(name = "cm_curr")
	public String getCommissionCurr() {
		return commissionCurr;
	}

	public void setCommissionCurr(String commissionCurr) {
		this.commissionCurr = commissionCurr;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Basic
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	@Column(name = "informed")
	public Boolean getInformed() {
		return informed;
	}

	public void setInformed(Boolean informed) {
		this.informed = informed;
	}

	@Column(name = "biller_data")
	public String getBillerData() {
		return this.billerData;
	}

	public void setBillerData(String billerData) {
		this.billerData = billerData;
	}
	
	@Override
	public String toString() {
		return "TransactionHistoryEntity [id=" + id + ", created=" + created
				+ ", transactionId=" + transactionId + ", productId="
				+ productId + ", subId=" + subId + ", additionalData="
				+ additionalData + ", amt=" + amt + ", curr=" + curr
				+ ", transactionDate=" + transactionDate + ", transactionHour="
				+ transactionHour + ", commissionAmt=" + commissionAmt
				+ ", commissionCurr=" + commissionCurr + ", type=" + type
				+ ", status=" + status 
				+ ", billerData=" + billerData + "]";
	}
}
