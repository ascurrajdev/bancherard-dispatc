package py.com.sodep.bancard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "microservices_properties", schema = "bancard")
@SequenceGenerator(name = "microservices_properties_id_seq", sequenceName = "bancard.microservices_properties_id_seq", allocationSize=1)
public class MicroServiceProperty {

	private Long id;
	
	private String propertyName;
	
	private String value;
	
	private MicroServiceEntity microService;

	public MicroServiceProperty() {
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="microservices_properties_id_seq")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name = "property_name", nullable = false)
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "microservice_id", nullable=false)
	public MicroServiceEntity getMicroService() {
		return this.microService;
	}
	
	public void setMicroService(MicroServiceEntity microService) {
		this.microService = microService;
	}
	
}
