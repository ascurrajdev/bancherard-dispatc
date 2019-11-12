package py.com.sodep.bancard.api.enums;

public enum BehaviorPeriodicity {

	A("ONLINE"), D("DAILY"), S("WEEKLY"), Q("BIWEEKLY"), M("MONTHLY");

	private final String value;

	BehaviorPeriodicity(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static BehaviorPeriodicity fromValue(String value) {
        for (BehaviorPeriodicity type: BehaviorPeriodicity.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return getDefault();
    }

    public static BehaviorPeriodicity fromEnumName(String key) {
		if (key != null) {
			for (BehaviorPeriodicity messageKey : values()) {
				if (messageKey.name().equals(key)) {
					return messageKey;
				}
			}
		}
		return getDefault();
	}

	private static BehaviorPeriodicity getDefault() {
		return null;
	}
}
