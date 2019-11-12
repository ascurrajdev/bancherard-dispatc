package py.com.bancard.microservice.cit.dto;

import java.math.BigDecimal;
import java.util.Date;

public class InvResponseDto {

	private int nroOperacion;
	private String desOperacion;
	private int nroCuota;
	private String capital;
	private String interes;
	private String mora;
	private String punitorio;
	private String gastos;
	private String iva10;
	private String iva5;
	private String totalDeuda;
	private String moneda;
	private Date fechaVencimiento;
	private String apenom;
	public int getNroOperacion() {
		return nroOperacion;
	}
	public void setNroOperacion(int nroOperacion) {
		this.nroOperacion = nroOperacion;
	}
	public String getDesOperacion() {
		return desOperacion;
	}
	public void setDesOperacion(String desOperacion) {
		this.desOperacion = desOperacion;
	}
	public int getNroCuota() {
		return nroCuota;
	}
	public void setNroCuota(int nroCuota) {
		this.nroCuota = nroCuota;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getInteres() {
		return interes;
	}
	public void setInteres(String interes) {
		this.interes = interes;
	}
	public String getMora() {
		return mora;
	}
	public void setMora(String mora) {
		this.mora = mora;
	}
	public String getPunitorio() {
		return punitorio;
	}
	public void setPunitorio(String punitorio) {
		this.punitorio = punitorio;
	}
	public String getGastos() {
		return gastos;
	}
	public void setGastos(String gastos) {
		this.gastos = gastos;
	}
	public String getIva10() {
		return iva10;
	}
	public void setIva10(String iva10) {
		this.iva10 = iva10;
	}
	public String getIva5() {
		return iva5;
	}
	public void setIva5(String iva5) {
		this.iva5 = iva5;
	}
	public String getTotalDeuda() {
		return totalDeuda;
	}
	public void setTotalDeuda(String totalDeuda) {
		this.totalDeuda = totalDeuda;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getApenom() {
		return apenom;
	}
	public void setApenom(String apenom) {
		this.apenom = apenom;
	}



}

