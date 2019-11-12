package py.com.bancard.microservice.cit.microservices;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import py.com.bancard.microservice.cit.client.CitClient;
import py.com.bancard.microservice.cit.config.CitClientConfig;
import py.com.bancard.microservice.cit.dto.Const;
import py.com.bancard.microservice.cit.dto.InvRequestDto;
import py.com.bancard.microservice.cit.dto.InvResponseDto;
import py.com.bancard.microservice.cit.dto.PaymentRequestDto;
import py.com.bancard.microservice.cit.dto.ReverseRequestDto;
import py.com.bancard.microservice.cit.dto.TxResponseDto;
import py.com.sodep.bancard.api.base.BancardMSBase;
import py.com.sodep.bancard.api.dto.TransactionDTO;
import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.enums.TransactionStatus;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMS;
import py.com.sodep.bancard.api.microservices.BancardTransactionProvider;
import py.com.sodep.bancard.api.microservices.ITransactionAware;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.api.objects.InvoiceQuery;
import py.com.sodep.bancard.api.objects.Payment;
import py.com.sodep.bancard.api.objects.Reverse;

public class CitMSImpl extends BancardMSBase implements BancardMS, ITransactionAware {
	final Logger logger = LoggerFactory.getLogger(CitMSImpl.class);

	protected static CitClientConfig config;
	private CitClient citClient;
	private BancardTransactionProvider transactionProvider;
	private String serviceName;
	private String product;
	
	
	public void setBancardTransactionProvider(String serviceName, String product, BancardTransactionProvider transactionProvider) {
		this.transactionProvider = transactionProvider;
		this.serviceName = serviceName;
		this.product = product;
		logger.debug(this.transactionProvider.toString());
		logger.debug(this.serviceName);
		logger.debug(this.product);
	}

	@Override
	public void initialize(Map<String, String> initParams) {
		config = new CitClientConfig();
		config.setUser(getRequiredParameter("user", initParams));
		config.setPassword(getRequiredParameter("password", initParams));
		config.setDriverClassName(getRequiredParameter("driver", initParams));
		config.setServiceUrl(getRequiredParameter("databaseUrl",initParams));
		config.setTimeOut(Integer.valueOf(getRequiredParameter("timeout", initParams)));
		citClient = new CitClient(config.getTimeOut(), dataSource(config));
	}

	private DataSource dataSource(final CitClientConfig config) {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(config.getServiceUrl(), config.getUser(), config.getPassword());
		driverManagerDataSource.setDriverClassName(config.getDriverClassName());
		return driverManagerDataSource;
	}

	@Override
	public BancardResponse listInvoices(InvoiceQuery invoiceQuery) throws BancardAPIException {
		try {
			BancardResponse bancardResponse = BancardResponse.getSuccess(GenericMessageKey.QUERY_PROCESSED, invoiceQuery.getTransactionId());
			InvRequestDto request = CitMapper.invoices(invoiceQuery);
			List<InvResponseDto> response = citClient.invoices(request);
			logger.info(citClient.printInvoicesResponse(response));
			bancardResponse.setInvoices(CitValidator.validateInvoices(response));
			return bancardResponse;
		} catch (BancardAPIException e){
			logger.error(e.getMessage());
			return BancardResponse.getError(e, invoiceQuery.getTransactionId());
		} catch (Exception e){
			logger.error(Arrays.toString(e.getStackTrace()));
			return BancardResponse.getTxError(GenericMessageKey.QUERY_NOT_PROCESSED, invoiceQuery.getTransactionId());
		}
	}

	@Override
	public BancardResponse doPayment(Payment payment) throws BancardAPIException {
		try {
			PaymentRequestDto request = CitMapper.payment(payment);
			request.setUsuario(config.getUser());
			request.setPassword(config.getPassword());
			logger.info("### payment. Se cargó el Request = "+request);
			
			TxResponseDto response = citClient.payment(request);
			logger.info(citClient.printTxResponse(response));
			
			TransactionDTO dto = CitMapper.paymentToTransaction(request.getIdentity(), payment, serviceName, product);
			
			if(Const.CIT_TX_SUCCESS.equals(response.getCodigoError()))
	            dto.setStatus(TransactionStatus.PAYMENT_PROCESSED);
	        else
	            dto.setStatus(TransactionStatus.PAYMENT_DECLINED);
	           
	        transactionProvider.logTransaction(dto);
			
			return CitValidator.validatePaymentResponse(response, payment.getTransactionId());
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			return BancardResponse.getError(new BancardAPIException(GenericMessageKey.PAYMENT_NOT_AUTHORIZED, e.getCause()), payment.getTransactionId());
		}
	}

	@Override
	public BancardResponse doReverse(Reverse reverse) throws BancardAPIException {
		try {
			ReverseRequestDto request = CitMapper.reverse(reverse);
			request.setUsuario(config.getUser());
			request.setPassword(config.getPassword());
			
			TransactionDTO transaccion = transactionProvider.getTransactionsByTid(reverse.getTransactionId());
			
			String type = transaccion.getType();
			if(type.contains("-")){
                request.setNroOperacion((Integer.parseInt(type.split("-")[0]))); // Primer elemento: Nro de Operacion
                request.setNroCuota(Integer.parseInt(type.split("-")[1])); // Segundo elemento: Nro de Cuota
                request.setImporte((type.split("-")[2])); // Tercer Elemento: importe
                request.setMoneda((type.split("-")[3]));  // Cuarto Elemento: moneda
                }
			
			logger.info("### reverse. Se cargó el Request = " + request);
			
			TxResponseDto responseBD = citClient.reverse(request);
			BancardResponse response = CitValidator.validateReverseResponse(responseBD, reverse.getTransactionId());
			
			if (transaccion.getStatus() == TransactionStatus.PAYMENT_PROCESSED && responseBD.getCodigoError().equals(Const.CIT_TX_SUCCESS))
	               transactionProvider.reverseTransaction(transaccion);
			
			logger.info(citClient.printTxResponse(responseBD));
			
			return response;
		} catch (BancardAPIException e) {
			logger.error(e.getMessage(), e);
			return BancardResponse.getError(e, reverse.getTransactionId());
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			return BancardResponse.getError(new BancardAPIException(GenericMessageKey.TRANSACTION_NOT_REVERSED, e.getCause()), reverse.getTransactionId());
		}
	}
}
