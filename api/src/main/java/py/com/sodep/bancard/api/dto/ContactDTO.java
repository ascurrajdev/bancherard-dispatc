package py.com.sodep.bancard.api.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "sendTo", "contact", "behaviorId" })
public class ContactDTO implements Serializable {

	private static final long serialVersionUID = -8006003445124051176L;

	private BigInteger id;
	private String sendTo;
	private String contact;
	private BigInteger behaviorId;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public BigInteger getId() {
		return id;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public BigInteger getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(BigInteger behaviorId) {
		this.behaviorId = behaviorId;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public static String[] contactListToStringArray(List<ContactDTO> list) {
		if (list == null || list.size() == 0)
			return null;
		String[] response = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			response[i] = list.get(i).getSendTo();
		return response;
	}

	@Override
	public String toString() {
		return "ContactDTO [id=" + id + ", sendTo=" + sendTo + ", contact=" + contact + ", behaviorId=" + behaviorId
				+ "]";
	}
}