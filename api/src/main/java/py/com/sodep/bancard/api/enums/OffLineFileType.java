package py.com.sodep.bancard.api.enums;

public enum OffLineFileType {

	CSV("CSV"), XLS("XLS"), XLSX("XLSX"), ODS("ODS"), JSON("JSON"), XML("XML");

	private final String value;

	OffLineFileType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static OffLineFileType fromValue(String value) {
        for (OffLineFileType type: OffLineFileType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return getDefault();
    }

    public static OffLineFileType fromEnumName(String key) {
		if (key != null) {
			for (OffLineFileType messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	private static OffLineFileType getDefault() {
		return null;
	}
}
