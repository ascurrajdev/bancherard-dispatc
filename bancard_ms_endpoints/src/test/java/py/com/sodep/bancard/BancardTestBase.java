package py.com.sodep.bancard;

import java.io.InputStream;

import py.com.sodep.bancard.dto.MicroServiceDTO;
import py.com.sodep.bancard.rest.PaymentRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class  BancardTestBase {

	protected final ObjectMapper mapper = new ObjectMapper();

	public static final String MS_MOCK_JSON = "/mock_ms.json";
	
	protected MicroServiceDTO readMockMS() {
	    MicroServiceDTO obj;
	    try {
	        InputStream inputStream = BancardTestBase.class.getResourceAsStream(MS_MOCK_JSON);
	        obj = mapper.readValue(inputStream, MicroServiceDTO.class);
	        obj.setServiceName("ms-dummy");
	        obj.setProduct("product-dummy");
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	    return obj;
	}
	
	protected Object readValue(String resource, Class<?> classValue) {
	    Object obj;
	    try {
	        InputStream inputStream = BancardTestBase.class.getResourceAsStream(resource);
	        obj = mapper.readValue(inputStream, classValue);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	    return obj;
	}

	protected String toJsonString(Object request) {
        try {
            return this.mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}