package py.com.sodep.bancard.api.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.dto.ContactDTO;
import py.com.sodep.bancard.api.dto.MailSenderDTO;
import py.com.sodep.bancard.api.enums.BehaviorComplementFineType;
import py.com.sodep.bancard.api.enums.ParticularMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardTransactionProvider;

public class BancardMSUtils {
	private static final Logger logger = LoggerFactory.getLogger(BancardMSUtils.class);
	
	public BancardMSUtils() {
	}

	public static BigDecimal simpleInterestCalculation(final BehaviorComplementFineType fineType,
			final Double interestRateOrAmount, final BigDecimal capital, final Date initialDue, final Date currentDue)
			throws BancardAPIException {
		BigDecimal timeCoefficient, taxCoefficient, time, interes = new BigDecimal(0);
		switch (fineType) {
		case DIALY_PERCENTAGE:
			time = new BigDecimal(DateHelper.daysBetweenDate(initialDue, currentDue));
			timeCoefficient = time.divide(new BigDecimal(DateHelper.NUMBER_OF_DAYS_IN_A_YEAR), 5, RoundingMode.HALF_UP);
			taxCoefficient = new BigDecimal(interestRateOrAmount.doubleValue() / 100);
			interes = new BigDecimal(capital.doubleValue() * timeCoefficient.doubleValue() * taxCoefficient.doubleValue());
			return capital.add(interes);
		case MONTHLY_PERCENTAGE:
			time = new BigDecimal(DateHelper.monthsBetweenTwoDates(initialDue, currentDue));
			timeCoefficient = time.divide(new BigDecimal(DateHelper.NUMBER_OF_MONTHS_IN_A_YEAR), 5, RoundingMode.HALF_UP);
			taxCoefficient = new BigDecimal(interestRateOrAmount.doubleValue() / 100);
			interes = new BigDecimal(capital.doubleValue() * timeCoefficient.doubleValue() * taxCoefficient.doubleValue());
			return capital.add(interes);
		case ANNUAL_PERCENTAGE:
			time = new BigDecimal(DateHelper.yearsBetweenTwoDates(initialDue, currentDue));
			timeCoefficient = time.divide(new BigDecimal(1), 5, RoundingMode.HALF_UP);
			taxCoefficient = new BigDecimal(interestRateOrAmount.doubleValue() / 100);
			interes = new BigDecimal(capital.doubleValue() * taxCoefficient.doubleValue() * timeCoefficient.doubleValue());
			return capital.add(interes);
		case FIXED_PER_MONTH:
			time = new BigDecimal(DateHelper.monthsBetweenTwoDates(initialDue, currentDue));
			return capital.add(new BigDecimal(time.doubleValue() * interestRateOrAmount));
		case FIXED_PER_YEAR:
			time = new BigDecimal(DateHelper.yearsBetweenTwoDates(initialDue, currentDue));
			return capital.add(new BigDecimal(time.doubleValue() * interestRateOrAmount));
		case FIXED_SINGLE:
			return capital.add(new BigDecimal(interestRateOrAmount));
		default:
			logger.error("No se ha configurado el Tipo de Mora (BehaviorComplementFineType) para este producto");
			throw new BancardAPIException(ParticularMessageKey.QUERY_NOT_PROCESSED,
					"No se ha podido calcular el interes a partir del Behavior (Tipo de Mora)");
		}
	}

	public static void sendMail(final BancardTransactionProvider provider, final String[] to, final String subject, final String text) {
		try {
			if (provider == null) {
				logger.error("Mail con subject: {} no enviado: BancardTransactionProvider NULL",  subject);
				return;
			}
			MailSenderDTO sender = new MailSenderDTO(to, subject, text);
			provider.sendMail(sender, (File[]) null);
		} catch (BancardAPIException e) {
			logger.error("BancardAPIException.SendMail: " + e);
		} catch (Exception e) {
			logger.error("Exception.SendMail: " + e);
		}
	}

	public static String[] getToByContactDtoList(List<ContactDTO> contactDtoList) {
		logger.info("{}", contactDtoList);
		String[] to = new String[contactDtoList.size()];
		int contador = 0;
		for (ContactDTO dto : contactDtoList)
			to[contador++] = dto.getSendTo();
		return to;
	}
	
	public static File getFileByStringBuilder(StringBuilder csvSB, String path) throws BancardAPIException {
		try {
			File file = new File(path);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(csvSB.toString());
			writer.flush();
			writer.close();
			return file;
		} catch (Exception e) {
			logger.error("Exception-getTigoChargeInformation: " + e);
			throw new BancardAPIException(ParticularMessageKey.TRANSACTION_INFORMATION_ERROR, e.getMessage());
		}
	}
	
	public static boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (Exception ex1) {
				return false;
			}
		}
		return true;
	}
}