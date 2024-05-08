package br.com.jcv.notifier.infrastructure.config;

import org.springframework.amqp.core.Queue;
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

    @Bean(name = "queueNotifierAdd")
    public Queue queueNotifierAdd() {
        Queue queue = new Queue(mqQueueNotifierAdd,true);
        log.info("queueNotifierAdd :: Connecting to queue {}",queue.getActualName());
        return queue;
    }

}
