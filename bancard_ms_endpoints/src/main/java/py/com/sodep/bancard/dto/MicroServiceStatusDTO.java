package py.com.sodep.bancard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MicroServiceStatusDTO extends MicroServiceDTO {
	
	private Boolean active;
	
	private Integer numberOfFails;
	
	private Integer numberOfTimeout;

	@JsonIgnore(false)
	@Override
	public String getServiceName() {
		return super.getServiceName();
	}
	
	@Override
	public void setServiceName(String serviceName) {
		super.setServiceName(serviceName);
	};
	
	@JsonIgnore(false)
	@Override
	public String getProduct() {
		return super.getProduct();
	}
	
	@Override
	public void setProduct(String product) {
		super.setProduct(product);
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getNumberOfFails() {
		return numberOfFails;
	}

	public void setNumberOfFails(Integer numberOfFails) {
		this.numberOfFails = numberOfFails;
	}

	public Integer getNumberOfTimeout() {
		return numberOfTimeout;
	}

	public void setNumberOfTimeout(Integer numberOfTimeout) {
		this.numberOfTimeout = numberOfTimeout;
	}
	
}
