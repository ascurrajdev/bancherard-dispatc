package py.com.sodep.bancard.api.objects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonResponse {
	/*
	** Attribute
	************/
	@JsonProperty("tid")
	protected Long transactionId;
	@JsonProperty("status")
	protected String status;
	@JsonProperty("messages")
	protected List<BancardMessage> messages;
	
	/*
	** Getters, Setters
	*******************/
	public Long getTransactionId() {
	    return transactionId;
	}

	public void setTransactionId(Long transactionId) {
	    this.transactionId = transactionId;
	}

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

	@Override
	public String toString() {
		return "CommonMessage [transactionId=" + transactionId + ", status="
				+ status + ", messages=" + messages + "]";
	}
}
