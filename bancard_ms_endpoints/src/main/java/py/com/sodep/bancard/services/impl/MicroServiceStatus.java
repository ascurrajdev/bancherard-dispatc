package py.com.sodep.bancard.services.impl;

import java.util.Date;

/**
 * Esta clase conserva el estado de un MicroServicio
 */
public class MicroServiceStatus {
	public static final String PROP_MAX_NUMBER_OF_FAILS = "maxNumberOfFails";
	public static final String PROP_MAX_TIMEOUT= "maxTimeout";
	public static final String PROP_TIMEOUT_IN_MILLISECONDS = "timeoutInMilliseconds";
	
	/**
	 * El número de milisegundos que el sistema debe esperar hasta declarar que
	 * el tiempo de respuesta del MicroServicio expiro
	 **/

	private Long timeoutInMilliseconds;
	/**
	 * La cantidad de veces que un microservicio expiro
	 **/
	private Integer numberOfTimeout;

	/**
	 * La cantidad maxima que una llamada a un microservicio puede expirar antes
	 * de que el servicio sea declarado que está abajo
	 */
	private Integer maxTimeOut;

	/**
	 * La cantidad de vececes que fallo por un error interno del microservicio
	 */
	private Integer numberOfFails;

	/**
	 * La cantidad maxima que puede fallar por errores internos del micro
	 * servicio
	 */
	private Integer maxNumberOfFails;
	
	
	/**
	 * Fecha y hora en formato yyyy-MM-dd HH:mm:ss.SSS
	 * de la última hora y fecha que fue inactivado
	 */
	private Date inactivationTime;

	public MicroServiceStatus() {
		this.numberOfFails = 0;
		this.numberOfTimeout = 0;
	}

	public synchronized Long getTimeoutInMilliseconds() {
		return timeoutInMilliseconds;
	}

	public synchronized void setTimeoutInMilliseconds(Long timeoutInMilliseconds) {
		this.timeoutInMilliseconds = timeoutInMilliseconds;
	}

	public synchronized Integer getNumberOfTimeout() {
		return numberOfTimeout;
	}

	public synchronized void setNumberOfTimeout(Integer numberOfTimeout) {
		this.numberOfTimeout = numberOfTimeout;
	}

	public synchronized Integer getMaxTimeOut() {
		return maxTimeOut;
	}

	public synchronized void setMaxTimeOut(Integer maxTimeOut) {
		this.maxTimeOut = maxTimeOut;
	}

	/**
	 * Un microservicio deja de estar activo si el numero de timeout ya llego a
	 * su limite, o la cantidad de fallos
	 */
	public synchronized boolean isActive() {
		if (getNumberOfTimeout() >= getMaxTimeOut()) {
			return false;
		}
		if (getNumberOfFails() >= getMaxNumberOfFails()) {
			return false;
		}
		return true;
	}

	synchronized void incrementNumberOfTimeout() {
		this.numberOfTimeout++;
	}

	synchronized void incrementFails() {
		this.numberOfFails++;
	}

	public Integer getNumberOfFails() {
		return numberOfFails;
	}

	public void setNumberOfFails(Integer numberOfFails) {
		this.numberOfFails = numberOfFails;
	}

	public Integer getMaxNumberOfFails() {
		return maxNumberOfFails;
	}

	public void setMaxNumberOfFails(Integer maxNumberOfFails) {
		this.maxNumberOfFails = maxNumberOfFails;
	}

	/**
	 * Por ahora no vamos a usar esto
	 * @return
	 */
	public Date getInactivationTime() {
		return this.inactivationTime;
	}
	
	public void setInactivationTime(Date inactivationTime) {
		this.inactivationTime = inactivationTime;
	}
	
	/**
	 * Deja los contadores de timeout y fallo a cero
	 */
	synchronized void reset() {
		setNumberOfTimeout(0);
		setNumberOfFails(0);
		inactivationTime = null;
	}
}
