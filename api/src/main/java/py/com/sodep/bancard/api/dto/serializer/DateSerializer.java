package py.com.sodep.bancard.api.dto.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import py.com.sodep.bancard.api.utils.DateHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateSerializer extends StdSerializer<Date> {
	private static final long serialVersionUID = -6075341992699873019L;
	private static final Logger logger = LoggerFactory.getLogger(DateSerializer.class);

	protected DateSerializer() {
		super(Date.class);
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateHelper.SIMPLE_DAY_FORMAT);
			String result = sdf.format(value);
			jgen.writeString(result);
		} catch (Exception e) {
			logger.error("Exception: + " + e);
            throw new RuntimeException(e);
		}
	}

}
