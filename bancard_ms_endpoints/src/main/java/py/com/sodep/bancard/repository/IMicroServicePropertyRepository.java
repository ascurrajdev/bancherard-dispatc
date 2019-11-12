package py.com.sodep.bancard.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import py.com.sodep.bancard.domain.MicroServiceProperty;


public interface IMicroServicePropertyRepository extends CrudRepository<MicroServiceProperty, Long>{

	List<MicroServiceProperty> findById(Long microServiceId);
	
}
