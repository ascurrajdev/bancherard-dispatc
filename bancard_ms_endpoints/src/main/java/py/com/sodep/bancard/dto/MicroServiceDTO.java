package py.com.sodep.bancard.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description="Datos para la actualización y carga de un nuevo Microservicio")
public class MicroServiceDTO {

	private Long id;
	private String serviceName;
	private String product;
	private String className;
	private String status;
	private Map<String, String> params;
	private Long timeoutInMilliseconds;
	private Integer maxNumberOfFails;
	private Integer maxTimeOut;

	public MicroServiceDTO() {
	}
	
	// TODO documentar por qué esta esto
	@JsonIgnore
	@ApiModelProperty(value = "Nombre del servicio al que queremos hacer consultas")
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	// TODO documentar por qué esta esto
	@JsonIgnore
	@ApiModelProperty(value = "Nombre del producto que el servicio ofrece para realizar consultas")
	public String getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	@ApiModelProperty(value = "Nombre de la clase Java (FQN) que implementa el API Bancard y se comunica con el Facturador")
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	@ApiModelProperty(value = "Pares Key/value de parámetros específicos para cada Microservicio. Ejemplo, la URL para comunicación con el Facturador debe ir aquí.")
	public Map<String, String> getParams() {
		return params;
	}
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	@ApiModelProperty(value = "Máximo tiempo de espera de ejecución de Microservicio en milisegundos")
	public Long getTimeoutInMilliseconds() {
		return timeoutInMilliseconds;
	}
	
	public void setTimeoutInMilliseconds(Long timeoutInMilliseconds) {
		this.timeoutInMilliseconds = timeoutInMilliseconds;
	}
	
	@ApiModelProperty(value = "Máxima cantidad de timeouts detectados en un Microservicio antes de ser desactivado")
	public Integer getMaxTimeOut() {
		return maxTimeOut;
	}
	
	public void setMaxTimeOut(Integer maxTimeout) {
		this.maxTimeOut = maxTimeout;
	}

	
	@ApiModelProperty(value = "Máxima cantidad de errores detectados en un Microservicio antes de ser desactivado")
	public Integer getMaxNumberOfFails() {
		return maxNumberOfFails;
	}
	
	public void setMaxNumberOfFails(Integer maxNumberOfFails) {
		this.maxNumberOfFails = maxNumberOfFails;
	}
	
	@JsonIgnore
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MicroServiceDTO [serviceName=" + serviceName + ", product="
				+ product + ", status=" + status +"]";
	}
}