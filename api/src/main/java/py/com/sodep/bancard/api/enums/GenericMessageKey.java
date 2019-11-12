package py.com.sodep.bancard.api.enums;

public enum GenericMessageKey {
	PAYMENT_AUTHORIZED("PaymentAuthorized", "success", "PAGO AUTORIZADO"), 
	PAYMENT_PROCESSED("PaymentProcessed", "success", "PAGO PROCESADO CON EXITO"), 
	PAYMENT_NOT_AUTHORIZED("PaymentNotAuthorized", "error", "EL PAGO NO PUDO SER PROCESADO"),
	SUBSCRIBER_NOT_FOUND("SubscriberNotFound", "error", "NO SE HA ENCONTRADO UN ABONADO CON EL CODIGO ENVIADO"),
	
	QUERY_PROCESSED("QueryProcessed", "success", "LA CONSULTA FUE PROCESADA CON EXITO"), 
	QUERY_NOT_ALLOWED("QueryNotAllowed", "error", "LA OPERACION DE CONSULTA NO ESTA PERMITIDA"), 
	QUERY_NOT_PROCESSED("QueryNotProcessed", "error", "LA CONSULTA NO FUE PROCESADA"),
	
	INSERT_PROCESSED("InsertProcessed", "success", "LA INSERCION FUE PROCESADA CON EXITO"),
	INSERT_NOT_PROCESSED("InsertNotProcessed", "error", "LA INSERCION NO PUDO SER PROCESADA"),
	INSERT_NOT_ALLOWED("InsertNotAllowed", "error", "LA OPERACION DE INSERCION NO ESTA PERMITIDA"),
	
	UPDATE_PROCESSED("UpdateProcessed", "success", "LA ACTUALIZACION FUE PROCESADA CON EXITO"),
	UPDATE_NOT_PROCESSED("UpdateNotProcessed", "error", "LA ACTUALIZACION NO PUDO SER PROCESADA"),
	UPDATE_NOT_ALLOWED("UpdateNotAllowed", "error", "LA OPERACION DE ACTUALIZACION NO ESTA PERMITIDA"),
	
	DELETE_PROCESSED("DeleteProcessed", "success", "LA ELIMINACION FUE PROCESADA CON EXITO"),
	DELETE_NOT_PROCESSED("DeleteNotProcessed", "error", "LA ELIMINACION NO PUDO SER PROCESADA"),
	DELETE_NOT_ALLOWED("DeleteNotAllowed", "error", "LA OPERACION DE ELIMINACION NO ESTA PERMITIDA"),
	INVALID_PARAMETERS("InvalidParameters", "error", "ERROR EN LOS PARAMETROS"), 
	MISSING_PARAMETERS("MissingParameters", "error", "PARAMETRO REQUERIDO NO RECIBIDO"), 
	NO_RESPONSE_FROM_HOST("NoResponseFromHost", "error", "NO HUBO RESPUESTA DEL HOST/AUTORIZADOR"), 
	HOST_TRANSACTION_ERROR("HostTransactionError", "error", "ERROR EN EL HOST AUTORIZADOR"), 
	SUBSCRIBER_WITHOUT_DEBT("SubscriberWithoutDebt", "info", "EL ABONADO NO TIENE DEUDA PENDIENTE"), 
	CLIENT_CONNECTION_ERROR("ClientConnectionError", "error", "NO SE PUDO ESTABLECER UNA CONEXION CON EL FACTURADOR"), 
	MICRO_SERVICE_UPDATE_SUCCESS("MicroServiceUpdateSuccess", "success", "MICROSERVICIO ACTUALIZADO CORRECTAMENTE"), 
	MICRO_SERVICE_CREATE_SUCCESS("MicroServiceCreateSuccess", "success", "MICROSERVICIO CREADO CORRECTAMENTE"), 
	MICRO_SERVICE_RELOAD_SUCCESS("MicroServiceReloadSuccess", "success", "MICROSERVICIOS CARGADOS CORRECTAMENTE EN DISPATCHER"), 
	MICRO_SERVICE_NOT_FOUND("MicroServiceNotFound", "error", "MICROSERVICIO NO ENCONTRADO"), 
	TRANSACTION_REVERSED("TransactionReversed", "success", "LA REVERSA FUE PROCESADA CON EXITO."), 
	ALREADY_REVERSED("AlreadyReversed", "error", "LA TRANSACCION YA FUE REVERSADA"),
	TRANSACTION_NOT_REVERSED("TransactionNotReversed", "error","LA TRANSACCION NO FUE REVERSADA"),
    MICROSERVICE_INTERNAL_ERROR("MicroServiceInternalError", "error", "ERROR INTERNO DEL MICRO SERVICIO"), 
    MICROSERVICE_TIMEOUT("MicroServiceTimeOut", "error", "EL MICRO SERVICIO NO RESPONDE EN EL TIEMPO ESPERADO"), 
    MICROSERVICE_INITIALIZATION_ERROR("MicroServiceInitializationError", "error", "ERROR DURANTE LA INICIALIZACION DEL MICRO SERVICIO"), 
    MICROSERVICE_DOWN("MicroServiceDown", "error", "EL MICRO SERVICIO HA SIDO DESACTIVADO"), 
    ERROR_CLEAN_SUCCESS("ErrorCleanSuccess", "success", "EL REGISTRO DE ERRORES HA SIDO BORRADO DE FORMA EXITOSA"), 
    TRANSACTION_ALREADY_REVERSED("AlreadyReversed", "error", "LA TRANSACCION YA FUE REVERSADA"),
    SERVICE_UPDATE_SUCCESS("ServiceUpdateSuccess", "success", "SERVICIO ACTUALIZADO CORRECTAMENTE"), 
	SERVICE_CREATE_SUCCESS("ServiceCreateSuccess", "success", "SERVICIO CREADO CORRECTAMENTE"),
	SERVICE_NOT_FOUND("ServiceNotFound", "error", "SERVICIO NO ENCONTRADO"),
	OFF_LINE_SERVICE_SUCCESS("OffLineServicesSuccess", "error", "PROCESO OFFLINE CONCLUIDO SATISFACTORIAMENTE"),
	OFF_LINE_SERVICE_ERROR("OffLineServicesError", "error", "PROBLEMAS EN EL PROCESO OFFLINE"),
	MICROSERVICES_ENUMERATION_ERROR("MicroServiceEnumerationError", "error", "NO SE PUDO OBTENER LA SIGUIENTE ENUMERACION EN MICROSERVICES"),
	SET_GET_PERMANENT_SESSION_ID_SUCCESS("SetGetPermanentSessionIdSuccess", "success", "SESSION PERMANENTE DE LA SET OBTENIDA SATISFACTORIAMENTE"),
	SET_GET_PERMANENT_SESSION_ID_ERROR("SetGetPermanentSessionIdError", "error", "PROBLEMAS OBTENIENDO LA SESSION PERMANENTE DE LA SET"),
	SET_ORDER_TRANSFER_SUCCESS("SetOrderTransferSuccess", "success", "PROCESO ORDEN DE TRANSFERENCIA CON LA SET CONCLUIDA SATISFACTORIAMENTE"),
	SET_ORDER_TRANSFER_ERROR("SetOrderTransferError", "error", "PROBLEMAS EN LA ORDER DE TANSFERENCIA CON LA SET, FAVOR VERIFICAR"),
	TRANSACTION_INFORMATION_SUCCESS("TransactionInformationSuccess", "success", "PROCESO QUE INFORMA LAS TRANSACCIONES HA CONCLUIDO CON EXITO"),
	TRANSACTION_INFORMATION_ERROR("TransactionInformationError", "error", "ERROR EN EL PROCESO QUE INFORMA LAS TRANSACCIONES"),
	UNKNOWN_ERROR("UnknownError", "error", "ERROR DESCONOCIDO");

	private final String key;
	private final String level;
	private final String description;

	GenericMessageKey(String value, String level, String description) {
		this.key = value;
		this.description = description;
		this.level = level;
	}

	public static GenericMessageKey fromKey(String value) {
		if (value != null) {
			for (GenericMessageKey messageKey : values()) {
				if (messageKey.key.equals(value)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	public static GenericMessageKey fromEnumName(String key) {
		if (key != null) {
			for (GenericMessageKey messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	public static GenericMessageKey getDefault() {
		return GenericMessageKey.UNKNOWN_ERROR;
	}

	public String getKey() {
		return key;
	}

	public String getLevel() {
		return level;
	}

	public String getDesc() {
		return description;
	}
}
