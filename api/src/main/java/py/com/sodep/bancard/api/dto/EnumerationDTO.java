package py.com.sodep.bancard.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "productUid", "cod", "numericValue", "description"})
public class EnumerationDTO implements Serializable {

	private static final long serialVersionUID = 8179595456655673837L;

	private Integer id;
	private Integer productUid;
	private String cod;
	private long numericValue;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductUid() {
		return productUid;
	}

	public void setProductUid(Integer productUid) {
		this.productUid = productUid;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public long getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(long numericValue) {
		this.numericValue = numericValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "EnumerationDTO [id=" + id + ", productUid=" + productUid + ", cod=" + cod + ", numericValue="
				+ numericValue + ", description=" + description + "]";
	}	
}