package py.com.sodep.bancard.rest;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import py.com.sodep.bancard.api.objects.JsonAddl;

@XmlRootElement(name = "Payment")
public class JsonPaymentRequest {

	@NotNull(message="El identificador de transacción es requerido")
	private Long tid;
	
	@NotNull(message="El identificador del producto es requerido")
	private Integer prd_id;
	
	@NotNull(message="La propiedad de identificador de subscriptor es requerida")
	@Size(min = 1, message= "Al menos un identificador de subscriptor es requerido")
	private List<String> sub_id;

	@NotNull(message="El monto es requerido")
	private Integer amt;

	@NotNull(message="El tipo de moneda es requerido")
	private String curr;

	@NotNull(message="La fecha de la transacción es requerida")
	private String trn_dat;

	@NotNull(message="La hora de la transacción es requerida")
	private String trn_hou;

	private Integer cm_amt;

	private String cm_curr;

	@NotNull(message="La propiedad de identificador de factura es requerida")
	private List<String> inv_id;

	private String type;
	
	private JsonAddl addl;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getPrd_id() {
		return prd_id;
	}

	public void setPrd_id(Integer prd_id) {
		this.prd_id = prd_id;
	}

	public List<String> getInv_id() {
		return inv_id;
	}

	public void setInv_id(List<String> inv_id) {
		this.inv_id = inv_id;
	}

	public Integer getAmt() {
		return amt;
	}

	public void setAmt(Integer amt) {
		this.amt = amt;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getTrn_dat() {
		return trn_dat;
	}

	public void setTrn_dat(String trn_dat) {
		this.trn_dat = trn_dat;
	}

	public String getTrn_hou() {
		return trn_hou;
	}

	public void setTrn_hou(String trn_hou) {
		this.trn_hou = trn_hou;
	}

	public Integer getCm_amt() {
		return cm_amt;
	}

	public void setCm_amt(Integer cm_amt) {
		this.cm_amt = cm_amt;
	}

	public String getCm_curr() {
		return cm_curr;
	}

	public void setCm_curr(String cm_curr) {
		this.cm_curr = cm_curr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getSub_id() {
		return sub_id;
	}

	public void setSub_id(List<String> sub_id) {
		this.sub_id = sub_id;
	}

	public JsonAddl getAddl() {
		return addl;
	}

	public void setAddl(JsonAddl addl) {
		this.addl = addl;
	}

	@Override
	public String toString() {
		return "JsonPaymentRequest [tid=" + tid + ", prd_id=" + prd_id
				+ ", sub_id=" + sub_id + ", inv_id=" + inv_id + ", amt="
				+ amt + ", curr=" + curr + ", trn_dat=" + trn_dat
				+ ", trn_hou=" + trn_hou + ", cm_amt=" + cm_amt
				+ ", cm_curr=" + cm_curr + ", type=" + type + ", addl="
				+ addl + "]";
	}
}
