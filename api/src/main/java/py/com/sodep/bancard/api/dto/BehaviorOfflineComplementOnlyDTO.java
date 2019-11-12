package py.com.sodep.bancard.api.dto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "productUid", "name", "offlineBehaviorComplementsInJson" })
public class BehaviorOfflineComplementOnlyDTO {
	private BigInteger id;
	private Integer productUid;
	private String name;
	private OfflineBehaviorComplementsInJson offlineBehaviorComplementsInJson;

	@JsonIgnore
	public boolean isNew() {
		return (this.id == null);
	}

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

	public OfflineBehaviorComplementsInJson getOfflineBehaviorComplementsInJson() {
		return offlineBehaviorComplementsInJson;
	}

	public void setOfflineBehaviorComplementsInJson(OfflineBehaviorComplementsInJson offlineBehaviorComplementsInJson) {
		this.offlineBehaviorComplementsInJson = offlineBehaviorComplementsInJson;
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
		return "BehaviorOfflineComplementOnlyDTO [id=" + id + ", productUid=" + productUid + ", name=" + name
				+ ", offlineBehaviorComplementsInJson=" + offlineBehaviorComplementsInJson + ", additionalProperties="
				+ additionalProperties + "]";
	}
}