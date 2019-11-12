package py.com.sodep.bancard.dto;


public class UnexpectedErrorDTO {
	
	private String exceptionType;
    private String insertTime;
    private String url;
    private String userAgent;
    private String stackTrace;
	private String microServiceName;
	
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getMicroServiceName() {
		return microServiceName;
	}
	public void setMicroServiceName(String microServiceName) {
		this.microServiceName = microServiceName;
	}

}
