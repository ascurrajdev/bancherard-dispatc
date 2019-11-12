package py.com.sodep.bancard.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.sodep.bancard.api.dto.TransactionDTO;
import py.com.sodep.bancard.api.utils.DateHelper;
import py.com.sodep.bancard.domain.TransactionHistoryEntity;
import py.com.sodep.bancard.repository.ITransactionHistoryRepository;
import py.com.sodep.bancard.services.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	ITransactionHistoryRepository repository;
	
	@Override
	public List<TransactionDTO> findAll(String service, String product,
			Date fromDate, Date toDate, Boolean informed, String orderBy, boolean ascending) {
		Sort sort = getSort(orderBy, ascending);
		List<TransactionHistoryEntity> entityList = repository.findByServiceNameAndProductAndCreatedBetweenAndInformed(service, product, fromDate, toDate, informed, sort);
		return toDTOList(entityList);
	}

	private Sort getSort(String orderBy, boolean ascending) {
		Sort.Direction direction = Sort.Direction.ASC;
		if (!ascending) {
			direction = Sort.Direction.DESC;
		}
		Sort sort = null;
		if (orderBy != null && orderBy.trim().length() > 0) {
			sort = new Sort(direction, orderBy);
		} else {
			sort = new Sort(direction, "created");
		}
		return sort;
	}

	private List<TransactionDTO> toDTOList(
			List<TransactionHistoryEntity> entityList) {
		if (entityList != null && !entityList.isEmpty()) {
			List<TransactionDTO> dtoList = new ArrayList<TransactionDTO>();
			for (TransactionHistoryEntity entity : entityList) {
				TransactionDTO dto = new TransactionDTO();
				BeanUtils.copyProperties(entity, dto);
				dto.setCreated(DateHelper.formatDate(entity.getCreated()));
				dtoList.add(dto);
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Transactional
	@Override
	public void inform(List<TransactionDTO> transactions) {
		if (transactions != null && !transactions.isEmpty()) {
			for (TransactionDTO dto : transactions) {
				TransactionHistoryEntity entity = repository.findByTransactionId(dto.getTransactionId());
				if (entity != null && !entity.getInformed()) {
					entity.setInformed(Boolean.TRUE);
					repository.save(entity);
				} else {
					// TODO ¿que hacemos acá?
					// a lo mejor seria bueno lanzar un DispatcherBusinessException o algo así
				}
			}
		}
	}

}
