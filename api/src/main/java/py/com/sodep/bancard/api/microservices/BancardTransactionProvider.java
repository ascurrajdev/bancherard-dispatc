package py.com.sodep.bancard.api.microservices;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import py.com.sodep.bancard.api.dto.BehaviorDTO;
import py.com.sodep.bancard.api.dto.BillsDTO;
import py.com.sodep.bancard.api.dto.ContactDTO;
import py.com.sodep.bancard.api.dto.MailSenderDTO;
import py.com.sodep.bancard.api.dto.MailingListConfigDTO;
import py.com.sodep.bancard.api.dto.PlanDTO;
import py.com.sodep.bancard.api.dto.TransactionDTO;
import py.com.sodep.bancard.api.objects.Invoice;

public interface BancardTransactionProvider {

	public List<Invoice> getInvoicesPayedToday(String serviceName, String product, String subId, Integer amt);

	public List<TransactionDTO> getTransactionsToday(String serviceName, String product, String subId, Integer amt);

	public void deleteTransactionByInvId(String serviceName, String product, String subId, String invId);

	public void reverseTransaction(TransactionDTO transaction);

	public void logTransaction(TransactionDTO transaction);

	public TransactionDTO getTransactionsByTid(long tid);

	public TransactionDTO getByInvId(String serviceName, String product, String subId, String invId);
	
	public TransactionDTO getBySubId(String serviceName, String product, String subId);

	public void updateByTid(long tid, TransactionDTO transaction);

	public Integer sumAmountCompletedByPrdIdAndBillId(Integer productUid, Integer billId);

	public List<BillsDTO> findBillsPending(Integer productUid, String subId);

	public BillsDTO findBillsById(BigInteger id);

	public BehaviorDTO getBehaviorByProductUid(Integer productUid);

	public BillsDTO updateBill(BillsDTO dto);

	public BillsDTO findBillByPrdUIdAndInvID(Integer productUid, String invId);

	public Long enumerationNextValue(String cod, Integer productUid);

	public List<BillsDTO> findBillsPendingBeforeActualDue(Integer productId, String listStringToJson, Date dueDate);
	
	public ContactDTO findContactById(Integer id);

	public List<ContactDTO> findContactByBehaviorId(Integer behaviorId);

	public List<ContactDTO> findContactByProductUid(Integer productUid);

	public List<PlanDTO> findPlanByBehaviorId(Integer behaviorId);

	public List<PlanDTO> findPlanByProductUid(Integer productUid);

	public void sendMail(MailSenderDTO sender, File... attachments) throws BancardAPIException;
	
	public MailSenderDTO getSenderMailDtoByMailingListCode(String maillingCode);
	
	public MailingListConfigDTO getMailingListConfigDtoByMailingListCode(String mailingCode);
}