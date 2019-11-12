package py.com.sodep.bancard.services;

import py.com.sodep.bancard.dto.MicroServiceDTO;

public interface MicroServiceEntityService {
	MicroServiceDTO save(MicroServiceDTO microServiceDto);
	void delete(String serviceName, String product);
	MicroServiceDTO create(MicroServiceDTO microServiceDto);
	MicroServiceDTO findOne(Long id);
}
