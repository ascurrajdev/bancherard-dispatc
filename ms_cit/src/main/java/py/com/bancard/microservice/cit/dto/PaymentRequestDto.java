package py.com.bancard.microservice.cit.dto;

public class PaymentRequestDto {

	private String usuario; 
	private String password;
	private int nroOperacion; 
	private int nroCuota;
	private String importe;
	private String moneda;
	private long codTransaccion;
	private String anhomes;
	private String fechapago;
	private int anulado;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getNroOperacion() {
		return nroOperacion;
	}
	public void setNroOperacion(int nroOperacion) {
		this.nroOperacion = nroOperacion;
	}
	public int getNroCuota() {
		return nroCuota;
	}
	public void setNroCuota(int nroCuota) {
		this.nroCuota = nroCuota;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public long getCodTransaccion() {
		return codTransaccion;
	}
	public void setCodTransaccion(long codTransaccion) {
		this.codTransaccion = codTransaccion;
	}
	public String getAnhomes() {
		return anhomes;
	}
	public void setAnhomes(String anhomes) {
		this.anhomes = anhomes;
	}
	public String getFechapago() {
		return fechapago;
	}
	public void setFechapago(String fechapago) {
		this.fechapago = fechapago;
	}
	public int getAnulado() {
		return anulado;
	}
	public void setAnulado(int anulado) {
		this.anulado = anulado;
	}
	@Override
	public String toString() {
		return "PaymentRequestDto [usuario=" + usuario + ", password=" + password + ", nroOperacion=" + nroOperacion
				+ ", nroCuota=" + nroCuota + ", importe=" + importe + ", moneda=" + moneda + ", codTransaccion="
				+ codTransaccion + ", anhomes=" + anhomes + ", fechapago=" + fechapago + ", anulado=" + anulado + "]";
	}
	
	public String getIdentity(){
        String cad = this.nroOperacion + "-"+this.nroCuota+ "-"+this.importe + "-"+this.moneda;
        return cad;
    }
	
}