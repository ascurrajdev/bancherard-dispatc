package py.com.sodep.bancard.api.enums;

public enum ShippingType {

	MAIL("MAIL"), FTP("FTP"), SFTP("SFTP"), WS_REST("WS_REST"), WS_SOAP("WS_SOAP");

	private final String value;

	ShippingType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ShippingType fromValue(String value) {
        for (ShippingType type: ShippingType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return getDefault();
    }

    public static ShippingType fromEnumName(String key) {
		if (key != null) {
			for (ShippingType messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	private static ShippingType getDefault() {
		return ShippingType.MAIL;
	}
}
