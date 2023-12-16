package br.com.jcv.security.guardian.mq.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("notifierProducer")
public class NotifierProducer extends AbstractProducer implements IProducer<AddNotificationMessage,Boolean>{

    @Autowired private @Qualifier("exchangeMsDirectNotifier") DirectExchange directExchange;
    @Override
    public Boolean dispatch(AddNotificationMessage message) {
        rabbitTemplate.convertAndSend(directExchange.getName(),"notifierGenericRK",message );
        return true;
    }
}
