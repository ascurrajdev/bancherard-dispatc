package py.com.sodep.bancard.api.microservices;

import java.util.Map;

import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

/**
 * Implementar esta interfaz para agregar la comunicación con los facturadores y
 * realizar consulta de facturas y pagos/reversas.
 * <p>
 * Las respuestas de los facturadores deben ser traducidas a clases del API de Bancard.
 *
 */
//FIXME capaz usar un nombre que tenga mas que ver con facturadores
public interface BancardMS {

	/**
	 * Consulta por WS al facturador la lista de facturas que corresponden
	 * al a la lista de subscriptores 
	 * <p>
	 * @param invoiceQuery contiene lista de id de subscriptores
	 * @return
	 * @throws BancardAPIException 
	 * @throws BancardMSException
	 */
	BancardResponse listInvoices(InvoiceQuery invoiceQuery) throws BancardAPIException;

	/**
	 * Realiza una operación de pago. Implementaciones de este método deberían
	 * escribir la lógica de negocio específica para realizar una petición deu
	 * pago al web service del facturador.
	 * <p>
	 * Implementaciones de este método puede o no realizar una consulta por WS
	 * al facturador. O puede simplemente tener una lógica de validación de
	 * datos de pago como es el caso de la ANDE.
	 * 
	 * @param payment
	 *            clase con información del pago a realizar, esta clase contiene
	 *            tanto el subId como el invId, para los distintos tipos de
	 *            métodos de pago.
	 * @throws BancardAPIException
	 */
	BancardResponse doPayment(Payment payment) throws BancardAPIException;
	
	
	/**
	 * Realiza una operación de reversa.
	 * <p>
	 * Implementaciones de este método deberían escribir la lógica de negocio
	 * específica para realizar una petición de reversa al web service del
	 * facturador.
	 * <p>
	 * 
	 * @param paymentToReverse
	 * @throws BancardAPIException
	 */
	BancardResponse doReverse(Reverse reverse) throws BancardAPIException;

	/**
	 * Inicializa el plugin con la lista de parametros cargados en la tabla
	 * microservices_properties. El metodo es llamado sobre la instancia del
	 * microservicio que se carga.
	 * 
	 * */
	public void initialize(Map<String, String> initParams);
	
	
}
