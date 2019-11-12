package py.com.sodep.bancard.api.microservices;

import py.com.sodep.bancard.api.enums.ErrorCode;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.enums.ParticularMessageKey;
import py.com.sodep.bancard.api.objects.BancardResponse;

public class ValidatorHelper {
	public static BancardResponse throwValidationException(ErrorCode errorCode) {
		return throwValidationException(errorCode, null);
	}

	public static BancardResponse throwValidationException(ErrorCode errorCode, String description) {
		String errorMsg = getErrorMessage(errorCode, description);
		BancardAPIException response = new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, errorMsg);
		response.setErrorCode(errorCode.toValue());
		return BancardResponse.getError(response);
	}

	public static BancardResponse throwValidationException(String errorCode, String description) {
		BancardAPIException response = new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, description);
		response.setErrorCode(errorCode);
		return BancardResponse.getError(response);
	}

	
	public static void throwException(ErrorCode errorCode, GenericMessageKey message) throws BancardAPIException {
		String errorMsg = getErrorMessage(errorCode, null);
		BancardAPIException e = new BancardAPIException(message, errorMsg);
		e.setErrorCode(errorCode.toValue());
		throw e;
	}
	
	
	
	private static String getErrorMessage(ErrorCode errorCode, String description) {
		String msg;
		if (description != null) {
			msg = description;
		} else {
			msg = errorCode.toDescription();
		}
		return msg;
	}
}
