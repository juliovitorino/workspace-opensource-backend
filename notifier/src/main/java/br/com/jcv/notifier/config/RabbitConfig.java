package br.com.jcv.notifier.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${mq.queues.notifier}")
    private String mqQueueNotifier;

    @Bean("connectionFactoryMQ")
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory();
    }

    @Bean("messageConverter")
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean("rabbitListenerContainerFactory")
    @DependsOn(value = "connectionFactoryMQ")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(12);
        factory.setConsecutiveActiveTrigger(1);
        factory.setMessageConverter(messageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
    @Bean(name = "queueNotifier")
    public Queue queueNotifier() {
        log.info("Bean has been connected to queue {}", mqQueueNotifier);
        return new Queue(mqQueueNotifier,true);
    }


}