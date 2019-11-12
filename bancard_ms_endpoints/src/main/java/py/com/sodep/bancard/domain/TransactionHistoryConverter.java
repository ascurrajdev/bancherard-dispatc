package py.com.sodep.bancard.domain;


import org.springframework.beans.BeanUtils;

import py.com.sodep.bancard.api.dto.TransactionDTO;
import py.com.sodep.bancard.api.objects.ITransaction;
import py.com.sodep.bancard.api.utils.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionHistoryConverter {

	private static final ObjectMapper mapper = new ObjectMapper();
	

	public static TransactionHistoryEntity fromPayment(ITransaction payment) {
		TransactionHistoryEntity entity = new TransactionHistoryEntity();
		BeanUtils.copyProperties(payment, entity);
		entity.setAmt(payment.getAmount());
		entity.setCurr(payment.getCurrency());
		entity.setCommissionAmt(payment.getCommissionAmount());
		entity.setCommissionCurr(payment.getCommissionCurrency());
		entity.setSubId(payment.getSubscriberIds().get(0));
		String invIdAsStr = StringUtils.toStringWithSeparator(payment.getInvId(), ",");
		entity.setInvId(invIdAsStr);
		try {
			String additionalData = mapper.writeValueAsString(payment.getAdditionalProperties());
			entity.setAdditionalData(additionalData);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return entity;

	}

	public static TransactionHistoryEntity fromDTO(TransactionDTO transaction) {
		TransactionHistoryEntity entity = new TransactionHistoryEntity();
		BeanUtils.copyProperties(transaction, entity);
		return entity;
	}

	public static TransactionDTO fromEntity(TransactionHistoryEntity  entity) {
		TransactionDTO dto = new TransactionDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
