package py.com.sodep.bancard.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import py.com.sodep.bancard.api.enums.BehaviorPeriodicity;
import py.com.sodep.bancard.api.enums.OffLineFileType;
import py.com.sodep.bancard.api.enums.OffLineType;

@JsonPropertyOrder({ "id", "productId", "productUid", "microservicesId", "commerceId", "commerceBranchId", "name",
		"sendEmail", "periodicity", "mult", "customSending", "mailFileHeader", "mailFileSeparator", "addlDataInJsonFormat", "prntMsg",
		"msOffline", "offlineType", "offlineFileType", "offlineNumberOfFields", "offlineFileStructureInJson",
		"offlineBehaviorComplementsInJson", "createdAt", "updatedAt" })
public class BehaviorDTO {
	private BigInteger id;
	private Integer productId;
	private Integer productUid;
	private Integer microservicesId;
	private Integer commerceId;
	private Integer commerceBranchId;
	private String name;
	private boolean sendEmail;
	private BehaviorPeriodicity periodicity;
	private boolean mult;
	private boolean customSending;
	private String mailFileHeader;
	private String mailFileSeparator;
	private String addlDataInJsonFormat;
	private String prntMsg;
	private boolean msOffline;
	private OffLineType offlineType;
	private OffLineFileType offlineFileType;
	private Integer offlineNumberOfFields;
	private List<BehaviorOfflineFileStructureInJson> offlineFileStructureInJson;
	private OfflineBehaviorComplementsInJson offlineBehaviorComplementsInJson; 
	private Date createdAt;
	private Date updatedAt;

	@JsonIgnore
	public boolean isNew() {
		return (this.id == null);
	}

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductUid() {
		return productUid;
	}

	public void setProductUid(Integer productUid) {
		this.productUid = productUid;
	}

	public Integer getMicroservicesId() {
		return microservicesId;
	}

	public void setMicroservicesId(Integer microservicesId) {
		this.microservicesId = microservicesId;
	}

	public Integer getCommerceId() {
		return commerceId;
	}

	public void setCommerceId(Integer commerceId) {
		this.commerceId = commerceId;
	}

	public Integer getCommerceBranchId() {
		return commerceBranchId;
	}

	public void setCommerceBranchId(Integer commerceBranchId) {
		this.commerceBranchId = commerceBranchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public BehaviorPeriodicity getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(BehaviorPeriodicity periodicity) {
		this.periodicity = periodicity;
	}

	public boolean getMult() {
		return mult;
	}

	public void setMult(boolean mult) {
		this.mult = mult;
	}

	public boolean getCustomSending() {
		return customSending;
	}

	public void setCustomSending(boolean customSending) {
		this.customSending = customSending;
	}

	public String getMailFileHeader() {
		return mailFileHeader;
	}

	public void setMailFileHeader(String mailFileHeader) {
		this.mailFileHeader = mailFileHeader;
	}

	public String getMailFileSeparator() {
		return mailFileSeparator;
	}

	public void setMailFileSeparator(String mailFileSeparator) {
		this.mailFileSeparator = mailFileSeparator;
	}

	public String getAddlDataInJsonFormat() {
		return addlDataInJsonFormat;
	}

	public void setAddlDataInJsonFormat(String addlDataInJsonFormat) {
		this.addlDataInJsonFormat = addlDataInJsonFormat;
	}

	public String getPrntMsg() {
		return prntMsg;
	}

	public void setPrntMsg(String prntMsg) {
		this.prntMsg = prntMsg;
	}

	public boolean getMsOffline() {
		return msOffline;
	}

	public void setMsOffline(boolean msOffline) {
		this.msOffline = msOffline;
	}

	public OffLineType getOfflineType() {
		return offlineType;
	}

	public void setOfflineType(OffLineType offlineType) {
		this.offlineType = offlineType;
	}

	public OffLineFileType getOfflineFileType() {
		return offlineFileType;
	}

	public void setOfflineFileType(OffLineFileType offlineFileType) {
		this.offlineFileType = offlineFileType;
	}

	public Integer getOfflineNumberOfFields() {
		return offlineNumberOfFields;
	}

	public void setOfflineNumberOfFields(Integer offlineNumberOfFields) {
		this.offlineNumberOfFields = offlineNumberOfFields;
	}

	public List<BehaviorOfflineFileStructureInJson> getOfflineFileStructureInJson() {
		return offlineFileStructureInJson;
	}

	public void setOfflineFileStructureInJson(List<BehaviorOfflineFileStructureInJson> offlineFileStructureInJson) {
		this.offlineFileStructureInJson = offlineFileStructureInJson;
	}

	public OfflineBehaviorComplementsInJson getOfflineBehaviorComplementsInJson() {
		return offlineBehaviorComplementsInJson;
	}

	public void setOfflineBehaviorComplementsInJson(OfflineBehaviorComplementsInJson offlineBehaviorComplementsInJson) {
		this.offlineBehaviorComplementsInJson = offlineBehaviorComplementsInJson;
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
		return "BehaviorDTO [id=" + id + ", productId=" + productId + ", productUid=" + productUid
				+ ", microservicesId=" + microservicesId + ", commerceId=" + commerceId + ", commerceBranchId="
				+ commerceBranchId + ", name=" + name + ", sendEmail=" + sendEmail + ", periodicity=" + periodicity
				+ ", mult=" + mult + ", customSending=" + customSending + ", mailFileHeader=" + mailFileHeader
				+ ", mailFileSeparator=" + mailFileSeparator + ", addlDataInJsonFormat=" + addlDataInJsonFormat
				+ ", prntMsg=" + prntMsg + ", msOffline=" + msOffline + ", offlineType=" + offlineType
				+ ", offlineFileType=" + offlineFileType + ", offlineNumberOfFields=" + offlineNumberOfFields
				+ ", offlineFileStructureInJson=" + offlineFileStructureInJson + ", offlineBehaviorComplementsInJson="
				+ offlineBehaviorComplementsInJson + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", additionalProperties=" + additionalProperties + "]";
	}
}