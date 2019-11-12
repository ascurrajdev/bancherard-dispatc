package py.com.sodep.bancard.api.enums;

public enum ParticularMessageKey {
	PAYMENT_AUTHORIZED("PaymentAuthorized", "success"), 
	PAYMENT_PROCESSED("PaymentProcessed", "success"), 
	PAYMENT_NOT_AUTHORIZED("PaymentNotAuthorized", "error"),
	SUBSCRIBER_NOT_FOUND("SubscriberNotFound", "error"),
	QUERY_PROCESSED("QueryProcessed", "success"), 
	QUERY_NOT_ALLOWED("QueryNotAllowed", "error"), 
	QUERY_NOT_PROCESSED("QueryNotProcessed", "error"),
	INSERT_PROCESSED("InsertProcessed", "success"),
	INSERT_NOT_PROCESSED("InsertNotProcessed", "error"),
	INSERT_NOT_ALLOWED("InsertNotAllowed", "error"),
	UPDATE_PROCESSED("UpdateProcessed", "success"),
	UPDATE_NOT_PROCESSED("UpdateNotProcessed", "error"),
	UPDATE_NOT_ALLOWED("UpdateNotAllowed", "error"),
	DELETE_PROCESSED("DeleteProcessed", "success"),
	DELETE_NOT_PROCESSED("DeleteNotProcessed", "error"),
	DELETE_NOT_ALLOWED("DeleteNotAllowed", "error"),
	INVALID_PARAMETERS("InvalidParameters", "error"), 
	MISSING_PARAMETERS("MissingParameters", "error"), 
	NO_RESPONSE_FROM_HOST("NoResponseFromHost", "error"), 
	HOST_TRANSACTION_ERROR("HostTransactionError", "error"), 
	SUBSCRIBER_WITHOUT_DEBT("SubscriberWithoutDebt", "info"), 
	CLIENT_CONNECTION_ERROR("ClientConnectionError", "error"), 
	MICRO_SERVICE_UPDATE_SUCCESS("MicroServiceUpdateSuccess", "success"),
	MICRO_SERVICE_CREATE_SUCCESS("MicroServiceCreateSuccess", "success"), 
	MICRO_SERVICE_RELOAD_SUCCESS("MicroServiceReloadSuccess", "success"), 
	MICRO_SERVICE_NOT_FOUND("MicroServiceNotFound", "error"), 
	TRANSACTION_REVERSED("TransactionReversed", "success"), 
	ALREADY_REVERSED("AlreadyReversed", "error"),
	TRANSACTION_NOT_REVERSED("TransactionNotReversed", "error"),
    MICROSERVICE_INTERNAL_ERROR("MicroServiceInternalError", "error"), 
    MICROSERVICE_TIMEOUT("MicroServiceTimeOut", "error"), 
    MICROSERVICE_INITIALIZATION_ERROR("MicroServiceInitializationError", "error"), 
    MICROSERVICE_DOWN("MicroServiceDown", "error"), 
    ERROR_CLEAN_SUCCESS("ErrorCleanSuccess", "success"), 
    TRANSACTION_ALREADY_REVERSED("AlreadyReversed", "error"),
    SERVICE_UPDATE_SUCCESS("ServiceUpdateSuccess", "success"), 
	SERVICE_CREATE_SUCCESS("ServiceCreateSuccess", "success"),
	SERVICE_NOT_FOUND("ServiceNotFound", "error"),
	OFF_LINE_SERVICE_SUCCESS("OffLineServicesSuccess", "error"),
	OFF_LINE_SERVICE_ERROR("OffLineServicesError", "error"),
	MICROSERVICES_ENUMERATION_ERROR("MicroServiceEnumerationError", "error"),
	SET_GET_PERMANENT_SESSION_ID_SUCCESS("SetGetPermanentSessionIdSuccess", "success"),
	SET_GET_PERMANENT_SESSION_ID_ERROR("SetGetPermanentSessionIdError", "error"),
	SET_ORDER_TRANSFER_SUCCESS("SetOrderTransferSuccess", "success"),
	SET_ORDER_TRANSFER_ERROR("SetOrderTransferError", "error"),
	SET_OPEN_BATCH_COPACO("SetOpenBatchCopaco", "error"),
	TRANSACTION_INFORMATION_SUCCESS("TransactionInformationSuccess", "success"),
	TRANSACTION_INFORMATION_ERROR("TransactionInformationError", "error"),
    UNKNOWN_ERROR("UnknownError", "error");

	private final String key;
	private final String level;

	ParticularMessageKey(String key, String level) {
		this.key = key;
		this.level = level;
	}

	public static ParticularMessageKey fromKey(String key) {
		if (key != null) {
			for (ParticularMessageKey messageKey : values()) {
				if (messageKey.key.equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	public static ParticularMessageKey fromEnumName(String key) {
		if (key != null) {
			for (ParticularMessageKey messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	public static ParticularMessageKey getDefault() {
		return ParticularMessageKey.UNKNOWN_ERROR;
	}

	public String getKey() {
		return key;
	}

	public String getLevel() {
		return level;
	}
}
