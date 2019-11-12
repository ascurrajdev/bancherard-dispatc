/**
 * 
 */
package py.com.sodep.bancard.api.microservices;

/**
 * Bancard Microservice's implementations should use this class to report a
 * problem that happens inside the implementation.
 * <p>
 * Instances of this classes are not problems defined in Bancard REST API, for
 * that purpose see {@link BancardAPIException}.
 *
 */
public class BancardMSException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6932985398861805276L;

	private String key;

	/**
	 * El nombre del microservicio que falló
	 */
	private String failedMicroService;
	
	
	/**
	 * 
	 */
	public BancardMSException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BancardMSException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public BancardMSException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	// FIXME eliminar constructores que no se usan para estandarizar la forma en
	// como se lanzan estas excepciones
	/**
	 * @param message
	 * @param cause
	 */
	public BancardMSException(String message, Throwable cause) {
		super(message, cause);
	}

	public BancardMSException(Throwable cause, String key) {
		super(cause);
		this.key = key;
	}

	// FIXME capaz se puede tener un constructor mas simple y que reciba los
	// keys que están en MessageKey
	// FIXME No estoy seguro si los key de MessageKey son los errores posibles
	// de excepciones
	public BancardMSException(String message, Throwable cause, String key) {
		super(message, cause);
		this.key = key;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BancardMSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setFailedMicroService(String name) {
		this.failedMicroService = name;
	}
	
	public String getFailedMicroService() {
		return this.failedMicroService;
	}

	@Override
	public String toString() {
		return "BancardMSException [key=" + key + ", failedMicroService="
				+ failedMicroService + ", getMessage()=" + getMessage() + "]";
	}
}
