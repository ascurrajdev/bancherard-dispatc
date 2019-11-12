package py.com.sodep.bancard.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MicroServicePropertyId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8235852573591960947L;

	private Long microServiceId;
	
	private String propertyName;

	
	public MicroServicePropertyId() {
	}

	@Column(name = "id_microservice", nullable = false)
	public Long getMicroServiceId() {
		return microServiceId;
	}

	public void setMicroServiceId(Long microServiceId) {
		this.microServiceId = microServiceId;
	}

	@Column(name = "property_name", nullable = false)
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	

}
