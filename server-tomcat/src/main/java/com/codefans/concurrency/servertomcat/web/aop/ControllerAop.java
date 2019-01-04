package com.codefans.concurrency.servertomcat.web.aop;

import com.codefans.concurrency.servertomcat.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 *
 */
@Component("controllerAop")
public class ControllerAop implements MethodBeforeAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAop.class);
    
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
//        LOGGER.info("AOP方法前执行：target:" + target + ",method:" + method.getName());
        HttpServletResponse response = WebContext.getInstance().getResponse();
        //允许跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
    }
}
