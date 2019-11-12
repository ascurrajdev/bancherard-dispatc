package py.com.bancard.microservice.cit.client;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;

import py.com.bancard.microservice.cit.dto.Const;
import py.com.bancard.microservice.cit.dto.InvRequestDto;
import py.com.bancard.microservice.cit.dto.InvResponseDto;
import py.com.bancard.microservice.cit.dto.PaymentRequestDto;
import py.com.bancard.microservice.cit.dto.ReverseRequestDto;
import py.com.bancard.microservice.cit.dto.TxResponseDto;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.HipChatHelper;

public class CitClient {
	private static final Logger logger = LoggerFactory.getLogger(CitClient.class);
	public static final String INDENT_UNIT = "\t";
	
	private HipChatHelper hipChatHelper = new HipChatHelper();
	private JdbcTemplate jdbcTemplate;

	public CitClient(int timeOut, DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource, true);
		jdbcTemplate.setQueryTimeout(timeOut);
	}

	public List<InvResponseDto> invoices(InvRequestDto request) throws RemoteException, BancardAPIException, SQLException {
		try {
			List<InvResponseDto> response = jdbcTemplate.query(Const.INV_QUERY, new Object[] { request.getDocNumber() }, new InvResponseMapper()); 
			//System.out.println(String.valueOf(jdbcTemplate.getDataSource().getConnection().isClosed()));
			return response;
		} catch (UncategorizedSQLException e) {
			logger.error("Invoices UncategorizedSQLException:", e.getCause());
			hipChatHelper.sendNotificationHipChat("CIT Invoices UncategorizedSQLException: " + request + "\n" + e);
			throw new BancardAPIException(GenericMessageKey.QUERY_NOT_PROCESSED, "Se ha producido un error consultando al Facturador");
		} catch (Exception e) {
			logger.error("Invoices Exception: ", e);
			hipChatHelper.sendNotificationHipChat("CIT Invoices Exception: " + request + "\n" + e);
			throw new RemoteException(e.getMessage(), e.getCause());
		}/* finally {
			if(jdbcTemplate.getDataSource().getConnection() != null) {
				try {
					jdbcTemplate.getDataSource().getConnection().close();
				} catch (SQLException e2) {
					logger.error("Invoices SQLException: {}", e2);
				}
				
			}
		}*/
	}

	public TxResponseDto payment(PaymentRequestDto request) throws RemoteException, BancardAPIException {
		try {
			Object[] objectArray = new Object[] { request.getUsuario(), request.getPassword(), request.getNroOperacion() , request.getNroCuota(), request.getImporte(), request.getMoneda(), request.getCodTransaccion(), request.getAnhomes(), request.getFechapago(), request.getAnulado() };
			
			int insert=0;
			insert = jdbcTemplate.update(Const.PAYMENT_SQL, objectArray);
			
			TxResponseDto response = new TxResponseDto();
			
			if(insert==1)
			{
				response.setCodigoError("000");
				response.setDescripcionError("Pago Exitoso");
			}
			else{
				response.setCodigoError("001");
				response.setDescripcionError("Error en Pago");
			}
			
			return response;
		} catch (UncategorizedSQLException e) {
			logger.error("Payment UncategorizedSQLException:", e.getCause());
			hipChatHelper.sendNotificationHipChat("CIT Payment UncategorizedSQLException: " + request + "\n" + e);
			throw new BancardAPIException(GenericMessageKey.PAYMENT_NOT_AUTHORIZED, "El pago no pudo ser procesado");
		} catch (Exception e) {
			logger.error("Payment Exception", e);
			hipChatHelper.sendNotificationHipChat("CIT Payment Exception: " + request + "\n" + e);
			throw new RemoteException(e.getMessage(), e.getCause());
		}
	}

	public TxResponseDto reverse(ReverseRequestDto request) throws RemoteException, BancardAPIException {
		try {
			
			Object[] objectArray1 = new Object[] {request.getCodTransaccion(), request.getUsuario()};
			
			Object[] objectArray2 = new Object[] {request.getUsuario(), request.getPassword(), request.getNroOperacion() , request.getNroCuota(), request.getImporte(), request.getMoneda(), request.getCodTransaccion(), request.getCodTransaccionAnular(), request.getFechaanulacion()};
			
			int insert1=0;
			int insert2=0;
			
			//Primero se cambia un valor en la tabla "pagos", el valor de "anulado" pasa de 0 a 1
			insert1 = jdbcTemplate.update(Const.REVERSE_SQL1, objectArray1);
			
			//Segundo, Si el update se ejecutó correctamente, se inserta un registro en la tabla "anulacion"
			if(insert1 == 1)
				insert2 = jdbcTemplate.update(Const.REVERSE_SQL2, objectArray2);
			
			TxResponseDto response = new TxResponseDto();
			
			if(insert2==1)
			{
				response.setCodigoError("000");
				response.setDescripcionError("Reversa Exitosa");
			}
			else{
				response.setCodigoError("001");
				response.setDescripcionError("Error en Reversa");
			}
			
			return response;
		} catch (UncategorizedSQLException e) {
			logger.error("Reverse UncategorizedSQLException:", e.getCause());
			hipChatHelper.sendNotificationHipChat("CIT Reverse UncategorizedSQLException: " + request + "\n" + e);
			throw new BancardAPIException(GenericMessageKey.TRANSACTION_NOT_REVERSED, "La reversa no pudo ser procesada");
		} catch (Exception e) {
			logger.error("Reverse Exception", e);
			hipChatHelper.sendNotificationHipChat("CIT Reverse Exception: " + request + "\n" + e);
			throw new RemoteException(e.getMessage(), e.getCause());
		}
	}

	public String printInvoicesResponse(List<InvResponseDto> response) {
		if (response == null) {
			return String.valueOf(response);
		}

		if (response.isEmpty()) {
			return String.valueOf(response);
		}

		if (response.size() == 0) {
			return String.valueOf(response);
		}

		StringBuilder sb = new StringBuilder();
		Iterator<InvResponseDto> itr = response.iterator();
		boolean primerRegistro = true;
		sb.append("{ \n");
		sb.append(INDENT_UNIT).append("FACTURAS: \n");
		while (itr.hasNext()) {
			InvResponseDto factura = itr.next();
			if (!primerRegistro)
				sb.append(INDENT_UNIT).append("\t'########    ########'").append("\n");
			sb.append(INDENT_UNIT).append("\t'NRO DE SOCIO': '").append(factura.getNroOperacion()).append("', \n");
			sb.append(INDENT_UNIT).append("\t'APELLIDO Y NOMBRE': '").append(factura.getApenom()).append("', \n");
			sb.append(INDENT_UNIT).append("\t'DESCRIPCION_OPERACION': '").append(factura.getDesOperacion()).append("', \n");
			sb.append(INDENT_UNIT).append("\t'NRO CUOTA': '").append(factura.getNroCuota()).append("', \n");
			sb.append(INDENT_UNIT).append("\t'TOTAL DEUDA': '").append(factura.getTotalDeuda()).append("', \n");
			
			if (primerRegistro)
				primerRegistro = false;
		}
		sb.append("}");
		return sb.toString();
	}

	public String printTxResponse(TxResponseDto responseList) {
		if (responseList == null){
			return String.valueOf(new TxResponseDto());
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{ \n");
		sb.append(INDENT_UNIT).append("'COD_ERROR': '").append(responseList.getCodigoError()).append("', \n");
		sb.append(INDENT_UNIT).append("'DSC_ERROR': '").append(responseList.getDescripcionError()).append("', \n");
		sb.append("}");
		return sb.toString();
	}
}