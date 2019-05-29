package com.wayne.sbt2.domain;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 212331901
 * @date 2019/2/13
 */
@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    public LogAspect() {
        logger.info(this.getClass().getName() + "created");
    }

    @Before("com.wayne.sbt2.domain.LogAspectConfig.helloLog()")
    public void before(JoinPoint joinPoint) {
        logger.info("before sayHello..." + joinPoint);
    }

    @AfterReturning(value = "com.wayne.sbt2.domain.LogAspectConfig.helloLog()", returning = "retValue" )
    public void afterReturning(JoinPoint joinPoint, String retValue) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        logger.info(" cut in method: " + method.getName());
        logger.info("Return: " + retValue);
    }
}
