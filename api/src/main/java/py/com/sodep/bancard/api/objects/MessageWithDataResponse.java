package py.com.sodep.bancard.api.objects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "messages", "data" })
public class MessageWithDataResponse<T> {

	@JsonProperty("status")
	private String status;
	@JsonProperty("messages")
	private List<BancardMessage> messages;
	private T data;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<BancardMessage> getMessages() {
		return messages;
	}
	public void setMessages(List<BancardMessage> messages) {
		this.messages = messages;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public void setOk(String msg) {
		this.setStatus(BancardResponse.RESPONSE_STATUS_SUCCESS);
		this.setMessages(BancardMessage.getOneSuccessList(BancardMessage.LEVEL_SUCCESS, msg));
	}

	public void setInfo(String msg) {
		this.setStatus(BancardMessage.LEVEL_INFO);
		this.setMessages(BancardMessage.getOneSuccessList(BancardMessage.LEVEL_INFO, msg));
	}

	public void setError(String msg) {
		this.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		this.setMessages(BancardMessage.getOneSuccessList(BancardMessage.LEVEL_ERROR, msg));
	}

	@Override
	public String toString() {
		return "MessageWithDataResponse [status=" + status + ", messages=" + messages + ", data=" + data + "]";
	}
}