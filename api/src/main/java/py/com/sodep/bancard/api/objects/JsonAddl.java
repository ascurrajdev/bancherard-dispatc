package py.com.sodep.bancard.api.objects;

import java.util.List;

import py.com.sodep.bancard.api.enums.PaymentMethod;

public class JsonAddl {
	private Integer cmr_id;	
	private Integer cmr_bra;
	private List<String> srv_dta;
	private PaymentMethod payment_method;

	public Integer getCmr_id() {
		return cmr_id;
	}
	public void setCmr_id(Integer cmr_id) {
		this.cmr_id = cmr_id;
	}
	public Integer getCmr_bra() {
		return cmr_bra;
	}
	public void setCmr_bra(Integer cmr_bra) {
		this.cmr_bra = cmr_bra;
	}
	public List<String> getSrv_dta() {
		return srv_dta;
	}
	public void setSrv_dta(List<String> srv_dta) {
		this.srv_dta = srv_dta;
	}
	public PaymentMethod getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(PaymentMethod payment_method) {
		this.payment_method = payment_method;
	}

	@Override
	public String toString() {
		return "JsonAddl [cmr_id=" + cmr_id + ", cmr_bra=" + cmr_bra
				+ ", srv_dta=" + srv_dta + ", payment_method=" + payment_method
				+ "]";
	}
}
