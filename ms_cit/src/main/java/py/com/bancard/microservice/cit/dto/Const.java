package py.com.bancard.microservice.cit.dto;

public interface Const {
	public static final String INV_QUERY = "select cuotas.* from cuotas left join pagos on (cuotas.nroOperacion = pagos.nroOperacion and cuotas.nroCuota = pagos.nroCuota and CAST(REVERSE(cuotas.desOperacion) AS UNSIGNED)=REVERSE(pagos.anhomes) and pagos.anulado=0) where pagos.nroCuota is null and cuotas.nroOperacion=(?) order by fechaVencimiento limit 1;";
	public static final String PAYMENT_SQL = "insert into pagos (usuario, password, nroOperacion, nroCuota, importe, moneda, codTransaccion, anhomes, fechapago, anulado) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String REVERSE_SQL1 = "update pagos set anulado= '1' where codTransaccion= (?) and usuario=(?)";
	public static final String REVERSE_SQL2 = "insert into anulacion (usuario, password, nroOperacion, nroCuota, importe, moneda, codTransaccion, codTransaccionAnular, fechaanulacion) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String CIT_TX_SUCCESS = "000";
}
