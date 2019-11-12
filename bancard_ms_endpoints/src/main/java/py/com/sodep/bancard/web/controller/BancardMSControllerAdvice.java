package py.com.sodep.bancard.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMSException;
import py.com.sodep.bancard.api.microservices.HipChatHelper;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.utils.StringUtils;
import py.com.sodep.bancard.domain.UnexpectedErrorEntity;
import py.com.sodep.bancard.rest.ReverseRequest;
import py.com.sodep.bancard.services.UnexpectedErrorService;

/**
 * Esta clase maneja las exepciones arrojadas en los RestControllers codigos de
 * estado y mensajes definidos en el Bancard REST API.
 * <p>
 * Por ejemplo, va a manejar exepciones arrojaadas en {@link InvoiceController}
 * y {@link PaymentController}.
 *
 */
// TOOD documentar mejor los métodos de esta clase
@ControllerAdvice
public class BancardMSControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(BancardMSControllerAdvice.class);

    private HipChatHelper hipChatHelper = new HipChatHelper();

	@Autowired
	private UnexpectedErrorService errorService;

	/**
	 * 1) Peticiones con parámetros requeridos pero no proveídos.
	 * 3) Peticiones con parámetros validados por la anotación @Valid pero fallaron esa validación. Ejemplo: 
	 * {@link ReverseRequest}
	 * @param req
	 * @param e
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> badRequest(HttpServletRequest req, Exception e) {
		logger.error("==== Invalid request: errorMsg={}", e.getMessage());
		BancardResponse response = BancardResponse.getError(GenericMessageKey.MISSING_PARAMETERS);
		setTransactionIdIfExists(req, response);
		return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
	}

	/**
	 * Peticiones con parámetros de tipo de dato no acpetado por el Dipsatcher.
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({TypeMismatchException.class})
    protected ResponseEntity<Object> invalidParametersRequest(HttpServletRequest req, Exception e) {
		logger.error("==== Invalid Parameter in request: errorMsg={}", e.getMessage());
		hipChatHelper.sendNotificationHipChat("MS-Advice invalidParametersRequest: " + "\n" + String.valueOf(req) + "\n" + e);
		BancardResponse response = BancardResponse.getError(GenericMessageKey.INVALID_PARAMETERS);
		setTransactionIdIfExists(req, response);
		return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
	}

	/**
	 * Errores de validación lanzados por el microservicio.
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler({BancardAPIException.class})
    protected ResponseEntity<Object> handleMicroServiceCheckedError(HttpServletRequest req, BancardAPIException e) {
		logger.error("==== MicroService BancardAPIException: e.getKey() = {}, errorMsg={}", e.getKey(), e.getMessage());
		hipChatHelper.sendNotificationHipChat("MS-Advice handleMicroServiceCheckedError: " + "\n" + String.valueOf(req) + "\n" + e);
		BancardResponse response = BancardResponse.getError(e);
		setTransactionIdIfExists(req, response);
		setErrorCode(e, response);
		return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
	}

	/**
	 * Errores inesperados en los microservicios.
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler({BancardMSException.class})
    protected ResponseEntity<Object> handleMicroServiceUncheckedError(HttpServletRequest req, BancardMSException e) {
		logger.error("==== MicroService internal runtime error: e.getKey() = {}", e.getKey());
		logger.error(e.getMessage(), e);
		hipChatHelper.sendNotificationHipChat("MS-Advice handleMicroServiceUncheckedError: " + "\n" + String.valueOf(req) + "\n" + e);
		BancardResponse response = BancardResponse.getError(e);
		// Por ahora solamente esto vamos a loguear, los errores dentro del MicroServicio
		logError(e, req, e.getFailedMicroService());
		return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
	}
	
	/**
	 * Exceptiones runtime dentro del dispatcher, que no son de ningun microservicio particular.
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> handleDispatcherRuntimeException(HttpServletRequest req, RuntimeException e) {
		logger.error("==== Dispatcher internal runtime exception");
		logger.error(e.getMessage(), e);
		hipChatHelper.sendNotificationHipChat("MS-Advice handleDispatcherRuntimeException: " + "\n" + String.valueOf(req) + "\n" + e);
		BancardResponse response = BancardResponse.getError(e);
		logError(e, req, null);
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Excepciones dentro del dispatcher, que no son de ningun microservicio particular.
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleDispatcherException(HttpServletRequest req, Exception e) {
		logger.error("==== Dispatcher internal exception");
		logger.error(e.getMessage(), e);
		hipChatHelper.sendNotificationHipChat("MS-Advice handleDispatcherException: " + "\n" + String.valueOf(req) + "\n" + e);
		BancardResponse response = BancardResponse.getError(e);
		logError(e, req, null);
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	private void logError(Throwable e, HttpServletRequest req, String failedMicroService) {
		try {
			if (errorService != null && e != null) {
				//String microServiceKey =
				String url = getUrl(req);
				String userAgent = getUserAgent(req);
				if (failedMicroService != null) {
					// error de MS
					if (e.getCause() != null) {
						errorService.logMicroServiceError(e.getCause(), failedMicroService, url, userAgent);
					} else {
						errorService.logMicroServiceError(e, failedMicroService, url, userAgent);
					}
				} else {
					// error del dispatcher
					
				}
			}
		} catch (Throwable ex) {
			logger.error("El dispatcher no pudo guardar un registro del error");
			logger.error(ex.getMessage(), ex);
		}
	}

	private String getUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		if (url != null) {
			url = StringUtils.truncate(url, UnexpectedErrorEntity.MAX_URL_LENGTH);
		}
		return url;
	}

	private String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		if (userAgent != null) {
			userAgent = StringUtils.truncate(userAgent, UnexpectedErrorEntity.MAX_USERAGENT_LENGTH);
		}
		return userAgent;
	}

	private void setTransactionIdIfExists(HttpServletRequest req, BancardResponse response) {
		String tidStr = req.getParameter("tid");
		if (tidStr != null) {
			try {
				response.setTransactionId(Long.parseLong(tidStr));
			} catch (NumberFormatException ne) {
			}
		}
	}

	private void setErrorCode(BancardAPIException e, BancardResponse response) {
		String errorCode = e.getErrorCode();
		if (errorCode != null && !errorCode.isEmpty()) {
			response.setErrorCode(errorCode);
		}
	}
}