package py.com.sodep.bancard.api.objects;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMSException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BancardResponse extends CommonResponse {
	private static final  Logger logger = LoggerFactory.getLogger(BancardResponse.class);
	public static final String RESPONSE_STATUS_SUCCESS = "success";
	public static final String RESPONSE_STATUS_ERROR = "error";
    public static int AUT_CODE_LAST_DIGITS = 6;

    /*
    ** Constructors
    **************/
    public BancardResponse() {
        // ---------------- Atención developers del mañana ---------------
        // este debería ser el único constructor público para que
        // se pueda serializar esta clase con Jackson (u otro).
        // Para construir objetos de esta clase usar los métodos estáticos
    	this.status = RESPONSE_STATUS_SUCCESS;
    	this.messages = new ArrayList<BancardMessage>();
    }


    /*
    ** Attribute
    ************/
    @JsonProperty("tkt")
    private Long tkt;
    @JsonProperty("invoices")
    private List<Invoice> invoices;
	@JsonProperty("prnt_msg")
	private List<String> printMessagePIC;
	@JsonProperty("prt_msg")
	private List<String> printMessageTIC;
    @JsonProperty("error_code")
	private String errorCode;
    @JsonProperty("aut_code")
	private String autCode;

    /*
    ** Getters, Setters
    *******************/
    public Long getTkt() {
        return tkt;
    }

    public void setTkt(Long tkt) {
        this.tkt = tkt;
    }

    public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<String> getPrintMessagePIC() {
		return printMessagePIC;
	}

	public void setPrintMessagePIC(List<String> printMessagePIC) {
		this.printMessagePIC = printMessagePIC;
	}

	public List<String> getPrintMessageTIC() {
		return printMessageTIC;
	}

	public void setPrintMessageTIC(List<String> printMessageTIC) {
		this.printMessageTIC = printMessageTIC;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode){
		this.errorCode = errorCode;
	}

	public String getAutCode() {
		return autCode;
	}

	public void setAutCode(String autCode) {
		this.autCode = autCode;
	}

	/*
	** Static Method - Constructores estáticos de respuestas de error/success
	*******************************************************************************/

	// FIXME rvillabla: Estos métodos deben usarse en lugar de los constructores
	// para simplificar el API de esta clase. Los constructores deberían ser privados.
	public static BancardResponse getSuccess(GenericMessageKey messageKey, long tid) {
		BancardResponse response = new BancardResponse();
		response.setTransactionId(tid);
		response.setStatus(BancardResponse.RESPONSE_STATUS_SUCCESS);
		List<String> descriptions = new ArrayList<String>();
		descriptions.add(messageKey.getDesc());
		List<BancardMessage> messages = getMessageList(messageKey, descriptions);
		response.setMessages(messages);
		return response;
	};

	public static BancardResponse getSuccessNoTx(GenericMessageKey messageKey) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_SUCCESS);
		List<String> descriptions = new ArrayList<String>();
		descriptions.add(messageKey.getDesc());
		List<BancardMessage> messages = getMessageList(messageKey, descriptions);
		response.setMessages(messages);
		return response;
	};

	public static BancardResponse getInvoicesSuccess(List<Invoice> invoices, Long tId) {
		GenericMessageKey messageKey = null;
		if (invoices != null && !invoices.isEmpty()) {
			messageKey = GenericMessageKey.QUERY_PROCESSED;
		} else {
			messageKey = GenericMessageKey.SUBSCRIBER_WITHOUT_DEBT;
		}
		BancardResponse resp = getSuccess(messageKey, tId);
		resp.setInvoices(invoices);
		resp.setTransactionId(tId);
		resp.setTkt(tId);
		return resp;
	}

	public static BancardResponse getPaymentSuccess(GenericMessageKey message, long tId) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_SUCCESS);
		List<String> descriptions = new ArrayList<String>();
		descriptions.add(message.getDesc());
		List<BancardMessage> messages = getMessageList(message, descriptions);
		response.setMessages(messages);
		response.setTransactionId(tId);
		response.setAutCode(parseAutCode(tId));
		return response;
	}

	public static BancardResponse getPaymentSuccess(GenericMessageKey message, long tId, String autCode) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_SUCCESS);
		List<String> descriptions = new ArrayList<String>();
		descriptions.add(message.getDesc());
		List<BancardMessage> messages = getMessageList(message, descriptions);
		response.setMessages(messages);
		response.setTransactionId(tId);
		response.setAutCode(autCode);
		return response;
	}

	public static BancardResponse getPaymentError(BancardAPIException bae, long tid) {
		BancardResponse response = new BancardResponse();
		response.setTransactionId(tid);
		response.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		List<String> descriptions = new ArrayList<String>();
		BancardMessage bancardMessage = new BancardMessage(BancardMessage.LEVEL_ERROR);
		bancardMessage.setKey(GenericMessageKey.PAYMENT_NOT_AUTHORIZED.getKey());
		descriptions.add(bae.getMessage());
		bancardMessage.setDsc(descriptions);
		List<BancardMessage> bancardMessageList = new ArrayList<BancardMessage>();
		bancardMessageList.add(bancardMessage);
		response.setMessages(bancardMessageList);
		return response;
	}

	public static BancardResponse getReverseError(BancardAPIException bae, long tid) {
		BancardResponse response = new BancardResponse();
		response.setTransactionId(tid);
		response.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		List<String> descriptions = new ArrayList<String>();
		BancardMessage bancardMessage = new BancardMessage(BancardMessage.LEVEL_ERROR);
		bancardMessage.setKey(GenericMessageKey.TRANSACTION_NOT_REVERSED.getKey());
		descriptions.add(bae.getMessage());
		bancardMessage.setDsc(descriptions);
		List<BancardMessage> bancardMessageList = new ArrayList<BancardMessage>();
		bancardMessageList.add(bancardMessage);
		response.setMessages(bancardMessageList);
		return response;
	}

	public static BancardResponse getError(GenericMessageKey messageKey) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		List<String> descriptions = new ArrayList<String>();
		descriptions.add(messageKey.getDesc());
		List<BancardMessage> messages = getMessageList(messageKey, descriptions);
		response.setMessages(messages);
		return response;
	}

	public static BancardResponse getError(BancardMSException be) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		List<String> descriptions = new ArrayList<String>();
		BancardMessage bancardMessage = new BancardMessage(BancardMessage.LEVEL_ERROR);
		bancardMessage.setKey(be.getKey());
		descriptions.add(be.getMessage());
		bancardMessage.setDsc(descriptions);
		List<BancardMessage> bancardMessageList = new ArrayList<BancardMessage>();
		bancardMessageList.add(bancardMessage);
		response.setMessages(bancardMessageList);
		return response;
	}

	public static BancardResponse getError(BancardAPIException bae) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		List<String> descriptions = new ArrayList<String>();
		BancardMessage bancardMessage = new BancardMessage(BancardMessage.LEVEL_ERROR);
		bancardMessage.setKey(bae.getKey());
		descriptions.add(bae.getMessage());
		bancardMessage.setDsc(descriptions);
		List<BancardMessage> bancardMessageList = new ArrayList<BancardMessage>();
		bancardMessageList.add(bancardMessage);
		response.setMessages(bancardMessageList);
		return response;
	}

	public static BancardResponse getError(BancardAPIException bae, long tid) {
		BancardResponse response = getError(bae);
		response.setTransactionId(tid);
		return response;
	}

	public static BancardResponse getError(Throwable tw) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		GenericMessageKey messageKey = GenericMessageKey.UNKNOWN_ERROR;
		return buildErrorResponse(tw, response, messageKey);
	}
	
	public static BancardResponse getErrorTimeout(Throwable tw) {
		BancardResponse response = new BancardResponse();
		response.setStatus(BancardResponse.RESPONSE_STATUS_ERROR);
		GenericMessageKey messageKey = GenericMessageKey.NO_RESPONSE_FROM_HOST;
		return buildErrorResponse(tw, response, messageKey);
	}

	public static BancardResponse getTxError(GenericMessageKey messageKey,	long transactionId) {
		BancardResponse resp = getError(messageKey);
		resp.setTransactionId(transactionId);
		return resp;
	}

	public static BancardResponse getTxError(GenericMessageKey messageKey, String msg, long transactionId) {
		BancardResponse resp = getTxError(messageKey, transactionId);
		resp.getMessages().get(0).getDsc().add(0, msg);
		return resp;
	}

	/* Métodos privados
	 *******************/

	private static String buildCauseDescription(Throwable be) {
		if (be.getCause() != null) {
			return "Caused by: " + be.getCause().getClass().getName() + ": " + be.getCause().getMessage();
		}
		return null;
	}

	private static BancardResponse buildErrorResponse(Throwable e, BancardResponse response, GenericMessageKey messageKey) {
		List<String> descriptions = new ArrayList<String>();
		if (e.getMessage() != null) {
			descriptions.add(e.getMessage());
		} else {
			// El exception debe venir con su mensaje descriptivo,
			// esta parte es para asegurarnos de que si no viene,
			// por lo menos alguna descripcion del error le vamos
			// a retornar con la respuesta.
			descriptions.add(messageKey.getDesc());
		}
		String causeMsg = buildCauseDescription(e);
		if (causeMsg != null) {
			// Si hay una causa en el thrwable,
			// agregamos tambien eso como descripcion
			// del error.
			descriptions.add(causeMsg);
		}
		List<BancardMessage> messages = getMessageList(messageKey, descriptions);
		response.setMessages(messages);
		logger.info("buildErrorResponse: response = " + response);
		return response;
	}

	/**
	 * Parses the authorization code from the transaction id.
	 * <p>
	 * The authorization code is composed by the last {@link #AUT_CODE_LAST_DIGITS} of the transaction id.
	 *
	 * @param transactionId
	 * @return
	 */
	public static String parseAutCode(long tId) {
		String tidStr = String.valueOf(tId);
		if (tidStr.length() > AUT_CODE_LAST_DIGITS) {
			return tidStr.substring(tidStr.length() - AUT_CODE_LAST_DIGITS);
		}
		return tidStr;
	}

	protected static List<BancardMessage> getMessageList(GenericMessageKey messageKey, List<String> descriptions) {
		List<BancardMessage> messages = new ArrayList<BancardMessage>();
		BancardMessage message = new BancardMessage();
		message.setKey(messageKey.getKey());
		message.setLevel(messageKey.getLevel());
		message.setDsc(descriptions);
		messages.add(message);
		return messages;
	}

	@Override
	public String toString() {
		return "BancardResponse [invoices=" + invoices + ", printMessagePIC="
				+ printMessagePIC + ", printMessageTIC=" + printMessageTIC
				+ ", errorCode=" + errorCode + ", autCode=" + autCode
				+ ", toString()=" + super.toString() + "]";
	}
}