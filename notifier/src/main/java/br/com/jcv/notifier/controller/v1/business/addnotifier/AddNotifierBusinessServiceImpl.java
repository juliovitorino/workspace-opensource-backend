package br.com.jcv.notifier.controller.v1.business.addnotifier;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.notifier.controller.ControllerGenericResponse;
import br.com.jcv.notifier.dto.NotifierDTO;
import br.com.jcv.notifier.service.AbstractNotifierBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AddNotifierBusinessServiceImpl extends AbstractNotifierBusinessService implements AddNotifierBusinessService {

    @Override
    public ControllerGenericResponse execute(UUID processId, AddNotifierRequest addNotifierRequest) {
        log.info("execute :: processId = {} :: has started", processId);
        NotifierDTO saved = notifierService.salvar(mapper(addNotifierRequest));
        notifierService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        log.info("execute :: processId = {} :: has been finished", processId);
        return getControllerGenericResponseInstance("NTFR-1612", "Notification has been included successfully");
    }

}
