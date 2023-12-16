package br.com.jcv.security.guardian.mq.producer;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractProducer {
    @Autowired protected RabbitTemplate rabbitTemplate;
    @Autowired protected Gson gson;
}