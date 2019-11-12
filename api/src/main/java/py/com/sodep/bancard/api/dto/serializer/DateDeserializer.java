package py.com.sodep.bancard.api.dto.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import py.com.sodep.bancard.api.utils.DateHelper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateDeserializer extends JsonDeserializer<Date> {
	private static final Logger logger = LoggerFactory.getLogger(DateDeserializer.class);

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
    	SimpleDateFormat format1 = new SimpleDateFormat(DateHelper.SIMPLE_DAY_FORMAT);
    	SimpleDateFormat format2 = new SimpleDateFormat(DateHelper.DEFAULT_DATE_TIME_MILL_FORMAT);
    	SimpleDateFormat format3 = new SimpleDateFormat(DateHelper.SIMPLE_DAY_TIME_FORMAT_TssZ);
    	SimpleDateFormat format4 = new SimpleDateFormat(DateHelper.SIMPLE_DAY_TIME_FORMAT_TsssZ);
    	SimpleDateFormat format5 = new SimpleDateFormat(DateHelper.SIMPLE_DAY_TIME_FORMAT_FOR_UQ_FILE);
    	String date = jsonparser.getText();
        try {
            return format1.parse(date);
        } catch (ParseException e1) {
        	logger.error("ParseException1: " + e1);
        	try {
                return format2.parse(date);
			} catch (ParseException e2) {
				logger.error("ParseException2: " + e2);
				try {
	                return format3.parse(date);
				} catch (ParseException e3) {
					logger.error("ParseException3: " + e3);
					try {
		                return format4.parse(date);
					} catch (ParseException e4) {
						logger.error("ParseException4: " + e4);
						try {
			                return format5.parse(date);
						} catch (ParseException e5) {
							logger.error("ParseException5: " + e5);
				            throw new RuntimeException(e5);
						}
					}
				}
			}
        }
    }
}