package com.wayne.sbt2.messaging;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;


/**
 * Created by 305020173 on 2017/9/28.
 */

@Configuration
@EnableRabbit
public class RabbitmqConfig implements RabbitListenerConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Value("${logging.private.tag}")
    private String logTag;

    @Value("${rabbitmq.ai.tablepositon.fx}")
    private String tablePositionFx;

    @Value("${rabbitmq.ai.landmark.fx}")
    private String predictedLandmarkFx;

    @Value("${rabbitmq.firmware.tx}")
    private String firmwareTX;

    @Value("${rabbitmq.dpp.tx}")
    private String dppExchange;


    private static final long QUEUE_EXPIRATION = 2000L;
    @Value("${rabbitmq.ui.active.q}")
    private String uiActiveQueue;

    @Bean
    public Queue uiActiveQueue() {
//        return new Queue(uiActiveQueue, true);
        return QueueBuilder.durable(uiActiveQueue)
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .withArgument("x-max-length", 1)
                .build();
    }


    @Bean
    public FanoutExchange tablePositionExchange() {
        LOGGER.info(logTag + "create exchange: " + tablePositionFx);
        return new FanoutExchange(tablePositionFx, true, false);
    }

    @Bean
    public FanoutExchange predictedLandmarkFx() {
        LOGGER.info(logTag + "create exchange: " + predictedLandmarkFx);
        return new FanoutExchange(predictedLandmarkFx, true, false);
    }

    @Bean
    public TopicExchange dppExchange() {
        LOGGER.info(logTag + "create exchange: " + dppExchange);
        return new TopicExchange(dppExchange, true, false);
    }

    @Bean
    public Queue anonyPredictedLandmarkQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindingPredictedLandmarkFx(FanoutExchange predictedLandmarkFx, Queue anonyPredictedLandmarkQueue) {
        return BindingBuilder.bind(anonyPredictedLandmarkQueue).to(predictedLandmarkFx);
    }

    @Bean
    public TopicExchange firmwareTx() {
        return new TopicExchange(firmwareTX, true, false);
    }

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

//    @Bean
//    public DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory() {
//        DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
//        defaultMessageHandlerMethodFactory.setMessageConverter(mappingJackson2MessageConverter());
//        return defaultMessageHandlerMethodFactory;
//    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
//        registrar.setMessageHandlerMethodFactory(defaultMessageHandlerMethodFactory());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setMaxConcurrentConsumers(5);
        factory.setAdviceChain(new Advice[]{retryOperationsInterceptor()});
        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retryOperationsInterceptor() {
        return RetryInterceptorBuilder
                .stateless()
                .maxAttempts(5)
                .backOffOptions(1000, 3.0, 10000)
//                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }


    @Bean
    public RabbitMQClient getMQClient() {
        return new RabbitMQClient();
    }

    @Bean
    public LandmarkMQClient predictedLandmarkMQClient() {
        return new LandmarkMQClient();
    }
}
