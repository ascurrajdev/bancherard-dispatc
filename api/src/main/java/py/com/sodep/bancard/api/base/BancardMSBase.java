package py.com.sodep.bancard.api.base;

import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.sodep.bancard.api.enums.GenericMessageKey;
import py.com.sodep.bancard.api.microservices.BancardAPIException;

public abstract class BancardMSBase {

    private final Logger logger = LoggerFactory.getLogger(BancardMSBase.class);

    protected BancardAPIException throwException(RuntimeException e) {
        Throwable cause = e;
        if (cause instanceof NumberFormatException || cause instanceof InvalidParameterException) {
            logger.error("Parameters in request are invalid");
            return new BancardAPIException(GenericMessageKey.INVALID_PARAMETERS, e);
        }

        if ((cause = cause.getCause()) != null && cause instanceof ConnectException) {
            logger.error("WebService is down or inaccessible");
            return new BancardAPIException(GenericMessageKey.CLIENT_CONNECTION_ERROR, e);
        }
        return new BancardAPIException(GenericMessageKey.INVALID_PARAMETERS, e);
    }

    protected String getRequiredParameter(String paramName, Map<String, String> initParams) {
        String value = initParams.get(paramName);
        if (value == null) {
            throw new IllegalStateException("El parametro " + paramName + " es requerido para el plugin " + this.getClass().getName());
        }
        return value;
    }

    protected Boolean getBooleanParameter(String paramName, Map<String, String> initParams, Boolean defaultValue) {
        String value = initParams.get(paramName);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
}