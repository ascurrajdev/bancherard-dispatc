package py.com.sodep.bancard.api.enums;

public enum OffLineType {

	GENE("GENERIC"), CUST("CUSTOMIZED");

	private final String value;

	OffLineType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static OffLineType fromValue(String value) {
        for (OffLineType type: OffLineType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return getDefault();
    }

    public static OffLineType fromEnumName(String key) {
		if (key != null) {
			for (OffLineType messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	private static OffLineType getDefault() {
		return null;
	}
}