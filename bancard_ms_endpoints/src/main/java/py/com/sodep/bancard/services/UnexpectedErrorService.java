package py.com.sodep.bancard.services;

import java.util.List;

import py.com.sodep.bancard.domain.UnexpectedErrorEntity;
import py.com.sodep.bancard.dto.UnexpectedErrorDTO;
import py.com.sodep.bancard.services.impl.MicroService;

public interface UnexpectedErrorService {

	public UnexpectedErrorEntity logMicroServiceError(Throwable ex,
			MicroService microService);

	public List<UnexpectedErrorDTO> findAll(String orderBy,
			boolean ascending, Integer page, Integer rows);

	public List<UnexpectedErrorDTO> findByService(String serviceName,
			String product, String orderBy, boolean ascending, Integer page,
			Integer rows);

	public void clean();

	public UnexpectedErrorEntity logMicroServiceError(Throwable ex,
			String failedMicroService, String url, String userAgent);
}