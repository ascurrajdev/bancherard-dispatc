package py.com.sodep.bancard.api.enums;

public enum InvoicesFields {
	DUE("DUE"), AMT("AMOUNT"), MIN_AMT("MIN_AMOUNT"), INV_ID("INV_ID"), SUB_ID("SUB_ID"), CURR("CURRENCY"), ADDL(
			"ADDITIONAL_DATA"), NEXT_DUES("NEXT_DUES"), DSC("DESCRIPTION");

	private final String value;

	InvoicesFields(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static InvoicesFields fromValue(String value) {
        for (InvoicesFields type: InvoicesFields.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return getDefault();
    }

	public static InvoicesFields fromEnumName(String key) {
		if (key != null) {
			for (InvoicesFields messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	private static InvoicesFields getDefault() {
		return null;
	}
}