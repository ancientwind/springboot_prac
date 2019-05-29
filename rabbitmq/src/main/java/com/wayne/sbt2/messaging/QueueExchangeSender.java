package com.wayne.sbt2.messaging;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author 212331901
 * @date 2019/5/22
 */
public class QueueExchangeSender {

    public static final String Q_HELLO = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.189.134.47");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Q_HELLO, false, false, false, null);
            String message = "hello world";
            channel.basicPublish("", Q_HELLO, null, message.getBytes("UTF-8"));

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("connection is not available now");
            e.printStackTrace();
        }

    }
}
