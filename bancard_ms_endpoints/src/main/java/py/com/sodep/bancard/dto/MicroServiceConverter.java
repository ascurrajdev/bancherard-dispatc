package py.com.sodep.bancard.dto;

import java.util.HashMap;

import org.springframework.beans.BeanUtils;

import py.com.sodep.bancard.domain.MicroServiceEntity;
import py.com.sodep.bancard.domain.MicroServiceProperty;
import py.com.sodep.bancard.services.impl.MicroServiceStatus;

public class MicroServiceConverter {

	
	public static MicroServiceStatusDTO toStatusDTO(MicroServiceEntity entity, MicroServiceStatus status) {
		MicroServiceStatusDTO dto = new MicroServiceStatusDTO();
		BeanUtils.copyProperties(entity, dto);
		BeanUtils.copyProperties(status, dto);
		//dto.setStatus(status);
		dto.setParams(new HashMap<String, String>());
		for (MicroServiceProperty param: entity.getParams()) {
			dto.getParams().put(param.getPropertyName(), param.getValue());
		}
		return dto;
	}
	
	public static MicroServiceDTO toDTO(MicroServiceEntity entity) {
		MicroServiceDTO dto = new MicroServiceDTO();
		BeanUtils.copyProperties(entity, dto);
		dto.setParams(new HashMap<String, String>());
		for (MicroServiceProperty param: entity.getParams()) {
			dto.getParams().put(param.getPropertyName(), param.getValue());
		}
		return dto;
	}
	
}
