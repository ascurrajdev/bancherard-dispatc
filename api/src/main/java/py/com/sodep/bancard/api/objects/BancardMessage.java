package py.com.sodep.bancard.api.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import py.com.sodep.bancard.api.enums.GenericMessageKey;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "level", "key", "dsc" })
public class BancardMessage {
	/**
     * Levels of messages
     */
    public static final String LEVEL_INFO = "info";
    public static final String LEVEL_SUCCESS = "success";
    public static final String LEVEL_ERROR = "error";
    public static final String LEVEL_WARNING = "warning";
    
    public BancardMessage() {
		this.level = LEVEL_SUCCESS;
		this.key = null;
		this.dsc = null;
	}
    
    public BancardMessage(String level) {
		this.level = level;
		this.key = null;
		this.dsc = null;
	}

	@JsonProperty("level")
    private String level;
	@JsonProperty("key")
    private String key;
	@JsonProperty("dsc")
    private List<String> dsc = new ArrayList<String>();
	@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The level
     */
    @JsonProperty("level")
    public String getLevel() {
        return level;
    }

    /**
     * @param level The level
     */
    @JsonProperty("level")
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return The key
     */
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return The dsc
     */
    @JsonProperty("dsc")
    public List<String> getDsc() {
        return dsc;
    }

    /**
     * @param dsc The dsc
     */
    @JsonProperty("dsc")
    public void setDsc(List<String> dsc) {
        this.dsc = dsc;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonIgnore
	public GenericMessageKey getMessageKey() {
		return GenericMessageKey.fromKey(this.key);
	}
    
    public static List<BancardMessage> getOneSuccessList(String level, String msg) {
    	List<BancardMessage> list = new ArrayList<BancardMessage>();
    	BancardMessage m = new BancardMessage();
    	m.setKey(GenericMessageKey.QUERY_PROCESSED.getKey());
    	m.setDsc(Arrays.asList(new String[] {msg}));
    	m.setLevel(level);
    	list.add(m);
    	return Collections.unmodifiableList(list);
    }

    public static List<BancardMessage> getOneErrorList(String key, String msg) {
    	List<BancardMessage> list = new ArrayList<BancardMessage>();
    	BancardMessage m = new BancardMessage();
    	m.setKey(GenericMessageKey.QUERY_NOT_PROCESSED.getKey());
    	m.setDsc(Arrays.asList(new String[] {msg}));
    	m.setLevel(LEVEL_ERROR);
    	list.add(m);
    	return Collections.unmodifiableList(list);
    }

	@Override
	public String toString() {
		return "BancardMessage [level=" + level + ", key=" + key + ", dsc="
				+ dsc + ", additionalProperties=" + additionalProperties + "]";
	}
}