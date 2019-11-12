package py.com.sodep.bancard.services.impl;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.sodep.bancard.api.dto.*;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.enums.TransactionStatus;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMSException;
import py.com.sodep.bancard.api.microservices.BancardTransactionProvider;
import py.com.sodep.bancard.api.objects.Invoice;
import py.com.sodep.bancard.domain.TransactionHistoryConverter;
import py.com.sodep.bancard.domain.TransactionHistoryEntity;
import py.com.sodep.bancard.repository.ITransactionHistoryRepository;

@Service
public class BancardTransactionProviderImpl implements BancardTransactionProvider {

	@Autowired
	private ITransactionHistoryRepository transactionRepository;

	@Override
	public List<Invoice> getInvoicesPayedToday(String serviceName, String product, String subId, Integer amt) {
		List<TransactionHistoryEntity> transactions = transactionRepository
				.findByServiceNameAndProductAndSubIdAndAmt(serviceName, product, subId, amt);
		if (transactions != null) {
			return toInvoices(transactions);
		}
		return Collections.emptyList();
	}

	public List<TransactionDTO> getTransactionsToday(String serviceName, String product, String subId, Integer amt) {
		List<TransactionHistoryEntity> transactions = transactionRepository
				.findByServiceNameAndProductAndSubIdAndAmt(serviceName, product, subId, amt);
		if (transactions != null) {
			return toDTO(transactions);
		}
		return Collections.emptyList();
	}

	private List<Invoice> toInvoices(List<TransactionHistoryEntity> transactions) {
		List<Invoice> invoices = new ArrayList<Invoice>();
		for (TransactionHistoryEntity t : transactions) {
			Invoice invoice = new Invoice();
			// Identificador de factura INV_ID
			List<String> invId = new ArrayList<String>();
			String[] inv = t.getInvId().split(",");
			for (int i = 0; i < inv.length - 1; i++) {
				invId.add(inv[i]);
			}

			invoice.setAmount(t.getAmt());
			invoice.setInvId(invId);

			List<String> addl = new ArrayList<String>();
			addl.add(t.getAdditionalData());
			invoice.setAdditionalData(addl);
			invoice.setDue(t.getTransactionDate());
			// TODO agregar los otros campos
			invoices.add(invoice);
		}
		return invoices;
	}

	private List<TransactionDTO> toDTO(List<TransactionHistoryEntity> transactions) {
		List<TransactionDTO> trans = new ArrayList<TransactionDTO>();
		for (TransactionHistoryEntity t : transactions) {
			TransactionDTO dto = new TransactionDTO();
			dto.setServiceName(t.getServiceName());
			dto.setProduct(t.getProduct());
			dto.setAmt(t.getAmt());
			dto.setCurr(t.getCurr());
			dto.setCommissionAmt(t.getCommissionAmt());
			dto.setCommissionCurr(t.getCommissionCurr());
			dto.setSubId(t.getSubId());
			dto.setInvId(t.getInvId());
			dto.setProductUid(t.getProductId());
			dto.setTransactionDate(t.getTransactionDate());
			dto.setTransactionHour(t.getTransactionHour());
			dto.setTransactionId(t.getTransactionId());
			dto.setType(t.getType());
			trans.add(dto);
		}
		return trans;
	}

	@Transactional
	@Override
	public void reverseTransaction(TransactionDTO transaction) {
		Long tid = transaction.getTransactionId();
		TransactionHistoryEntity entity = transactionRepository.findByTransactionId(tid);
		if (entity != null && entity.getStatus() != null) {
			if (entity.getStatus() == TransactionStatus.REVERSED_PENDING) {
				throw new BancardMSException(GenericMessageKey.TRANSACTION_ALREADY_REVERSED.getDesc(), null,
						GenericMessageKey.TRANSACTION_ALREADY_REVERSED.getKey());
			} else {
				// if (entity.getStatus() == TransactionStatus.PAYMENT_PROCESSED) {
				// solamente los pagados satisfactoriamente
				entity.setStatus(TransactionStatus.REVERSED_PROCESSED);
				entity.setBillerData(transaction.getBillerData());
				transactionRepository.save(entity);
			}
		} else {
			throw new BancardMSException("No existe la transacción con id " + tid, null,
					GenericMessageKey.TRANSACTION_NOT_REVERSED.getKey());
		}
	}

	@Transactional
	@Override
	public void deleteTransactionByInvId(String serviceName, String product, String subId, String invId) {
		List<TransactionHistoryEntity> aBorrar = transactionRepository.selectTransactionsByInvId(serviceName, product,
				subId, invId);
		for (TransactionHistoryEntity dto : aBorrar) {
			transactionRepository.delete(dto.getId());
		}
	}

	@Transactional
	@Override
	public void logTransaction(TransactionDTO transaction) {
		TransactionHistoryEntity entity = TransactionHistoryConverter.fromDTO(transaction);
		if (entity.getStatus() == null) { // entity.setStatus(TransactionStatus.NEW);
			throw new BancardMSException(
					"Error, estado vacío para almacenar el historial. Tid: " + transaction.getTransactionId());
		}
		entity.setInformed(Boolean.FALSE);
		transactionRepository.save(entity);
	}

	@Override
	public TransactionDTO getTransactionsByTid(long tid) {
		return TransactionHistoryConverter.fromEntity(transactionRepository.findByTransactionId(tid));
	}

	@Transactional
	@Override
	public void updateByTid(long tid, TransactionDTO transaction) {
		TransactionHistoryEntity entity = TransactionHistoryConverter.fromDTO(transaction);
		if (entity.getStatus() == null) { // entity.setStatus(TransactionStatus.NEW);
			throw new BancardMSException(
					"Error, estado vacío para almacenar el historial. Tid: " + transaction.getTransactionId());
		}
		transactionRepository.updateByTid(tid, entity.getProductId(), entity.getSubId(), entity.getInvId(),
				entity.getAdditionalData(), entity.getAmt(), entity.getCurr(), entity.getTransactionDate(),
				entity.getTransactionHour(), entity.getCommissionAmt(), entity.getCommissionCurr(), entity.getType(),
				entity.getInformed(), entity.getBillerData());
	}

	@Override
	public TransactionDTO getByInvId(String serviceName, String product, String subId, String invId) {
		return TransactionHistoryConverter
				.fromEntity(transactionRepository.selectTransactionsByInvId(serviceName, product, subId, invId).get(0));
	}

	public TransactionDTO getBySubId(String serviceName, String product, String subId) {
		return null;
	}

	public Integer sumAmountCompletedByPrdIdAndBillId(Integer productUid, Integer billId) {
		return null;
	}

	public List<BillsDTO> findBillsPending(Integer productUid, String subId) {
		return null;
	}

	public BillsDTO findBillsById(BigInteger id) {
		return null;
	}

	public BehaviorDTO getBehaviorByProductUid(Integer productUid) {
		return null;
	}

	public BillsDTO updateBill(BillsDTO dto) {
		return null;
	}

	public BillsDTO findBillByPrdUIdAndInvID(Integer productUid, String invId) {
		return null;
	}

	public Long enumerationNextValue(String cod, Integer productUid) {
		return null;
	}

	public List<BillsDTO> findBillsPendingBeforeActualDue(Integer productId, String listStringToJson, Date dueDate) {
		return null;
	}

	public ContactDTO findContactById(Integer id) {
		return null;
	}

	public List<ContactDTO> findContactByBehaviorId(Integer behaviorId) {
		return null;
	}

	public List<ContactDTO> findContactByProductUid(Integer productUid) {
		return null;
	}

	public List<PlanDTO> findPlanByBehaviorId(Integer behaviorId) {
		return null;
	}

	public List<PlanDTO> findPlanByProductUid(Integer productUid) {
		return null;
	}

	public void sendMail(MailSenderDTO sender, File... attachments) throws BancardAPIException {
	}

	public MailSenderDTO getSenderMailDtoByMailingListCode(String maillingCode) {
		return null;
	}

	public MailingListConfigDTO getMailingListConfigDtoByMailingListCode(String mailingCode) {
		return null;
	}



	
}
