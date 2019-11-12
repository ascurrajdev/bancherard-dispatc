package py.com.sodep.bancard.api.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import py.com.sodep.bancard.api.microservices.BancardAPIException;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONTransformer<T> implements Serializable{
	private static final long serialVersionUID = 5069598263165124128L;

	private Class<T> cls;

    public JSONTransformer(Class<T> cls) {
        this.cls = cls;
    }

    public T fromJSON(String jsondata) throws BancardAPIException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            T response = (T) mapper.readValue(jsondata, this.cls);
            return response;
        } catch (IOException e) {
            throw new BancardAPIException(e);
        }
    }

    public String toJSON(T instance) throws BancardAPIException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(instance);
            return response;
        } catch (IOException e) {
            throw new BancardAPIException(e);
        }
    }

    @SuppressWarnings("unchecked")
	public List<T> toList(String jsondata) throws BancardAPIException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType t = mapper.getTypeFactory().constructCollectionType(List.class, this.cls);
            return  (List<T>)mapper.readValue(jsondata, t);
        } catch (IOException e) {
            throw new BancardAPIException(e);
        }
    }

    public String fromList(List<T> list) throws BancardAPIException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(list);
            return response;
        } catch (IOException e) {
            throw new BancardAPIException(e);
        }
    }

    @SuppressWarnings("unchecked")
	public Map<String, Object> toMap(String jsonString) throws BancardAPIException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> response = mapper.readValue(jsonString, HashMap.class);
            return response;
        } catch (IOException e) {
            throw new BancardAPIException(e);
        }
    }

    public String fromMap(Map<String, String> map) throws BancardAPIException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(map);
            return response;
        } catch (IOException e) {
            throw new BancardAPIException(e);
        }
    }

    public static interface JAXBElementMixin {
        @JsonValue
        Object getValue();
    }

    public String fromJAXBElement(JAXBElement<T> jaxbElement) throws BancardAPIException {
    	try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixIn(JAXBElement.class, JAXBElementMixin.class);
            String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jaxbElement);
            return response;
        } catch (IOException e) {
            throw new BancardAPIException(e);
        }
    }
}