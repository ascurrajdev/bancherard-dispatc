package py.com.sodep.bancard.api.client;


/**
 * Clase a ser extendida para agregar parámetros específicos de cada cliente web
 * service.
 */
public abstract class ClientConfig {

	private String serviceUrl;
	
	private String user;
	
	private String password;
	
	// FIXME Esta propiedad no debería hacer falta
	// si el namespace del wsdl cambia, quiere decir
	// que hubo un cambio en el API mismo del facturador.
	// Pero por ahora dejamos a modo de 'failsafe'
	private String soapBaseUrl;

	
	protected void initClient() throws Exception {
	}
	
	
	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSoapBaseUrl() {
		return soapBaseUrl;
	}

	public void setSoapBaseUrl(String soapBaseUrl) {
		this.soapBaseUrl = soapBaseUrl;
	}
	
	
}
