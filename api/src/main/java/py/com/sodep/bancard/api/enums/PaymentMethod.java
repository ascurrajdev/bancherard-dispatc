package py.com.sodep.bancard.api.enums;

/**
 * Indica cuáles son las formas de pago soportadas por el API REST de Bancard.
 */
public enum PaymentMethod {
	
	CASH("CASH"), DEBIT("DEBIT"), CREDIT("CREDIT"), CHECK("CHECK");
	
	private final String value;

    PaymentMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod type: PaymentMethod.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return getDefault();
    }

    public static PaymentMethod fromEnumName(String key) {
		if (key != null) {
			for (PaymentMethod messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	private static PaymentMethod getDefault() {
		return null;
	}

}
