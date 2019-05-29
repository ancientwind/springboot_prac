package com.wayne.sbt2.domain;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author 212331901
 * @date 2019/2/13
 */

public class LogAspectConfig {

    private Logger logger = LoggerFactory.getLogger(LogAspectConfig.class);

    public LogAspectConfig() {
        logger.info(this.getClass().getName() + " created.");
    }

    @Pointcut("execution(* com.wayne.sbt2.domain.Hello.sayHello(..))")
    public void helloLog() {
    }
}
