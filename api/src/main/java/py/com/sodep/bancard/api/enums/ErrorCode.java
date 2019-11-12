package py.com.sodep.bancard.api.enums;

/**
 * Enum con mensajes de error asociado a un código de error.
 * <p>
 * Estos valores deben ser usados cuando los microservicios validan datos que
 * vienen del dispatcher y los mismos no se aplican a las reglas propias de cada
 * facturador.
 *
 */
public enum ErrorCode {

	GENERIC_SYSTEM_ERROR("06", "ERROR DEL SISTEMA"),
	INVALID_TRANSACTION_AMOUNT("13", "MONTO INVÁLIDO"),
	INVALID_DUE_DATE("27", "FECHA DE VENCIMIENTO INVÁLIDA"),
	DUPLICATE_PAYMENT("94", "TRANSACCIÓN DUPLICADA - NO APROBADO"),
	INVALID_VERIFICATION_DIGIT("95", "VERIFIQUE ERROR"),
	INVALID_BARCODE("95", "LOS DATOS INGRESADOS NO SON CORRECTOS"),
	INVALID_DV("95", "EL DÍGITO VERIFICADOR NO ES CORRECTO"),
	INEXISTENT_PAYMENT("99", "PAGO INEXISTENTE"),
	CHECK_OR_CREDIT_NOT_ALLOWED("50", "EL SERVICIO NO PUEDE SER PAGADO CON CHEQUE O CRÉDITO"),
	NOT_ENOUGH_TRANSACTION_INFO("55", "INFORMACIÓN INSUFICIENTE SOBRE TRANSACCIÓN");
	
    private final String value;
    
    private final String description;

	ErrorCode(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public static ErrorCode fromValue(String value) {
		if (value != null) {
			for (ErrorCode key : values()) {
				if (key.value.equals(value)) {
					return key;
				}
			}
		}
		return getDefault();
	}

	public static ErrorCode fromEnumName(String key) {
		if (key != null) {
			for (ErrorCode messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	public static ErrorCode getDefault() {
		return null;
	}

	public String toValue() {
		return value;
	}
	
	public String toDescription() {
		return description;
	}
}