package py.com.sodep.bancard.api.dto.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.dto.BehaviorDTO;
import py.com.sodep.bancard.api.dto.BehaviorOfflineFileStructureInJson;
import py.com.sodep.bancard.api.dto.OfflineBehaviorComplementsInJson;
import py.com.sodep.bancard.api.enums.BehaviorComplementFineType;
import py.com.sodep.bancard.api.enums.OffLineFileType;
import py.com.sodep.bancard.api.microservices.BancardAPIException;

public class BehaviorValidator {
	private static final Logger logger = LoggerFactory.getLogger(BehaviorValidator.class);

	public static void createValidator(final BehaviorDTO dto) throws BancardAPIException {
		try {
			if (dto == null)
				throw new BancardAPIException("El objecto Behavior no puede ser nulo");

			if (dto.getId() != null)
				throw new BancardAPIException("Para la insertar un nuevo registro el ID debe ser null");

			if (dto.getProductId() == null || dto.getProductId().intValue() <= 0)
				throw new BancardAPIException("Debe ingresar un identificador de producto válido");

			if (dto.getProductUid() == null || dto.getProductUid().intValue() <= 0)
				throw new BancardAPIException("Debe ingresar un identificador universal de producto válido");

			if (dto.getName() == null || "".equals(dto.getName()) || dto.getName().length() <= 0)
				throw new BancardAPIException("Debe ingresar un nombre de producto valido");

			if (dto.getCustomSending() && (dto.getMailFileHeader() == null || "".equals(dto.getMailFileHeader())
					|| dto.getMailFileHeader().length() <= 0))
				throw new BancardAPIException(
						"Los Behavior de tipo CustomSending deben tener una cabecera de envío válido");

			if (dto.getPeriodicity() == null)
				throw new BancardAPIException("Debe ingresar una periodicidad de envíos de correos válido");

			if (dto.getMsOffline()) {

				if (dto.getOfflineNumberOfFields() == null || dto.getOfflineNumberOfFields().intValue() <= 0)
					throw new BancardAPIException(
							"Numero de campos (OfflineNumberOfFields) por registro no puede ser nulo ni menor a uno");

				if (dto.getOfflineBehaviorComplementsInJson() == null)
					throw new BancardAPIException(
							"El Complemento del Producto no puede ser nulo para un Behavior tipo Offline");

				OfflineBehaviorComplementsInJson complement = dto.getOfflineBehaviorComplementsInJson();
				if (complement.getReadFromTheLine() <= 0)
					throw new BancardAPIException("Leer desde la Lina no puede ser nulo o menor a 1, favor verificar");

				if (complement.getBillerDateFormatt() == null)
					throw new BancardAPIException(
							"El Formato de fecha no puede ser nulo para un Behavior tipo Offline");

				if (complement.getAmountOfFeesToGeneratePerRecord() <= 0)
					throw new BancardAPIException(
							"La cantidad de registros a procesar (AmountOfFeesToGeneratePerRecord) no puede ser nulo ni menor a uno para un Behavior tipo Offline");

				if (dto.getOfflineFileType() == null || "".equals(dto.getOfflineFileType()))
					throw new BancardAPIException(
							"Tipo de Archivo no configurador en Behavior tipo Offline");

				if (OffLineFileType.CSV.equals(dto.getOfflineFileType()) && (complement.getDelimited() == null || "".equals(complement.getDelimited())))
					throw new BancardAPIException(
							"El Delimitador de Caracteres no puede ser nulo para un Behavior tipo Offline CSV");

				if (complement.getFineType() == null)
					throw new BancardAPIException(
							"Tipo multa no puede ser nulo para un Behavior tipo Offline. Debe colocar al menos NOT_APPLY");

				if (!BehaviorComplementFineType.NOT_APPLY.equals(complement.getFineType())) {
					if (complement.getFineForUnpaid() == null || complement.getFineForUnpaid().intValue() <= 0)
						throw new BancardAPIException(
								"Behavior tipo Multa. Debe configurar un importe o un porcentaje mayor a cero para configurar la multa");

					if (BehaviorComplementFineType.ANNUAL_PERCENTAGE.equals(complement.getFineType())) {
						if (complement.getFineForUnpaid() == null || complement.getFineForUnpaid().intValue() > 100)
							throw new BancardAPIException(
									"Behavior tipo Multa con porcentaje anual no puede ser superior al 100%");
					}
					if (BehaviorComplementFineType.MONTHLY_PERCENTAGE.equals(complement.getFineType())) {
						if (complement.getFineForUnpaid() == null || complement.getFineForUnpaid().intValue() > 100)
							throw new BancardAPIException(
									"Behavior tipo Multa con porcentaje mensual no puede ser superior al 100%");
					}
					if (BehaviorComplementFineType.DIALY_PERCENTAGE.equals(complement.getFineType())) {
						if (complement.getFineForUnpaid() == null || complement.getFineForUnpaid().intValue() > 100)
							throw new BancardAPIException(
									"Behavior tipo Multa con porcentaje diario no puede ser superior al 100%");
					}
				}

				if (dto.getOfflineFileStructureInJson() == null)
					throw new BancardAPIException(
							"La estructura del Producto no puede ser nulo para un Behavior tipo Offline");

				List<BehaviorOfflineFileStructureInJson> structureInJson = dto.getOfflineFileStructureInJson();

				if (structureInJson == null || structureInJson.isEmpty() || structureInJson.size() <= 0)
					throw new BancardAPIException(
							"La estructura del Producto no puede ser nulo ni puede quedar vacia para un Behavior tipo Offline");

				if (dto.getOfflineNumberOfFields().intValue() != structureInJson.size())
					throw new BancardAPIException(
							"La cantidad de campos (OfflineNumberOfFields) no coinciden con la cantidad de esctructuras del campo (offlineFileStructureInJson)");

				if (dto.getOfflineType() == null)
					throw new BancardAPIException(
							"Tipo Offline (OfflineType)no puede ser nulo ni vacio para un Behavior tipo Offline. Ej: DISP(DISPATCHER), MSER(MICROSERVICES)");
			}
		} catch (BancardAPIException e) {
			logger.error("BancardAPIException: " + e);
			throw e;
		} catch (Exception e) {
			logger.error("Exception: " + e);
			throw new BancardAPIException("Ocurrio un error validando el Behavior: " + e);
		}
	}

	public static void behaviorOffLineValidator(final BehaviorDTO behaviorDTO) throws BancardAPIException {
		if (behaviorDTO == null)
			throw new BancardAPIException("Producto no configurado en Behavior");
		if (behaviorDTO.getProductUid() == null)
			throw new BancardAPIException("Campo Producto no configurado en Behavior");
		if (!behaviorDTO.getMsOffline())
			throw new BancardAPIException("El producto con UniversalID " + behaviorDTO.getProductUid()
					+ " no es un Producto Tipo OffLine en Behavior");
		if (behaviorDTO.getOfflineFileType() == null || "".equals(behaviorDTO.getOfflineFileType()))
			throw new BancardAPIException(
					"Tipo de Archivo no configurador en Behavior para el producto con UniversalID "
							+ behaviorDTO.getProductUid());
		if (behaviorDTO.getOfflineFileStructureInJson() == null
				|| "".equals(behaviorDTO.getOfflineFileStructureInJson()))
			throw new BancardAPIException(
					"La estructura del Archivo no se encuentra configurada en Behavior para el producto con UniversalID "
							+ behaviorDTO.getProductUid());
		if (behaviorDTO.getOfflineBehaviorComplementsInJson() == null
				|| "".equals(behaviorDTO.getOfflineBehaviorComplementsInJson()))
			throw new BancardAPIException("Complemento no configurado en Behavior para el producto con UniversalID "
					+ behaviorDTO.getProductUid());
		if (behaviorDTO.getOfflineNumberOfFields() == null || behaviorDTO.getOfflineNumberOfFields().intValue() <= 0)
			throw new BancardAPIException(
					"Favor configurar cantidad de elementos (campos) en Behavior para el producto con UniversalID "
							+ behaviorDTO.getProductUid());

		if (behaviorDTO.getOfflineType() == null || "".equals(behaviorDTO.getOfflineType()))
			throw new BancardAPIException("Favor configurar OffLineType en Behavior para el producto con UniversalID "
					+ behaviorDTO.getProductUid());

		if (behaviorDTO == null || behaviorDTO.getOfflineBehaviorComplementsInJson() == null)
			throw new BancardAPIException("Complemento no configurado en BehaviorDTO para el producto con UniversalID "
					+ behaviorDTO.getProductUid());

		if (behaviorDTO.getOfflineType() == null)
			throw new BancardAPIException(
					"Favor configurar OffLineType en BehaviorDTO para el producto con UniversalID "
							+ behaviorDTO.getProductUid());

		OfflineBehaviorComplementsInJson complement = behaviorDTO.getOfflineBehaviorComplementsInJson();
		if (complement.getAmountOfFeesToGeneratePerRecord() <= 0)
			throw new BancardAPIException(
					"Cantidad de registros a generar no puede ser nulo ni menor o igual a cero (Complemento) para el producto con UniversalID "
							+ behaviorDTO.getProductUid());

		if (!behaviorDTO.getMsOffline())
			throw new BancardAPIException("El producto con UniversalID " + behaviorDTO.getProductUid()
					+ " no se un tipo OffLine en Behavior");
	}
}