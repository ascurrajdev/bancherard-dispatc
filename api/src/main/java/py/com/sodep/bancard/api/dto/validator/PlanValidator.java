package py.com.sodep.bancard.api.dto.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.dto.PlanDTO;
import py.com.sodep.bancard.api.microservices.BancardAPIException;

public class PlanValidator {
	private static final Logger logger = LoggerFactory.getLogger(PlanValidator.class);

	public static void createValidator(final PlanDTO dto) throws BancardAPIException {
		try {
			if (dto == null)
				throw new BancardAPIException("El objecto Plan no puede ser nulo");

			if (dto.getId() != null)
				throw new BancardAPIException("Para la insertar un nuevo registro el ID debe ser null");
		} catch (BancardAPIException e) {
			logger.error("BancardAPIException: " + e);
			throw e;
		} catch (Exception e) {
			logger.error("Exception: " + e);
			throw new BancardAPIException("Ocurrio un error validando el Plan del Behavior: " + e);
		}
	}
}