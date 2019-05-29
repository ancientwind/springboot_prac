package com.wayne.sbt2.domain;

import com.wayne.sbt2.controller.MybatisController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 212331901
 * @date 2019/2/13
 */
@Component
public class Hello {

    private Logger logger = LoggerFactory.getLogger(MybatisController.class);

    public String sayHello(String name) {
        logger.info("hello " + name);
        return name;
    }
}
