package py.com.sodep.bancard.api.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import py.com.sodep.bancard.api.enums.AttributeDataType;
import py.com.sodep.bancard.api.enums.InvoicesFields;

@JsonPropertyOrder({ "attributePosition", "required", "invEquivalentField", "attributeName", "attributeDataType",
		"attributeLength", "attributePrecision", "attributeScale" })
public class BehaviorOfflineFileStructureInJson {

	private Integer attributePosition;
	private boolean required;
	private InvoicesFields invEquivalentField;
	private String attributeName;
	private AttributeDataType attributeDataType;
	private Integer attributeLength;
	private Integer attributePrecision;
	private Integer attributeScale;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getAttributePosition() {
		return attributePosition;
	}

	public void setAttributePosition(Integer attributePosition) {
		this.attributePosition = attributePosition;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public InvoicesFields getInvEquivalentField() {
		return invEquivalentField;
	}

	public void setInvEquivalentField(InvoicesFields invEquivalentField) {
		this.invEquivalentField = invEquivalentField;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public AttributeDataType getAttributeDataType() {
		return attributeDataType;
	}

	public void setAttributeDataType(AttributeDataType attributeDataType) {
		this.attributeDataType = attributeDataType;
	}

	public Integer getAttributeLength() {
		return attributeLength;
	}

	public void setAttributeLength(Integer attributeLength) {
		this.attributeLength = attributeLength;
	}

	public Integer getAttributePrecision() {
		return attributePrecision;
	}

	public void setAttributePrecision(Integer attributePrecision) {
		this.attributePrecision = attributePrecision;
	}

	public Integer getAttributeScale() {
		return attributeScale;
	}

	public void setAttributeScale(Integer attributeScale) {
		this.attributeScale = attributeScale;
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
		return "BehaviorOfflineFileStructureInJson [attributePosition=" + attributePosition + ", required=" + required
				+ ", invEquivalentField=" + invEquivalentField + ", attributeName=" + attributeName
				+ ", attributeDataType=" + attributeDataType + ", attributeLength=" + attributeLength
				+ ", attributePrecision=" + attributePrecision + ", attributeScale=" + attributeScale
				+ ", additionalProperties=" + additionalProperties + "]";
	}
}