package py.com.sodep.bancard.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "description", "amount", "minAmt", "maxAmt", "type", "behaviorId", "cantidad" })
public class PlanDTO implements Serializable {

	private static final long serialVersionUID = -2293044870624180634L;

	private BigInteger id;
	private String description;
	private BigDecimal amount;
	private BigDecimal minAmt;
	private BigDecimal maxAmt;
	private String type;
	private BigInteger behaviorId;
	private Integer cantidad;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public BigInteger getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}

	public BigDecimal getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigInteger getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(BigInteger behaviorId) {
		this.behaviorId = behaviorId;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void setId(BigInteger id) {
		this.id = id;
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
		return "PlanDTO [id=" + id + ", description=" + description + ", amount=" + amount + ", minAmt=" + minAmt
				+ ", maxAmt=" + maxAmt + ", type=" + type + ", behaviorId=" + behaviorId + ", cantidad=" + cantidad
				+ "]";
	}
}