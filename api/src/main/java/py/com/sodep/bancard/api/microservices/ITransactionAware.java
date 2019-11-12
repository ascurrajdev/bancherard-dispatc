package py.com.sodep.bancard.api.microservices;

/**
 * Implementar esta interfaz para dar acceso al microservicio a la tabla de
 * transacciones del dispatcher.
 *
 */
public interface ITransactionAware {

	/**
	 * Se setea una clase que permite a la implementación del microservicio
	 * tener una API a la tabla transaction_history del Dispatcher.
	 * 
	 * @param provider
	 */
	public void setBancardTransactionProvider(String serviceName, String product, BancardTransactionProvider transactionProvider);
	
}
