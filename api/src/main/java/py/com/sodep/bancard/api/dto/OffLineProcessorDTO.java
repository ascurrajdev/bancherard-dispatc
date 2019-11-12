package py.com.sodep.bancard.api.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import py.com.sodep.bancard.api.dto.serializer.DateTimeDeserializer;
import py.com.sodep.bancard.api.dto.serializer.DateTimeSerializer;
import py.com.sodep.bancard.api.enums.OfflineProcessor;

@JsonPropertyOrder({ "id", "productUid", "processDatetime", "fileName", "recordsInFile", "recordsInserted", "status"})
public class OffLineProcessorDTO implements Serializable {

	private static final long serialVersionUID = -7739313353398184216L;

	private BigInteger id;
	private Integer productUid;
	@JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
	private Date processDatetime;
	private String fileName;
	private Integer recordsInFile;
	private Integer recordsInserted;
	private OfflineProcessor status;

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

	public Date getProcessDatetime() {
		return processDatetime;
	}

	public void setProcessDatetime(Date processDatetime) {
		this.processDatetime = processDatetime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getRecordsInFile() {
		return recordsInFile;
	}

	public void setRecordsInFile(Integer recordsInFile) {
		this.recordsInFile = recordsInFile;
	}

	public Integer getRecordsInserted() {
		return recordsInserted;
	}

	public void setRecordsInserted(Integer recordsInserted) {
		this.recordsInserted = recordsInserted;
	}

	public OfflineProcessor getStatus() {
		return status;
	}

	public void setStatus(OfflineProcessor status) {
		this.status = status;
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
		return "OffLineProcessorDTO [id=" + id + ", productUid=" + productUid + ", processDatetime=" + processDatetime
				+ ", fileName=" + fileName + ", recordsInFile=" + recordsInFile + ", recordsInserted=" + recordsInserted
				+ ", status=" + status + ", additionalProperties=" + additionalProperties + "]";
	}
}