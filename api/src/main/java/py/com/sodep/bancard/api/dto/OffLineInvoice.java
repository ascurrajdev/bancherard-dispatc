package py.com.sodep.bancard.api.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import py.com.sodep.bancard.api.enums.OffLineType;
import py.com.sodep.bancard.api.objects.Invoice;

public class OffLineInvoice extends Invoice {

	private Integer productUid;
	private List<String> subId = new ArrayList<String>();
	private OffLineType offlineType;
	private String[] offlineFileRow;

	public Integer getProductUid() {
		return productUid;
	}
	public void setProductUid(Integer productUid) {
		this.productUid = productUid;
	}
	public List<String> getSubId() {
		return subId;
	}
	public void setSubId(List<String> subId) {
		this.subId = subId;
	}
	public OffLineType getOfflineType() {
		return offlineType;
	}
	public void setOfflineType(OffLineType offlineType) {
		this.offlineType = offlineType;
	}
	public String[] getOfflineFileRow() {
		return offlineFileRow;
	}
	public void setOfflineFileRow(String[] offlineFileRow) {
		this.offlineFileRow = offlineFileRow;
	}

	@Override
	public String toString() {
		return "OffLineInvoice [productUid=" + productUid + ", subId=" + subId + ", offlineType=" + offlineType
				+ ", offlineFileRow=" + Arrays.toString(offlineFileRow) + ", getDue()=" + getDue() + ", getAmount()="
				+ getAmount() + ", getMinAmount()=" + getMinAmount() + ", getInvId()=" + getInvId() + ", getCurrency()="
				+ getCurrency() + ", getAdditionalData()=" + getAdditionalData() + ", getNextDues()=" + getNextDues()
				+ ", getCommissionAmount()=" + getCommissionAmount() + ", getCommissionCurrency()="
				+ getCommissionCurrency() + ", getDescription()=" + getDescription() + ", getAdditionalProperties()="
				+ getAdditionalProperties() + "]";
	}
}