package py.com.sodep.bancard.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.objects.BancardMessage;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.dto.MicroServiceDTO;

/**
 * Generic class with helper methods for 'RestControllers'.
 * <p>
 * All 'RestControllers' should extend this class to have access to the helper
 * methods or behavior that is the same for every Bancard's REST API
 * request/response.
 *
 */
public abstract class BancardMSController {

	/**
	 * Given the {@link BancardResponse}'s {@link GenericMessageKey}, returns the
	 * {@link HttpStatus} expected by Bancard REST API clients.
	 * 
	 * @param response
	 * @return a {@link HttpStatus}
	 */
	protected HttpStatus statusCode(BancardResponse response) {
		List<BancardMessage> messages = response.getMessages();
		for (BancardMessage message: messages) {
			switch(message.getMessageKey()) {
			    case PAYMENT_AUTHORIZED:
			    case PAYMENT_PROCESSED:
			    case SUBSCRIBER_WITHOUT_DEBT:
			    case QUERY_PROCESSED:
			    case TRANSACTION_REVERSED:
			    case MICRO_SERVICE_UPDATE_SUCCESS:
			    case MICRO_SERVICE_CREATE_SUCCESS:
			    case MICRO_SERVICE_RELOAD_SUCCESS:
			    case ERROR_CLEAN_SUCCESS:
				    return HttpStatus.OK;

				case INVALID_PARAMETERS:
					return HttpStatus.UNPROCESSABLE_ENTITY;

				case SUBSCRIBER_NOT_FOUND:
				case MICRO_SERVICE_NOT_FOUND:
					return HttpStatus.NOT_FOUND;

				case HOST_TRANSACTION_ERROR:
				case MISSING_PARAMETERS:
				case NO_RESPONSE_FROM_HOST: 
				case QUERY_NOT_PROCESSED: 
				case QUERY_NOT_ALLOWED: 
				case UNKNOWN_ERROR:
				case PAYMENT_NOT_AUTHORIZED:
					return HttpStatus.FORBIDDEN;
				
				default:
					return HttpStatus.FORBIDDEN;
			}
		}
		return HttpStatus.FORBIDDEN;
	}

	protected void checkMsParameters(MicroServiceDTO microServiceDto) throws MissingServletRequestParameterException {
		if (microServiceDto.getClassName() == null) {
			throw new MissingServletRequestParameterException("className", "String");
		}

		if (microServiceDto.getStatus() == null) {
			throw new MissingServletRequestParameterException("status", "String");
		}

		if ("".equals(microServiceDto.getStatus())) {
			throw new MissingServletRequestParameterException("Microservices Status", "String");
		}

		if (!("A".equals(microServiceDto.getStatus()) || "I".equals(microServiceDto.getStatus()))) {
			throw new MissingServletRequestParameterException("Microservices Status", "A (active) or I (inactive)");
		}
	}
}