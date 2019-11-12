package py.com.sodep.bancard.interceptors;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import py.com.sodep.bancard.common.RequestPrinter;


public class LoggerInterceptor { //extends HandlerInterceptorAdapter {
    static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
    
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//        HttpServletResponse response, Object handler) throws Exception {
//    	logger.info("Before handling the request");
//        
//    	logger.info("Request debug:\n");
//    	logger.info(RequestPrinter.debugString(request));
//        return super.preHandle(request, response, handler);
//    }

    
    
}