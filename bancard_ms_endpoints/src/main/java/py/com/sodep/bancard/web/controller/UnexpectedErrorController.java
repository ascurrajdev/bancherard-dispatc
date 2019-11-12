package py.com.sodep.bancard.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.domain.UnexpectedErrorEntity;
import py.com.sodep.bancard.dto.UnexpectedErrorDTO;
import py.com.sodep.bancard.services.UnexpectedErrorService;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Controlador que acepta consultas sobre los errores producidos en el
 * Dispatcher y registrados en {@link UnexpectedErrorEntity}.
 */
@RestController
public class UnexpectedErrorController {

	private static final Logger logger = LoggerFactory.getLogger(UnexpectedErrorController.class);
	
	@Autowired
	private UnexpectedErrorService errorService;
	
	
	@ApiOperation(value = "Retorna una lista de errores de los microservicios", notes = "Se puede consultar la lista de errores de forma paginada")
    @RequestMapping(value="/admin/services/errors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UnexpectedErrorDTO>> get(
			@ApiParam(value="Página que se quiere consultar") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@ApiParam(value="Cantidad de filas por página que se quiere traer en la consulta") @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows,
			@ApiParam(value="Columna por la cual se quiere ordenar el resultado de la consulta") @RequestParam(value = "orderBy", required = false) String orderBy,
			@ApiParam(value="Tipo de orden del resultado de la consulta", allowableValues="asc, desc") @RequestParam(value = "order", required = false) String order) {

		logger.trace("Loading unexpected exception report");
		boolean ascending = true;
		if (order != null) {
			ascending = order.equalsIgnoreCase("asc");
		}
		List<UnexpectedErrorDTO> list = errorService.findAll(orderBy, ascending, page, rows);
		return new ResponseEntity<List<UnexpectedErrorDTO>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna una lista de errores de un microservicio dado", notes = "Se puede consultar la lista de errores de forma paginada")
    @RequestMapping(value="/admin/services/{name}/{product}/errors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UnexpectedErrorDTO>> getByService(
			@ApiParam(value = "Nombre del servicio cuyos errores queremos ver", required = true) @PathVariable String name, 
			@ApiParam(value = "Nombre del producto que ofrece el servicio. Este parámetro, en conjunto a su nombre, identifican de forma única al servicio", required = true) @PathVariable String product,
			@ApiParam(value="Página que se quiere consultar") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@ApiParam(value="Cantidad de filas por página que se quiere traer en la consulta") @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows,
			@ApiParam(value="Columna por la cual se quiere ordenar el resultado de la consulta") @RequestParam(value = "orderBy", required = false) String orderBy,
			@ApiParam(value="Tipo de orden del resultado de la consulta", allowableValues="asc, desc") @RequestParam(value = "order", required = false) String order) {

		logger.trace("Loading unexpected exception report");
		boolean ascending = true;
		if (order != null) {
			ascending = order.equalsIgnoreCase("asc");
		}
		List<UnexpectedErrorDTO> list = errorService.findByService(name, product, orderBy, ascending, page, rows);
		return new ResponseEntity<List<UnexpectedErrorDTO>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "Hace una limpieza de todos los errores registrados en el dispatcher", notes = "Limpia la tabla donde se van registrando los errores que arrojan los microservicios")
    @RequestMapping(value="/admin/services/errors/clean", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BancardResponse> clean(HttpServletRequest request) {
		errorService.clean();
		BancardResponse response = BancardResponse.getSuccessNoTx(GenericMessageKey.ERROR_CLEAN_SUCCESS);
		return new ResponseEntity<BancardResponse>(response, HttpStatus.OK);
	}
}
