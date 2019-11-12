package py.com.sodep.bancard.api.dto.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import py.com.sodep.bancard.api.utils.DateHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeSerializer extends StdSerializer<Date> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7147086263025908187L;

	protected DateTimeSerializer() {
		super(Date.class);
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

		SimpleDateFormat sdf = new SimpleDateFormat(DateHelper.DEFAULT_DATE_TIME_FORMAT);
		String result = sdf.format(value);

		jgen.writeString(result);
	}

}
