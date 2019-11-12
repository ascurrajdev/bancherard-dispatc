package py.com.sodep.bancard.api.enums;

public enum AttributeDataType {

	CHARACTER("C"), DOUBLE("D"), INTEGER("I");

	private final String value;

	AttributeDataType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static AttributeDataType fromValue(String value) {
        for (AttributeDataType attribute: AttributeDataType.values()) {
            if (attribute.value.equals(value)) {
                return attribute;
            }
        }
        return getDefault();
    }

    public static AttributeDataType fromEnumName(String key) {
		if (key != null) {
			for (AttributeDataType messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	private static AttributeDataType getDefault() {
		return CHARACTER;
	}
}
