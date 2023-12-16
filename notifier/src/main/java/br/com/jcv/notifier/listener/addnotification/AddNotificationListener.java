package br.com.jcv.notifier.listener.addnotification;

import br.com.jcv.notifier.controller.v1.business.addnotifier.AddNotifierBusinessService;
import br.com.jcv.notifier.listener.IListener;
import br.com.jcv.notifier.service.AbstractNotifierBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddNotificationListener extends AbstractNotifierBusinessService implements IListener<String> {

    @Autowired private AddNotifierBusinessService service;
    @Override
    @RabbitListener(queues = "${mq.queues.notifier}",
            ackMode = "AUTO",
            concurrency = "4",
            containerFactory = "rabbitListenerContainerFactory")
    public void onMessage(@Payload String payload) {
        log.info("onMessage :: received message from rabbit -> {}", payload);
        AddNotificationMessage message = gson.fromJson(payload, AddNotificationMessage.class);
        service.execute(message.getProcessId(), message.getAddNotificationRequest());
    }
}