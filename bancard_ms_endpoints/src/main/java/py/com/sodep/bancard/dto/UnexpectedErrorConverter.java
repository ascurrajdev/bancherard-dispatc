package py.com.sodep.bancard.dto;

import org.springframework.beans.BeanUtils;

import py.com.sodep.bancard.api.utils.DateHelper;
import py.com.sodep.bancard.domain.UnexpectedErrorEntity;

public class UnexpectedErrorConverter {


	public static UnexpectedErrorDTO toDTO(UnexpectedErrorEntity entity) {
		UnexpectedErrorDTO dto = new UnexpectedErrorDTO();
		BeanUtils.copyProperties(entity, dto);
		dto.setInsertTime(DateHelper.formatDate(entity.getInsertTime()));
		return dto;
	}

}
