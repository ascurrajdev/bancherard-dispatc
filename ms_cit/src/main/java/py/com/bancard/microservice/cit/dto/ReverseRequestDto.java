package py.com.bancard.microservice.cit.dto;


public class ReverseRequestDto {

	private String usuario; 
	private String password;
	private int nroOperacion; 
	private int nroCuota;
	private String importe;
	private String moneda;
	private long codTransaccion;
	private long codTransaccionAnular;
	private String fechaanulacion;
	
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
	public long getCodTransaccionAnular() {
		return codTransaccionAnular;
	}
	public void setCodTransaccionAnular(long codTransaccionAnular) {
		this.codTransaccionAnular = codTransaccionAnular;
	}
	public String getFechaanulacion() {
		return fechaanulacion;
	}
	public void setFechaanulacion(String fechaanulacion) {
		this.fechaanulacion = fechaanulacion;
	}
	@Override
	public String toString() {
		return "ReverseRequestDto [usuario=" + usuario + ", password=" + password + ", nroOperacion=" + nroOperacion
				+ ", nroCuota=" + nroCuota + ", importe=" + importe + ", moneda=" + moneda + ", codTransaccion="
				+ codTransaccion + ", codTransaccionAnular=" + codTransaccionAnular + ", fechaanulacion="
				+ fechaanulacion + "]";
	}
	
	

	
}