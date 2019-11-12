package py.com.sodep.bancard.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.sodep.bancard.domain.MicroServiceEntity;
import py.com.sodep.bancard.domain.MicroServiceProperty;
import py.com.sodep.bancard.dto.MicroServiceConverter;
import py.com.sodep.bancard.dto.MicroServiceDTO;
import py.com.sodep.bancard.repository.IMicroServicePropertyRepository;
import py.com.sodep.bancard.repository.IMicroServiceRepository;
import py.com.sodep.bancard.services.MicroServiceEntityService;

@Service
@Transactional
public class MicroServiceEntityServiceImpl implements MicroServiceEntityService {

	@Autowired
	private IMicroServiceRepository microServiceRepository;
	
	@Autowired
	private IMicroServicePropertyRepository microServicePropertyRepository;
	
	public MicroServiceDTO save(MicroServiceDTO microServiceDto) {
		MicroServiceEntity serviceEntity = microServiceRepository.findByServiceNameAndProduct(microServiceDto.getServiceName(), microServiceDto.getProduct());
		if (serviceEntity == null) {
			return null;
		}
		microServiceDto.setId(serviceEntity.getId());
		if (microServiceDto.getClassName() == null) {
			microServiceDto.setClassName(serviceEntity.getClassName());
		}
		BeanUtils.copyProperties(microServiceDto, serviceEntity);
		MicroServiceEntity saved = microServiceRepository.save(serviceEntity);
		saveProperties(saved, microServiceDto);
		MicroServiceDTO savedDto = MicroServiceConverter.toDTO(saved);
		return savedDto;
	}
	
	private List<MicroServiceProperty> saveProperties(MicroServiceEntity microService,
			MicroServiceDTO microServiceDto) {
		List<MicroServiceProperty> list = new ArrayList<MicroServiceProperty>();
		Map<String, String> params = microServiceDto.getParams();
		
		if (params != null && !params.isEmpty()) {
			Set<String> keySet = params.keySet();
			for (String key: keySet) {
				MicroServiceProperty p = saveMicroServiceProperty(microService, key, params.get(key));
				list.add(p);
			}
		}
		
		// guardamos el status
		if (microServiceDto.getMaxNumberOfFails() != null
				&& microServiceDto.getMaxNumberOfFails() > 0) {
			MicroServiceProperty p = saveMicroServiceProperty(microService, 
					MicroServiceStatus.PROP_MAX_NUMBER_OF_FAILS,
					String.valueOf(microServiceDto.getMaxNumberOfFails()));
			list.add(p);
		}
		
		if (microServiceDto.getMaxTimeOut() != null && microServiceDto.getMaxTimeOut() > 0) {
			MicroServiceProperty p = saveMicroServiceProperty(microService,
					MicroServiceStatus.PROP_MAX_TIMEOUT,
					String.valueOf(microServiceDto.getMaxTimeOut()));
			list.add(p);
		}
		
		if (microServiceDto.getTimeoutInMilliseconds() != null
				&& microServiceDto.getTimeoutInMilliseconds() > 0) {
			MicroServiceProperty p = saveMicroServiceProperty(microService,
					MicroServiceStatus.PROP_TIMEOUT_IN_MILLISECONDS,
					String.valueOf(microServiceDto.getTimeoutInMilliseconds()));
			list.add(p);
		}
		
		return list;
	}

	private MicroServiceProperty saveMicroServiceProperty(MicroServiceEntity microService, String name, String value) {
		MicroServiceProperty updatedParam = null;
		for (MicroServiceProperty param: microService.getParams()) {
			if (param.getPropertyName().equals(name)) {
				param.setValue(value);
				updatedParam = param;
				break;
			} 
		}
		
		if (updatedParam == null) {
			updatedParam = new MicroServiceProperty();
			updatedParam.setPropertyName(name);
			updatedParam.setValue(value);
			updatedParam.setMicroService(microService);
			microService.getParams().add(updatedParam);
		}
		
		return updatedParam;
	}

	@Override
	public void delete(String serviceName, String product) {
		MicroServiceEntity entity = microServiceRepository.findByServiceNameAndProduct(serviceName, product);
		if (entity != null) {
			List<MicroServiceProperty> properties = microServicePropertyRepository.findById(entity.getId());
			if (properties != null) {
				for (MicroServiceProperty microServiceProperty : properties) {
					microServicePropertyRepository.delete(microServiceProperty);
				}
			}
			microServiceRepository.delete(entity);
		}
	}

	@Override
	public MicroServiceDTO create(MicroServiceDTO microServiceDto) {
		MicroServiceEntity serviceEntity = new MicroServiceEntity();
		BeanUtils.copyProperties(microServiceDto, serviceEntity);
		MicroServiceEntity saved = microServiceRepository.save(serviceEntity);
		saveProperties(saved, microServiceDto);
		MicroServiceDTO createdDto = MicroServiceConverter.toDTO(saved);
		return createdDto;
	}

	@Override
	public MicroServiceDTO findOne(Long id) {
		MicroServiceEntity read = microServiceRepository.findOne(id);
		if (read != null) {
			return MicroServiceConverter.toDTO(read);
		}
		return null;
	}
}
