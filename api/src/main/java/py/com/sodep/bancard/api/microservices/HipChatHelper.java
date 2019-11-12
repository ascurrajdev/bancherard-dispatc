package py.com.sodep.bancard.api.microservices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HipChatHelper {
	private static final Logger logger = LoggerFactory.getLogger(HipChatHelper.class);
	private static final String ENV_DEFAULT = "PRODUCTION";

	private static RestTemplate template = new RestTemplate(getClientHttpRequestFactory(15000));

	private Properties prop = new Properties();
	private InputStream input = null;

	private String env;
	private int roomId;
	private String url;
	private String token;
	private String color;
	private String msgFormat;

	public HipChatHelper(){
		try {
			input = new FileInputStream("/opt/bancard/envs/application.yml");
			prop.load(input);
			this.env = prop.getProperty("hipchat.notification.env");
			this.roomId = Integer.valueOf(prop.getProperty("hipchat.notification.roomid"));
			this.url = prop.getProperty("hipchat.notification.url");
			this.token = prop.getProperty("hipchat.notification.token");
			this.color = prop.getProperty("hipchat.notification.json.color");
			this.msgFormat = prop.getProperty("hipchat.notification.json.message_format");
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		} catch (RuntimeException e) {
			logger.error("RuntimeException: ", e);
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
	}

	private static ClientHttpRequestFactory getClientHttpRequestFactory(int timeOut) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);
		httpRequestFactory.setConnectTimeout(timeOut);
		httpRequestFactory.setConnectionRequestTimeout(timeOut);
		return httpRequestFactory;
	}

	public void sendNotificationHipChat(String msg) {
		try {
			if (ENV_DEFAULT.equals(this.env)) {
				HipChatNotificationDto request = new HipChatNotificationDto();
				request.setColor(this.color);
				request.setMsgFmt(this.msgFormat);
				request.setMsg("@here " + msg);
				request.setNotify(true);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				Map<String, String> vars = new HashMap<>();
				vars.put(HipChatNotificationDto.ROOM_ID_PARAM_NAME, String.valueOf(this.roomId));
				HttpEntity<HipChatNotificationDto> entity = new HttpEntity<>(request, headers);
				template.exchange(this.url + this.token, HttpMethod.POST, entity, String.class, vars);
			}
		} catch (RuntimeException e) {
			logger.error("RuntimeException sendNotificationHipChat: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Exception sendNotificationHipChat: " + e.getMessage(), e);
		}
	}

	public class HipChatNotificationDto {
		public static final String ROOM_ID_PARAM_NAME = "room_id";

		@JsonProperty("color")
		private String color;
		@JsonProperty("notify")
		private boolean notify;
		@JsonProperty("message_format")
		private String msgFmt;
		@JsonProperty("message")
		private String msg;

		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public boolean isNotify() {
			return notify;
		}
		public void setNotify(boolean notify) {
			this.notify = notify;
		}
		public String getMsgFmt() {
			return msgFmt;
		}
		public void setMsgFmt(String msgFmt) {
			this.msgFmt = msgFmt;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}

		@Override
		public String toString() {
			return "HipChatNotificationDto [color=" + color + ", notify=" + notify + ", msgFmt=" + msgFmt + ", msg="
					+ msg + "]";
		}
	}

	@Override
	public String toString() {
		return "HipChatHelper [env=" + env + ", roomId=" + roomId + ", url=" + url + ", token=" + token + ", color="
				+ color + ", msgFormat=" + msgFormat + "]";
	}
}