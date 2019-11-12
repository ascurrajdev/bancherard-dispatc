package py.com.sodep.bancard.domain;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "unexpected_errors", schema = "bancard")
@SequenceGenerator(name = "unexpected_errors_id_seq", sequenceName = "bancard.unexpected_errors_id_seq", allocationSize=1)
public class UnexpectedErrorEntity {
	
	public static final int MAX_EXCEPTIONTYPE_LENGTH = 250;
	public static final int MAX_OFFENDINGCLASS_LENGTH = 250;
	public static final int MAX_STACKTRACE_LENGTH = 5000;
	public static final int MAX_URL_LENGTH = 250;
	public static final int MAX_USERAGENT_LENGTH = 250;

	
    private Long id;
    private String exceptionType;
    private Timestamp insertTime;
    private String url;
    private String userAgent;
    private String stackTrace;
	private String microServiceName;

    @Id
    @Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="unexpected_errors_id_seq")
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "exception_type", length = MAX_EXCEPTIONTYPE_LENGTH)
	public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Column(name = "insert_time", nullable = false)
	public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }
    
    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "user_agent")
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


    @Column(name = "stack_trace", length = MAX_STACKTRACE_LENGTH)
	public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Column(name = "microservice_name")
    public String getMicroServiceName() {
    	return this.microServiceName;
    }
    
    public void setMicroServiceName(String microServiceName) {
		this.microServiceName = microServiceName;
	}

	@Override
	public String toString() {
		return "UnexpectedErrorEntity [id=" + id + ", exceptionType="
				+ exceptionType + ", insertTime=" + insertTime + ", url=" + url
				+ ", userAgent=" + userAgent + ", microServiceName=" + microServiceName + "]";
	}
}
