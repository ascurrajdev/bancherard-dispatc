package py.com.sodep.bancard.ms.dummy;

import java.util.Map;

import py.com.sodep.bancard.api.enums.ErrorCode;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.api.microservices.ValidatorHelper;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

/**
 * Rechaza los pagos y reversas con un error genérico de validación.
 */
public class RejectionMicroService implements BancardMS {
	
	@Override
	public BancardResponse listInvoices(InvoiceQuery query) {
		return ValidatorHelper.throwValidationException(ErrorCode.GENERIC_SYSTEM_ERROR);
	}

	@Override
	public BancardResponse doPayment(Payment payment) throws BancardAPIException {
		boolean error = true;
		if (error) {
			ValidatorHelper.throwException(ErrorCode.GENERIC_SYSTEM_ERROR, GenericMessageKey.PAYMENT_NOT_AUTHORIZED);
		}
		return null;
	}

	@Override
	public BancardResponse doReverse(Reverse reverse) {
		return ValidatorHelper.throwValidationException(ErrorCode.GENERIC_SYSTEM_ERROR);
	}

	@Override
	public void initialize(Map<String, String> initParams) {
	}
}
