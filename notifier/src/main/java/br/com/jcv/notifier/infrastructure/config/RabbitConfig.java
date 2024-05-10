package br.com.jcv.notifier.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RabbitConfig {

    //
    // VARIAVES CRIADAS DENTRO DO SEU ARQUIVO .properties OU .yml
    @Value("${mq.queues.notifier.add}")
    private String mqQueueNotifierAdd;

    @Value("${rabbitMQ.virtualhost}")
    private String rabbitVirtualHost;

    @Value("${rabbitMQ.host}")
    private String rabbitHost;

    @Value("${rabbitMQ.port}")
    private int rabbitPort;

    @Value("${rabbitMQ.username}")
    private String rabbitUsername;

    @Value("${rabbitMQ.password}")
    private String rabbitPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(rabbitVirtualHost);
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        log.info("connectionFactory :: has been created at VirtualHost = {}, Host = {}, Port = {}",
                rabbitVirtualHost, rabbitHost, rabbitPort);
        return connectionFactory;
    }

    @Bean(name = "queueNotifierAdd")
    public Queue queueNotifierAdd() {
        Queue queue = new Queue(mqQueueNotifierAdd,true);
        log.info("queueNotifierAdd :: Connecting to queue {}",queue.getActualName());
        return queue;
    }

}
