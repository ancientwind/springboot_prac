package com.wayne.sbt2.messaging;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.AmqpTimeoutException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author 212331901
 * @date 2019/3/19
 */
public class RabbitMQClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.ui.active.q}")
    private String uiActiveQueue;

//    @PostConstruct
    public void init() {
        new Thread( () -> {
            while (true) {
                boolean status = this.pullUIActiveStatus();
                System.out.println("+++++ get message? " + status);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    /**
     * pull and check if there's messages in queue
     * @return
     */
    public boolean pullUIActiveStatus() {
        try {
            System.out.println("++++trying to  pull message");
//            return rabbitTemplate.receiveAndConvert(uiActiveQueue, new ParameterizedTypeReference<AliveMessage>(){}) != null;
            return rabbitTemplate.receive(uiActiveQueue) != null;
        } catch (AmqpConnectException e) {
            System.out.println("+++++ connect with amqp failed");
            System.out.println(e);
        } catch (AmqpIOException e) {
            System.out.println("+++++ queue not exits");
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("+++++ other exceptions");
            System.out.println(e);
        }
        return false;
    }
}
