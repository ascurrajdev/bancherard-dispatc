package py.com.sodep.bancard.services.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;
import py.com.sodep.bancard.api.microservices.BancardMSException;
import py.com.sodep.bancard.api.objects.BancardResponse;
import py.com.sodep.bancard.rest.InvoiceRequest;
import py.com.sodep.bancard.rest.PaymentRequest;
import py.com.sodep.bancard.rest.ReverseRequest;

@Component
public class MicroServiceExecutor {
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceExecutor.class);
	private final ExecutorService executor;
	private static final int DEFAULT_POOL_SIZE = 100;
	
	private final String DO_PAYMENT = "doPayment";
	private final String DO_REVERSE = "doReverse";

	@Autowired
	public MicroServiceExecutor(@Value("${ms.executor.pool.size}") Integer poolSize) {
		this.executor = Executors.newFixedThreadPool(poolSize);
	}

	public MicroServiceExecutor() {
		this.executor = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
	}

	// Con esta anotación, spring le deberia llamar a este método
	// cuando se cierra el contexto.
	@PreDestroy
	public void shutdown() {
		logger.info("Shutting down MICROSERVICE EXECUTOR");
		executor.shutdown();
	}

	
	public BancardResponse listInvoices(final InvoiceRequest request, final MicroService microService) throws BancardMSException, BancardAPIException {
		logger.debug("Preparando ejecución de " + microService.getName() + "#listInvoices");
		// Arma la tarea para invocar al listInvoices dentro de un executor
		Callable<BancardResponse> task = new Callable<BancardResponse>() {
			@Override
			public BancardResponse call() throws Exception {
				logger.debug("Ejecutando " + microService.getName() + "#listInvoices");
				return microService.getClientMS().listInvoices(request.toInvoiceQuery());
			}
		};

		MicroServiceStatus status = microService.toDTO();

		Future<BancardResponse> future = executor.submit(task);
		try {
			// Se llama a la tarea pero se espera como máximo que termine estos
			// millisengundos
			logger.debug("Ejecuntado el proceso future.get()");
			BancardResponse data = future.get(status.getTimeoutInMilliseconds(), TimeUnit.MILLISECONDS);
			logger.debug("Finalizado " + microService.getName() + "#listInvoices");
			logger.debug("data " + data);
			return data;
		} catch (InterruptedException e) {
			logger.error("Interrumpido " + microService.getName() + "#listInvoices");
			// conserva el estado de interrupción
			Thread.currentThread().interrupt();
			// Esto solo debería de pasar si se está apagando el server
			throw new BancardMSException("Tarea cancelada en Dispatcher");
		} catch (ExecutionException e) {
			logger.error("MicroService Error" + microService.getName() + "#listInvoices");
			processMSException(e, microService);			
			throw new BancardMSException(GenericMessageKey.QUERY_NOT_ALLOWED.getDesc(), e, GenericMessageKey.QUERY_NOT_ALLOWED.getKey());
		} catch (TimeoutException e) {
			logger.error("Timeout " + microService.getName() + "#listInvoices. MaxTimeout = " + status.getTimeoutInMilliseconds() + " ms.");
			// no proceso a tiempo la tarea. El Microservicio está tardando mas
			// de lo debido
			microService.incrementNumberOfTimeout();
			logger.debug("Realizo el incremento a 1 con microService.incrementNumberOfTimeout()");
			// FIXME enviar un cancel a la tarea para que intente salir por si
			// esta bloqueada. Total su respuesta ya no importa
			throw new BancardMSException(GenericMessageKey.QUERY_NOT_PROCESSED.getDesc(), e, GenericMessageKey.QUERY_NOT_PROCESSED.getKey());
		} finally {
			future.cancel(true);
		}

	}

	public BancardResponse doPayment(final PaymentRequest request, final MicroService microService) throws BancardAPIException {
		logger.debug("Preparando ejecución de " + microService.getName() + "#"+DO_PAYMENT);
		Callable<BancardResponse> task = new Callable<BancardResponse>() {
			@Override
			public BancardResponse call() throws Exception {
				logger.debug("Ejecutando " + microService.getName() + "#"+DO_PAYMENT);
				BancardResponse response;
				response = microService.getClientMS().doPayment(request.toPayment());
				logger.debug("response " + response);
				return response;
			}
		};
		return executeBancardResponseTask(task, microService, DO_PAYMENT, request.getTransactionId());
	}

	/**
	 * 
	 * @param subscriberIds
	 * @param reverse
	 * @throws BancardAPIException
	 */
	public BancardResponse doReverse(final ReverseRequest request, final MicroService microService) throws BancardAPIException {
		logger.debug("Preparando ejecución de " + microService.getName() + "#"+DO_REVERSE);
		Callable<BancardResponse> task = new Callable<BancardResponse>() {
			@Override
			public BancardResponse call() throws Exception {
				logger.debug("Ejecutando " + microService.getName() + "#"+DO_REVERSE);
				return microService.getClientMS().doReverse(request.toReverse());
			}
		};
		return executeBancardResponseTask(task, microService, DO_REVERSE, request.getTransactionId());
	}

	/**
	 * Ejecuta una tarea que no retorna nada. Con esto se puede tener algo de
	 * reutilizacion para todas los tareas que llaman al driver y no retornan
	 * nada
	 */
	private BancardResponse executeBancardResponseTask(Callable<BancardResponse> task, final MicroService microService, String methodName, long tid) throws BancardAPIException {
		logger.debug("en executeBancardResponseTask for: " + methodName);
		Future<BancardResponse> future = executor.submit(task);
		MicroServiceStatus status = microService.toDTO();
		try {
			BancardResponse response = future.get(status.getTimeoutInMilliseconds(), TimeUnit.MILLISECONDS);
			logger.debug("Finalizado " + microService.getName() + "#"+methodName);
			logger.debug("En Dispatcher: response " + response);
			return response;
		} catch (InterruptedException e) {
			logger.error("Interrumpido " + microService.getName() + "#"+methodName);
			// conserva el estado de interrupción
			Thread.currentThread().interrupt();
			// Esto solo debería de pasar si se está apagando el server
			throw new BancardMSException("Tarea cancelada en Dispatcher");
		} catch (ExecutionException e) {
			logger.error("Error de MicroServicio " + microService.getName() + "#"+methodName);
			processMSException(e, microService);
		} catch (TimeoutException e) {
			logger.error("Timeout " + microService.getName() + "#"+methodName+". MaxTimeout = " + status.getTimeoutInMilliseconds() + " ms.");
			BancardMSException msEx;
			if (DO_PAYMENT.equals(methodName)) {
				microService.incrementNumberOfTimeout();
				msEx = new BancardMSException(GenericMessageKey.PAYMENT_NOT_AUTHORIZED.getDesc(), e, GenericMessageKey.PAYMENT_NOT_AUTHORIZED.getKey());
			}else
				msEx = new BancardMSException(GenericMessageKey.TRANSACTION_NOT_REVERSED.getDesc(), e, GenericMessageKey.TRANSACTION_NOT_REVERSED.getKey());
			logger.debug("Incremento en 1 con microService.incrementNumberOfTimeout()");
			
			if (microService != null) {
				msEx.setFailedMicroService(microService.getName());
			}
			throw msEx;
		} finally {
			future.cancel(true);
		}
		return null;
	}

	/**
	 * Examina la excepcion ocurrida dentro de un microservicio y lanza un
	 * BancardAPIException o un BancardMSException. En caso de un error
	 * inesperado dentro del servicio lanza un BancardMSException
	 * 
	 * @param e
	 * @param microService 
	 * @throws BancardAPIException
	 * @throws BancardMSException
	 */
	private BancardAPIException processMSException(ExecutionException e, MicroService microService) throws BancardAPIException {
		Throwable cause = e.getCause();
		// TODO
		// Ver de que tipo es
		// BancardAPIException, BancardMSException, o un Throwable
		if (cause instanceof BancardAPIException) {
			// Si es uno de estos errores quiere decir que es un error de
			// parametros en el API esperado por el servicio o
			// es un error dentro del microservicio que ya fue detectado
			throw (BancardAPIException) cause;
		} else if (cause instanceof BancardMSException) {
			if (microService != null) {
				microService.incrementNumberOfFail();
			}
			throw (BancardMSException) cause;
		} else {
			if (microService != null) {
				microService.incrementNumberOfFail();
			}
			
			// Quiere decir que ocurrio un error inesperado en el servicio y no
			// fue tratado adecuadamente. Hay que reportarlo como tal
			throw new BancardMSException(GenericMessageKey.MICROSERVICE_INTERNAL_ERROR.getDesc(), cause, GenericMessageKey.MICROSERVICE_INTERNAL_ERROR.getKey());
		}
	}

//	private void setExtraErrorInfo(MicroService service, BancardMSException be) {
//		if (service != null) {
//			if (be != null) {
//				be.setFailedMicroService(service.getName());
//			}
//		}
//	}	
}
