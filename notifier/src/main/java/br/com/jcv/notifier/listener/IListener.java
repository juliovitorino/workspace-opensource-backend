package br.com.jcv.notifier.listener;

import org.springframework.messaging.handler.annotation.Payload;

public interface IListener<MessageInput> {
    void onMessage(@Payload MessageInput payload);
}