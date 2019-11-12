package py.com.sodep.bancard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import py.com.sodep.bancard.domain.UnexpectedErrorEntity;

public interface IUnexpectedErrorRepository extends JpaRepository<UnexpectedErrorEntity, Long> {

	Page<UnexpectedErrorEntity> findByMicroServiceName(String className,
			Pageable pageRequest);

	Page<UnexpectedErrorEntity> findByUrlLike(String model,
			Pageable pageRequest);
}
