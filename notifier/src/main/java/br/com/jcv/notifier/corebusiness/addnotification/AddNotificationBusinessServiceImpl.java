package br.com.jcv.notifier.corebusiness.addnotification;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.notifier.corelayer.service.NotifierService;
import br.com.jcv.notifier.infrastructure.dto.NotifierDTO;

@Service
public class AddNotificationBusinessServiceImpl implements AddNotificationBusinessService{

    @Autowired private NotifierService notifierService;

    @Override
    public Boolean execute(UUID processId, NotificationRequest notificationRequest) {
        NotifierDTO saved = notifierService.salvar(notifierService.toDTO(notificationRequest));
        notifierService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return Boolean.TRUE;
    }
}
