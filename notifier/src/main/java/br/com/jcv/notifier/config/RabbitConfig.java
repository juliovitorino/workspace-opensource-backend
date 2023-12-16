package br.com.jcv.notifier.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${mq.queues.notifier}")
    private String mqQueueNotifier;

    @Bean(name = "queueNotifier")
    public Queue queueNotifier() {
        log.info("Bean has been connected to queue {}", mqQueueNotifier);
        return new Queue(mqQueueNotifier,true);
    }


}