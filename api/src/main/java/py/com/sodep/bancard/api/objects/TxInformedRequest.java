package py.com.sodep.bancard.api.objects;

import java.util.List;

import py.com.sodep.bancard.api.dto.TransactionDTO;

public class TxInformedRequest {

	List<TransactionDTO> transactions;

	/**
	 * @return the transactions
	 */
	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "TxInformedRequest [transactions=" + transactions + "]";
	}
}