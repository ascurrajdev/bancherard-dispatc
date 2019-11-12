package py.com.sodep.bancard.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import py.com.sodep.bancard.domain.MicroServiceEntity;

public interface IMicroServiceRepository extends CrudRepository<MicroServiceEntity, Long> {
	
	MicroServiceEntity findByServiceNameAndProduct(String serviceName, String product);

	@Query(value = "select * from bancard.microservices where status = 'A'", nativeQuery=true)
	Iterable<MicroServiceEntity> findAllWithActiveState();

	@Query(value = "select * from bancard.microservices where status =:status", nativeQuery=true)
	Iterable<MicroServiceEntity> findByStatus(@Param("status") String status);	
}