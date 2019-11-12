package py.com.sodep.bancard.api.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "mailingListId", "mailingListType", "cronExpression", "defaultSubject", "defaultBody",
		"urlConfig", "pathConfig", "authenticationRequired", "userAuthentication", "passAuthentication", "status",
		"createdAt", "updatedAt" })
public class MailingListConfigDTO implements Serializable {
	private static final long serialVersionUID = -5501088021115469409L;

	private BigInteger id;
	private Integer mailingListId;
	private String mailingListType;
	private String cronExpression;
	private String defaultSubject;
	private String defaultBody;
	private String urlConfig;
	private String pathConfig;
	private boolean authenticationRequired;
	private String userAuthentication;
	private String passAuthentication;
	private String status;
	private Date createdAt;
	private Date updatedAt;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getMailingListId() {
		return mailingListId;
	}

	public void setMailingListId(Integer mailingListId) {
		this.mailingListId = mailingListId;
	}

	public String getMailingListType() {
		return mailingListType;
	}

	public void setMailingListType(String mailingListType) {
		this.mailingListType = mailingListType;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDefaultSubject() {
		return defaultSubject;
	}

	public void setDefaultSubject(String defaultSubject) {
		this.defaultSubject = defaultSubject;
	}

	public String getDefaultBody() {
		return defaultBody;
	}

	public void setDefaultBody(String defaultBody) {
		this.defaultBody = defaultBody;
	}

	public String getUrlConfig() {
		return urlConfig;
	}

	public void setUrlConfig(String urlConfig) {
		this.urlConfig = urlConfig;
	}

	public String getPathConfig() {
		return pathConfig;
	}

	public void setPathConfig(String pathConfig) {
		this.pathConfig = pathConfig;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public void setAuthenticationRequired(boolean authenticationRequired) {
		this.authenticationRequired = authenticationRequired;
	}

	public String getUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(String userAuthentication) {
		this.userAuthentication = userAuthentication;
	}

	public String getPassAuthentication() {
		return passAuthentication;
	}

	public void setPassAuthentication(String passAuthentication) {
		this.passAuthentication = passAuthentication;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (authenticationRequired ? 1231 : 1237);
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((cronExpression == null) ? 0 : cronExpression.hashCode());
		result = prime * result + ((defaultBody == null) ? 0 : defaultBody.hashCode());
		result = prime * result + ((defaultSubject == null) ? 0 : defaultSubject.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mailingListId == null) ? 0 : mailingListId.hashCode());
		result = prime * result + ((mailingListType == null) ? 0 : mailingListType.hashCode());
		result = prime * result + ((passAuthentication == null) ? 0 : passAuthentication.hashCode());
		result = prime * result + ((pathConfig == null) ? 0 : pathConfig.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((urlConfig == null) ? 0 : urlConfig.hashCode());
		result = prime * result + ((userAuthentication == null) ? 0 : userAuthentication.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailingListConfigDTO other = (MailingListConfigDTO) obj;
		if (authenticationRequired != other.authenticationRequired)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (cronExpression == null) {
			if (other.cronExpression != null)
				return false;
		} else if (!cronExpression.equals(other.cronExpression))
			return false;
		if (defaultBody == null) {
			if (other.defaultBody != null)
				return false;
		} else if (!defaultBody.equals(other.defaultBody))
			return false;
		if (defaultSubject == null) {
			if (other.defaultSubject != null)
				return false;
		} else if (!defaultSubject.equals(other.defaultSubject))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mailingListId == null) {
			if (other.mailingListId != null)
				return false;
		} else if (!mailingListId.equals(other.mailingListId))
			return false;
		if (mailingListType == null) {
			if (other.mailingListType != null)
				return false;
		} else if (!mailingListType.equals(other.mailingListType))
			return false;
		if (passAuthentication == null) {
			if (other.passAuthentication != null)
				return false;
		} else if (!passAuthentication.equals(other.passAuthentication))
			return false;
		if (pathConfig == null) {
			if (other.pathConfig != null)
				return false;
		} else if (!pathConfig.equals(other.pathConfig))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (urlConfig == null) {
			if (other.urlConfig != null)
				return false;
		} else if (!urlConfig.equals(other.urlConfig))
			return false;
		if (userAuthentication == null) {
			if (other.userAuthentication != null)
				return false;
		} else if (!userAuthentication.equals(other.userAuthentication))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MailingListConfigDTO [id=" + id + ", mailingListId=" + mailingListId + ", mailingListType="
				+ mailingListType + ", cronExpression=" + cronExpression + ", defaultSubject=" + defaultSubject
				+ ", defaultBody=" + defaultBody + ", urlConfig=" + urlConfig + ", pathConfig=" + pathConfig
				+ ", authenticationRequired=" + authenticationRequired + ", userAuthentication=" + userAuthentication
				+ ", passAuthentication=" + passAuthentication + ", status=" + status + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", additionalProperties=" + additionalProperties + "]";
	}
}