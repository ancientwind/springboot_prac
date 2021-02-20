package com.wayne.sbt2.controller;

import com.wayne.sbt2.domain.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 212331901
 * @date 2019/2/13
 */
@ConditionalOnExpression("'${product}' == 'revsggso'")
@RestController
@RequestMapping("mybatis2")
public class MybatisController2 {

    private Logger logger = LoggerFactory.getLogger(MybatisController2.class);

    @Autowired
    private Hello hello;

    public MybatisController2() {
        logger.info(this.getClass().getName() + " is created.");
    }

    @GetMapping("hello")
    public String hello(String name) {
        return hello.sayHello(name);
    }

}
