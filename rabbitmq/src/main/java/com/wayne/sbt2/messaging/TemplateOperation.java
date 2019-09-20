package com.wayne.sbt2.messaging;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 212331901
 * @date 9/17/2019
 */
@Component
public class TemplateOperation {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    private void init() {
        try {
            this.rabbitTemplate.convertAndSend("test", "test", "test");
        } catch (AmqpConnectException exception) {
            System.out.println("+++++++++++" + "connect failed " );
        }
    }
}
