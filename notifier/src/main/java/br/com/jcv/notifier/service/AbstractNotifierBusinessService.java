package br.com.jcv.notifier.service;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.notifier.controller.ControllerGenericResponse;
import br.com.jcv.notifier.controller.v1.business.addnotifier.AddNotifierRequest;
import br.com.jcv.notifier.dto.NotifierDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbstractNotifierBusinessService {
    @Autowired protected Gson gson;
    @Autowired protected NotifierService notifierService;


    protected ControllerGenericResponse getControllerGenericResponseInstance(String msgcode, String msg) {
        return ControllerGenericResponse.builder()
                .response(MensagemResponse.builder()
                        .msgcode(msgcode)
                        .mensagem(msg)
                        .build())
                .build();
    }

    protected NotifierDTO mapper(AddNotifierRequest addNotifierRequest) {
        NotifierDTO response = NotifierDTO.builder()
                .type(addNotifierRequest.getType())
                .applicationUUID(addNotifierRequest.getApplicationUUID())
                .userUUID(addNotifierRequest.getUserUUID())
                .description(addNotifierRequest.getDescription())
                .title(addNotifierRequest.getTitle())
                .isReaded("N")
                .build();
        return response;
    }

}

