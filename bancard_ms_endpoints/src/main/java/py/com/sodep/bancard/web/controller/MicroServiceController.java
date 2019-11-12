package py.com.sodep.bancard.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.domain.MicroServiceEntity;
import py.com.sodep.bancard.dto.MicroServiceConverter;
import py.com.sodep.bancard.dto.MicroServiceDTO;
import py.com.sodep.bancard.repository.IMicroServiceRepository;
import py.com.sodep.bancard.services.MicroServiceEntityService;
import py.com.sodep.bancard.services.MicroServiceManager;
import py.com.sodep.bancard.services.impl.MicroService;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/admin/services")
public class MicroServiceController extends BancardMSController {
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceController.class);

	@Autowired
	private IMicroServiceRepository microServiceRepository;

	@Autowired
	private MicroServiceManager microServiceManager;

	@Autowired
	private MicroServiceEntityService microServiceEntityService;

	@ApiOperation(value = "Guarda un nuevo microservicio en el dispatcher", notes = "Guarda un nuevo microservicio en el dispatcher junto a sus propiedades")
    @RequestMapping(value="/{name}/{product}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<BancardResponse> create(
			@ApiParam(value = "Nombre del servicio que queremos crear", required = true) @PathVariable String name,
			@ApiParam(value = "Nombre del producto que el servicio ofrece. Esto, en conjunto al nombre, identifica de forma única al servicio", required = true) @PathVariable String product,
			@ApiParam(value = "true se quiere cargar el servicio al dispatcher") @RequestParam(value="load", required=false, defaultValue="false") Boolean load,
			@RequestBody MicroServiceDTO microServiceDto) throws MissingServletRequestParameterException {
		checkMsParameters(microServiceDto);
		microServiceDto.setServiceName(name);
		microServiceDto.setProduct(product);
		microServiceEntityService.create(microServiceDto);
		if (load) {
			microServiceManager.reloadMicroService(name, product);
		}
		BancardResponse response = BancardResponse.getSuccessNoTx(GenericMessageKey.MICRO_SERVICE_CREATE_SUCCESS);
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}

	@ApiOperation(value = "Actualiza un microservicio en el dispatcher", notes = "Actualiza un microservicio en el dispatcher junto a sus propiedades")
    @RequestMapping(value="/{name}/{product}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<BancardResponse> update(
			@ApiParam(value = "Nombre del servicio que queremos actualizar", required = true) @PathVariable String name,
			@ApiParam(value = "Nombre del producto que el servicio ofrece. Esto, en conjunto al nombre, identifica de forma única al servicio", required = true) @PathVariable String product,
			@ApiParam(value = "true se quiere cargar el servicio al dispatcher") @RequestParam(value="load", required=false, defaultValue="false") Boolean load,
			@RequestBody MicroServiceDTO microServiceDto) throws MissingServletRequestParameterException {
		microServiceDto.setServiceName(name);
		microServiceDto.setProduct(product);
		MicroServiceDTO save = microServiceEntityService.save(microServiceDto);
		if (save != null) {
			if (load) {
				microServiceManager.reloadMicroService(name, product);
			}
			BancardResponse response = BancardResponse.getSuccessNoTx(GenericMessageKey.MICRO_SERVICE_UPDATE_SUCCESS);
			return new ResponseEntity<BancardResponse>(response, statusCode(response));
		}
		logger.error("Microservice was not found: " + microServiceDto);
		BancardResponse response = BancardResponse.getSuccessNoTx(GenericMessageKey.MICRO_SERVICE_NOT_FOUND);
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}

	@ApiOperation(value = "Borra un microservicio", notes = "Borra un microservicio")
    @RequestMapping(value="/{name}/{product}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BancardResponse> delete(
			@ApiParam(value = "Nombre del servicio que queremos borrar", required = true) @PathVariable String name,
			@ApiParam(value = "Nombre del producto que el servicio ofrece. Esto, en conjunto al nombre, identifica de forma única al servicio", required = true) @PathVariable String product) {
		microServiceEntityService.delete(name, product);
		BancardResponse response = BancardResponse.getSuccessNoTx(GenericMessageKey.MICRO_SERVICE_UPDATE_SUCCESS);
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}

	@ApiOperation(value = "Hace una recarga de un microservicio en el dispatcher", notes = "Carga la implementación de un microservicio almacencado en el dispatcher.")
    @RequestMapping(value= "/{name}/{product}/reload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<BancardResponse> reloadService(
			@ApiParam(value = "Nombre del servicio que queremos recargar", required = true) @PathVariable String name,
			@ApiParam(value = "Nombre del producto que el servicio ofrece. Esto, en conjunto al nombre, identifica de forma única al servicio", required = true) @PathVariable String product) {
		microServiceManager.reloadMicroService(name, product);
		BancardResponse response = BancardResponse.getSuccessNoTx(GenericMessageKey.MICRO_SERVICE_RELOAD_SUCCESS);
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}

	@ApiOperation(value = "Hace una recarga de todos los microservicios del dispatcher", notes = "Carga la implementación de cada microservicio almacencado en el dispatcher.")
    @RequestMapping(value= "/reload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<BancardResponse> reload() {
		microServiceManager.reload();
		BancardResponse response = BancardResponse.getSuccessNoTx(GenericMessageKey.MICRO_SERVICE_RELOAD_SUCCESS);
		return new ResponseEntity<BancardResponse>(response, statusCode(response));
	}

	@ApiOperation(value = "Lista el estado de todos los microservicios", notes = "Lista el estado de todos los microservicios cargados en la memoria del dispatcher")
    @RequestMapping(value="/status",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MicroServiceDTO>> status() {
		List<MicroServiceDTO> list = microServiceManager.listAll();
		return new ResponseEntity<List<MicroServiceDTO>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "Muestra el estado de un microservicio", notes = "Muestra el estado de un microservicio cargado en la memoria del dispatcher")
    @RequestMapping(value= "/{name}/{product}/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MicroServiceDTO> statusByService(
			@ApiParam(value = "Nombre del servicio cuyo estado queremos ver", required = true) @PathVariable String name, 
			@ApiParam(value = "Nombre del producto que el servicio ofrece. Esto, en conjunto al nombre, identifica de forma única al servicio", required = true) @PathVariable String product) {
		MicroService microService = microServiceManager.getMicroService(name, product);
		MicroServiceEntity  entity = microServiceRepository.findByServiceNameAndProduct(name, product);
		MicroServiceDTO dto = MicroServiceConverter.toStatusDTO(entity, microService.toDTO());
		return new ResponseEntity<MicroServiceDTO>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "Resetea el estado de un microservicio", notes = "Pone a cero los contadores de errores y timeout. Esto volverá activarlo para que pueda recibir peticiones.")
    @RequestMapping(value= "/{name}/{product}/status/reset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MicroServiceDTO> reset(
			@ApiParam(value = "Nombre del servicio cuyo estado queremos resetear", required = true) @PathVariable String name, 
			@ApiParam(value = "Nombre del producto que el servicio ofrece. Esto, en conjunto al nombre, identifica de forma única al servicio", required = true) @PathVariable String product) {
		MicroService microService = microServiceManager.getMicroService(name, product);
		microService.resetStatus();
		MicroServiceEntity  entity = microServiceRepository.findByServiceNameAndProduct(name, product);
		MicroServiceDTO dto = MicroServiceConverter.toStatusDTO(entity, microService.toDTO());
		return new ResponseEntity<MicroServiceDTO>(dto, HttpStatus.OK);
	}
}
