package py.com.sodep.bancard.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import py.com.sodep.bancard.api.utils.StringUtils;
import py.com.sodep.bancard.domain.MicroServiceEntity;
import py.com.sodep.bancard.domain.UnexpectedErrorEntity;
import py.com.sodep.bancard.dto.UnexpectedErrorConverter;
import py.com.sodep.bancard.dto.UnexpectedErrorDTO;
import py.com.sodep.bancard.repository.IMicroServiceRepository;
import py.com.sodep.bancard.repository.IUnexpectedErrorRepository;
import py.com.sodep.bancard.services.UnexpectedErrorService;

@Service
public class UnexpectedErrorServiceImpl implements UnexpectedErrorService {

	private static final Logger logger = LoggerFactory.getLogger(UnexpectedErrorServiceImpl.class);
	
	@Autowired
	IUnexpectedErrorRepository errorRepository;
	
	@Autowired
	IMicroServiceRepository microServiceRepo;
	
	@Override
	public void clean() {
		errorRepository.deleteAll();
	}

	@Override
	public UnexpectedErrorEntity logMicroServiceError(Throwable ex,
			String failedMicroService, String url, String userAgent) {
		
		String exceptionType = null;
		if (ex != null) {
			exceptionType = getExceptionType(ex);
		}
		
		String microServiceName = failedMicroService;
		
		String stackTrace = null;
		if (ex != null) {
			stackTrace = getStackTrace(ex);
		}
		
		
		UnexpectedErrorEntity error = new UnexpectedErrorEntity();
		error.setExceptionType(exceptionType);
		error.setMicroServiceName(microServiceName);
		error.setStackTrace(stackTrace);
		error.setUrl(url);
		error.setUserAgent(userAgent);
		error.setInsertTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
		logger.info("Guardando error: {}", error);
		return errorRepository.save(error);
	}
	
	
	/* (non-Javadoc)
	 * @see py.com.sodep.bancard.services.impl.IUnexpectedError#logMicroServiceError(java.lang.Throwable, py.com.sodep.bancard.services.impl.MicroService, java.lang.String)
	 */
	@Override
	public UnexpectedErrorEntity logMicroServiceError(Throwable ex, MicroService microService) {
		String exceptionType = null;
		if (ex != null) {
			exceptionType = getExceptionType(ex);
		}
		
		String microServiceName = microService.getName();
		String stackTrace = getStackTrace(ex);
		
		UnexpectedErrorEntity uncaughtException = new UnexpectedErrorEntity();
		uncaughtException.setExceptionType(exceptionType);
		uncaughtException.setMicroServiceName(microServiceName);
		uncaughtException.setStackTrace(stackTrace);

		return errorRepository.save(uncaughtException);
	}

	@Override
	public List<UnexpectedErrorDTO> findAll(String orderBy, boolean ascending,
			Integer page, Integer rows) {
		return findAll(null, null, orderBy, ascending, page, rows);
	}

	@Override
	public List<UnexpectedErrorDTO> findByService(String serviceName,
			String product, String orderBy, boolean ascending, Integer page,
			Integer rows) {
		return findAll(serviceName, product, orderBy, ascending, page, rows);
	}
	
	
	private List<UnexpectedErrorDTO> findAll(String serviceName, String product, String orderBy, boolean ascending,
			Integer page, Integer rows) {
		
		Sort.Direction direction = Sort.Direction.ASC;
		if (!ascending) {
			direction = Sort.Direction.DESC;
		}
		
		PageRequest pageRequest = null;
		
		if (orderBy != null && orderBy.trim().length() > 0) {
			pageRequest = new PageRequest(page - 1, rows, direction, orderBy);
		} else {
			pageRequest = new PageRequest(page - 1, rows, direction, "id");
		}
		
		
		Page<UnexpectedErrorEntity> findAll = null;
		
		if (serviceName != null && product != null) {
			MicroServiceEntity msEntity = microServiceRepo.findByServiceNameAndProduct(serviceName, product);
			if (msEntity != null) {
				// Esto solamente va a funcionar si se carga la URL
				// al registrar el error y esa carga no está garantizada
				// por el Dispatcher.
				// TODO Se deberia mejorar el registro de error de un MS
				// para que esta consulta esté asegurada de retornar
				// resultados. Por lo pronto esto es lo más rápido que
				// que se pudo poner a funcionar.
				findAll =  errorRepository.findByUrlLike("%/" + serviceName + "/%/" + product + "/%", pageRequest);
			}
		} else {
			findAll = errorRepository.findAll(pageRequest);
		}
		
		if (findAll != null) {
			List<UnexpectedErrorDTO> errorList = new ArrayList<UnexpectedErrorDTO>();
			
			for(UnexpectedErrorEntity entity : findAll.getContent()) {
				UnexpectedErrorDTO dto = UnexpectedErrorConverter.toDTO(entity);
				errorList.add(dto);
			}
			return errorList;
		}
		
		return Collections.emptyList();
	}

	private String getStackTrace(Throwable ex) {
		String stackTrace = StringUtils.getStackTraceAsString(ex);
		if (stackTrace != null) {
			stackTrace = ex.getMessage() + "\n" + stackTrace;
			stackTrace = StringUtils.truncate(stackTrace, UnexpectedErrorEntity.MAX_STACKTRACE_LENGTH);
		}
		return stackTrace;
	}

	private String getExceptionType(Throwable ex) {
		String exceptionType = ex.getClass().getName();
		exceptionType = StringUtils.truncate(ex.getClass().getCanonicalName(),
				UnexpectedErrorEntity.MAX_EXCEPTIONTYPE_LENGTH);
		return exceptionType;
	}
}
