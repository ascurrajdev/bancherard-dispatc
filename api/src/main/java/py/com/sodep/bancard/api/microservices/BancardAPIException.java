/**
 * 
 */
package py.com.sodep.bancard.api.microservices;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.enums.ParticularMessageKey;

/**
 * Instances of this class are exceptions that have a special meaning in
 * Bancard's REST API.
 * <p>
 * This exception should be handled by clients of {@link BancardMS}, and based
 * on the {@link #key} the appropiate response should be reported back to Bancard's
 * REST API clients.
 *
 */
public class BancardAPIException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4874223099605192065L;


	/**
	 * This is a name assigned to the exception by Bancard REST API.
	 * <p>
	 * Example: QueryProcessed
	 */
	private String key;
	
	
	/**
	 * Number identifying the exception
	 */
	private String errorCode;

	
	/**
	 * 
	 */
	public BancardAPIException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public BancardAPIException(Throwable cause) {
		super(cause);
	}

	public BancardAPIException(String message) {
		super(message);
	}

	public BancardAPIException(String message, Throwable cause) {
		super(message, cause);
	}

	public BancardAPIException(GenericMessageKey messageKey) {
		super(messageKey.getDesc());
		this.setKey(messageKey.getKey());
	}

	public BancardAPIException(GenericMessageKey messageKey, String msg) {
		super(msg);
		this.setKey(messageKey.getKey());
	}

	public BancardAPIException(GenericMessageKey messageKey, Throwable cause) {
		super(messageKey.getDesc(), cause);
		this.setKey(messageKey.getKey());
	}

	public BancardAPIException(ParticularMessageKey messageKey, Throwable cause, String msg) {
		super(msg, cause);
		this.setKey(messageKey.getKey());
	}

	public BancardAPIException(ParticularMessageKey messageKey, String msg) {
		super(msg);
		this.setKey(messageKey.getKey());
		this.setErrorCode(messageKey.getLevel());
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String toString() {
		return "BancardAPIException [key=" + key + ", errorCode=" + errorCode
				+ ", getMessage()=" + getMessage() + "]";
	}
}
