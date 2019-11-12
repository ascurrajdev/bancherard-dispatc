package py.com.bancard.microservice.cit.util;

import java.text.NumberFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.enums.ParticularMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;

public class CitUtil {
	
	private CitUtil(){}
	
	public static final String LOCALE = "es-PY";
	
	static final Logger logger = LoggerFactory.getLogger(CitUtil.class);

	public static String zerosToLeft(int number, int totalLength) {
		if (String.valueOf(number).length() > totalLength)
			return String.valueOf(number);
		String format = "%0" + totalLength + "d";
		return String.format(format, number);
	}

	public static String amountFormat(String locale, int value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag(locale));
	    return numberFormat.format(value);
	}
	
	public static String amountFormat(int value) {
		return amountFormat(LOCALE, value);
	}

	public static String nvl(String value) {
		return trim((value == null ? "-" : value));
	}

	public static String trim(String value) {
		return ("".equals(value.trim()) ? "-" : value);
	}
	
	public static String extractDigits(String src) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < src.length(); i++) {
	        char c = src.charAt(i);
	        if (Character.isDigit(c)) {
	            builder.append(c);
	        }
	    }
	    return builder.toString();
	}
	
	 public static String getConsecutivo(Long transactionId) throws BancardAPIException {
	        try {
	                String amt = String.valueOf(transactionId);
	                amt = (amt.length() > 10 ? amt.substring(amt.length() - 10) : amt);
	                return amt;     
	        } catch (Exception e) {
	                logger.error("GetConsecutivo.Exception: ", e);
	                throw new BancardAPIException(ParticularMessageKey.PAYMENT_NOT_AUTHORIZED, "EL PAGO NO PUDO SER PROCESADO:");
	        }
	 }
	 
	 public static String getConsecutivoReverse(Long transactionId) throws BancardAPIException {
	        try {
	                String amt = String.valueOf(transactionId);
	                amt = (amt.length() > 10 ? amt.substring(amt.length() - 10) : amt);
	                return amt;     
	        } catch (Exception e) {
	                logger.error("GetConsecutivo.Exception: ", e);
	                throw new BancardAPIException(ParticularMessageKey.TRANSACTION_NOT_REVERSED, "EL PAGO NO PUDO SER REVERSADO:");
	        }
	 }
}