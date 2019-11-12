package py.com.sodep.bancard.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "microservices", schema = "bancard", uniqueConstraints=@UniqueConstraint(columnNames = {"service_name", "product"}))
@SequenceGenerator(name = "microservices_id_seq", sequenceName = "bancard.microservices_id_seq", allocationSize=1)
public class MicroServiceEntity implements Serializable {

	private static final long serialVersionUID = 5100147585812539860L;

	private Long id;
	private String className;
	private String product;
	private String serviceName;
	private String status;
	
	private List<MicroServiceProperty> params = new ArrayList<MicroServiceProperty>();
	
	public MicroServiceEntity(String serviceName, String product) {
		this.serviceName = serviceName;
		this.product = product;
	}

	public MicroServiceEntity() {
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="microservices_id_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "service_name")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "class_name")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "product")
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL , mappedBy="microService", fetch=FetchType.EAGER)
	public List<MicroServiceProperty> getParams() {
		return this.params;
	}

	public void setParams(List<MicroServiceProperty> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "MicroServiceEntity [id=" + id + ", className=" + className + ", product=" + product + ", serviceName="
				+ serviceName + ", status=" + status + ", params=" + params + "]";
	}
}