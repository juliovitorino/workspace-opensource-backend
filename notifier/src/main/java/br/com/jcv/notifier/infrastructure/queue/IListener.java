package br.com.jcv.notifier.infrastructure.queue;

import org.springframework.messaging.handler.annotation.Payload;

public interface IListener<MessageInput> {
    void onMessage(@Payload MessageInput payload);
}
