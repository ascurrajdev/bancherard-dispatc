package py.com.sodep.bancard.api.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import py.com.sodep.bancard.api.enums.BehaviorComplementFineType;

@JsonPropertyOrder({ "readFromTheLine", "fineType", "fineForUnpaid", "numberOfRecordsOfNextDue",
		"amountOfFeesToGeneratePerRecord", "billerDateFormatt", "startFirstInstallment", "defaultMinAmount",
		"delimited", "cancelPreviousOfflineBillsUnpaid" })
public class OfflineBehaviorComplementsInJson {

	private int readFromTheLine;
	private BehaviorComplementFineType fineType;
	private Double fineForUnpaid;
	private int numberOfRecordsOfNextDue;
	private int amountOfFeesToGeneratePerRecord;
	private String startFirstInstallment;
	private String billerDateFormatt;
	private BigDecimal defaultMinAmount;
	private String delimited;
	private boolean cancelPreviousOfflineBillsUnpaid;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public int getReadFromTheLine() {
		return readFromTheLine;
	}

	public void setReadFromTheLine(int readFromTheLine) {
		this.readFromTheLine = readFromTheLine;
	}

	public BehaviorComplementFineType getFineType() {
		return fineType;
	}

	public void setFineType(BehaviorComplementFineType fineType) {
		this.fineType = fineType;
	}

	public Double getFineForUnpaid() {
		return fineForUnpaid;
	}

	public void setFineForUnpaid(Double fineForUnpaid) {
		this.fineForUnpaid = fineForUnpaid;
	}

	public int getNumberOfRecordsOfNextDue() {
		return numberOfRecordsOfNextDue;
	}

	public void setNumberOfRecordsOfNextDue(int numberOfRecordsOfNextDue) {
		this.numberOfRecordsOfNextDue = numberOfRecordsOfNextDue;
	}

	public int getAmountOfFeesToGeneratePerRecord() {
		return amountOfFeesToGeneratePerRecord;
	}

	public void setAmountOfFeesToGeneratePerRecord(int amountOfFeesToGeneratePerRecord) {
		this.amountOfFeesToGeneratePerRecord = amountOfFeesToGeneratePerRecord;
	}

	public String getStartFirstInstallment() {
		return startFirstInstallment;
	}

	public void setStartFirstInstallment(String startFirstInstallment) {
		this.startFirstInstallment = startFirstInstallment;
	}

	public String getBillerDateFormatt() {
		return billerDateFormatt;
	}

	public void setBillerDateFormatt(String billerDateFormatt) {
		this.billerDateFormatt = billerDateFormatt;
	}

	public BigDecimal getDefaultMinAmount() {
		return defaultMinAmount;
	}

	public void setDefaultMinAmount(BigDecimal defaultMinAmount) {
		this.defaultMinAmount = defaultMinAmount;
	}

	public String getDelimited() {
		return delimited;
	}

	public void setDelimited(String delimited) {
		this.delimited = delimited;
	}

	public boolean isCancelPreviousOfflineBillsUnpaid() {
		return cancelPreviousOfflineBillsUnpaid;
	}

	public void setCancelPreviousOfflineBillsUnpaid(boolean cancelPreviousOfflineBillsUnpaid) {
		this.cancelPreviousOfflineBillsUnpaid = cancelPreviousOfflineBillsUnpaid;
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
		return "OfflineBehaviorComplementsInJson [readFromTheLine=" + readFromTheLine + ", fineType=" + fineType
				+ ", fineForUnpaid=" + fineForUnpaid + ", numberOfRecordsOfNextDue=" + numberOfRecordsOfNextDue
				+ ", amountOfFeesToGeneratePerRecord=" + amountOfFeesToGeneratePerRecord + ", startFirstInstallment="
				+ startFirstInstallment + ", billerDateFormatt=" + billerDateFormatt + ", defaultMinAmount="
				+ defaultMinAmount + ", delimited=" + delimited + ", cancelPreviousOfflineBillsUnpaid="
				+ cancelPreviousOfflineBillsUnpaid + ", additionalProperties=" + additionalProperties + "]";
	}
}