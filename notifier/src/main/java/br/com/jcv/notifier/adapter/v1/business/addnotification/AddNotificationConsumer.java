package br.com.jcv.notifier.adapter.v1.business.addnotification;

import java.util.UUID;

import com.google.gson.Gson;

import br.com.jcv.notifier.corebusiness.addnotification.AddNotificationBusinessService;
import br.com.jcv.notifier.corebusiness.addnotification.NotificationRequest;
import br.com.jcv.notifier.infrastructure.queue.IListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddNotificationConsumer implements IListener<String> {

    @Autowired private AddNotificationBusinessService addNotificationBusinessService;

    @Autowired private Gson gson;

    @Override
    @RabbitListener(queues = "AddNotifier.INPUT")
    public void onMessage(@Payload String payload) {
        log.info("onMessage ::AddNotificationConsumer has received from rabbit -> {}",
                gson.toJson(payload));
        NotificationRequest notificationRequest = gson.fromJson(payload, NotificationRequest.class);
        addNotificationBusinessService.execute(UUID.randomUUID(), notificationRequest);
    }
}
