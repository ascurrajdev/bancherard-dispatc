package py.com.sodep.bancard.api.dto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import py.com.sodep.bancard.api.enums.OffLineFileType;
import py.com.sodep.bancard.api.enums.OffLineType;

@JsonPropertyOrder({ "id", "productUid", "name", "msOffline", "offlineType", "offlineFileType", "offlineNumberOfFields",
		"offlineFileStructureInJson", "offlineBehaviorComplementsInJson" })
public class BehaviorOfflineOnlyDTO {
	private BigInteger id;
	private Integer productUid;
	private String name;
	private Boolean msOffline;
	private OffLineType offlineType;
	private OffLineFileType offlineFileType;
	private Integer offlineNumberOfFields;
	private List<BehaviorOfflineFileStructureInJson> offlineFileStructureInJson;
	private OfflineBehaviorComplementsInJson offlineBehaviorComplementsInJson;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getProductUid() {
		return productUid;
	}

	public void setProductUid(Integer productUid) {
		this.productUid = productUid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMsOffline() {
		return msOffline;
	}

	public void setMsOffline(Boolean msOffline) {
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

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	@Override
	public String toString() {
		return "BehaviorOfflineOnlyDTO [id=" + id + ", productUid=" + productUid + ", name=" + name + ", msOffline="
				+ msOffline + ", offlineType=" + offlineType + ", offlineFileType=" + offlineFileType
				+ ", offlineNumberOfFields=" + offlineNumberOfFields + ", offlineFileStructureInJson="
				+ offlineFileStructureInJson + ", offlineBehaviorComplementsInJson=" + offlineBehaviorComplementsInJson
				+ ", additionalProperties=" + additionalProperties + "]";
	}
}