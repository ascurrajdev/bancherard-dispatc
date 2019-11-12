package py.com.sodep.bancard.api.objects;

import java.util.List;
import java.util.Map;

public interface ITransaction {

	/**
	 * @return The due
	 */
	public abstract String getDue();

	/**
	 * @return The amt
	 */
	public abstract Integer getAmount();

	/**
	 * @return The minAmt
	 */
	public abstract Integer getMinAmount();

	/**
	 * @return The invId
	 */
	public abstract List<String> getInvId();

	/**
	 * @return The curr
	 */
	public abstract String getCurrency();

	/**
	 * @return The addl
	 */
	public abstract JsonAddl getAdditionalData();

	/**
	 * @return The nextDues
	 */
	public abstract List<NextDue> getNextDues();

	/**
	 * @return The cmAmt
	 */
	public abstract Integer getCommissionAmount();

	/**
	 * @return The cmCurr
	 */
	public abstract String getCommissionCurrency();

	/**
	 * @return The dsc
	 */
	public abstract String getDescription();

	public abstract Map<String, Object> getAdditionalProperties();

	public abstract List<String> getSubscriberIds();

	public abstract Long getTransactionId();

	/**
	 * @return the transactionDate
	 */
	public abstract String getTransactionDate();

	public abstract String getTransactionHour();

	public abstract Integer getProductId();

	public abstract String getType();

	public abstract Map<String, Object> getBillerData();

}