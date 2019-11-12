package py.com.sodep.bancard.services;

import java.util.Date;
import java.util.List;

import py.com.sodep.bancard.api.dto.TransactionDTO;

public interface ITransactionService {

	List<TransactionDTO> findAll(String service, String product, Date fromDate,
			Date toDate, Boolean informed, String orderBy, boolean ascending);

	void inform(List<TransactionDTO> transactions);

}
