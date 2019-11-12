package py.com.sodep.bancard.api.enums;

public enum TransactionStatus {
	/*
	** Propongo tener los 
	** siguientes estados:
	** 0 = QUERY_PROCESSED
	** 1 = PAYMENT_PENDING
	** 2 = PAYMENT_PROCESSED
	** 3 = PAYMENT_DECLINED
	** 4 = REVERSED_PENDING
	** 5 = REVERSED_PROCESSED
	** 6 = REVERSE_DECLINED
	*************************/
	QUERY_PROCESSED, PAYMENT_PENDING, PAYMENT_PROCESSED, PAYMENT_DECLINED, REVERSED_PENDING, REVERSED_PROCESSED, REVERSE_DECLINED;

	public static int getStatusToIntDataType(TransactionStatus txStatus) {
		if (txStatus == null)
			return 0;
		int typeOfTxStatus;
		switch (txStatus) {
		case QUERY_PROCESSED:
			typeOfTxStatus = 0;
			break;
		case PAYMENT_PENDING:
			typeOfTxStatus = 1;
			break;
		case PAYMENT_PROCESSED:
			typeOfTxStatus = 2;
			break;
		case PAYMENT_DECLINED:
			typeOfTxStatus = 3;
			break;
		case REVERSED_PENDING:
			typeOfTxStatus = 4;
			break;
		case REVERSED_PROCESSED:
			typeOfTxStatus = 5;
			break;
		case REVERSE_DECLINED:
			typeOfTxStatus = 6;
			break;
		default:
			typeOfTxStatus = 0;
		}
		return typeOfTxStatus;
	}
}