package py.com.bancard.microservice.cit.config;

import py.com.sodep.bancard.api.client.ClientConfig;

public class CitClientConfig extends ClientConfig {
	private int timeOut;
	private String driverClassName;

	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	@Override
	public String toString() {
		return "ProinsaClientConfig [timeOut=" + timeOut + ", driverClassName=" + driverClassName + "]";
	}
}