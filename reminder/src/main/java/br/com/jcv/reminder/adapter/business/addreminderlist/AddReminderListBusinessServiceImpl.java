package br.com.jcv.reminder.adapter.business.addreminderlist;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.reminder.corelayer.service.ReminderListService;
import br.com.jcv.reminder.infrastructure.dto.ReminderListDTO;

@Service
public class AddReminderListBusinessServiceImpl implements AddReminderListBusinessService{
    @Autowired
    private ReminderListService reminderListService;

    @Override
    public AddReminderListResponse execute(UUID processId, AddReminderListRequest addReminderListRequest) {
        ReminderListDTO saved = reminderListService.salvar(toDTO(addReminderListRequest));
        reminderListService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return getResponse(saved);
    }

    public AddReminderListResponse getResponse(ReminderListDTO reminderListDTO) {
        return AddReminderListResponse.builder()
                .uuidExternalList(reminderListDTO.getUuidExternalList())
                .mensagemResponse(
                        MensagemResponse.builder()
                                .msgcode("MSG-0001")
                                .mensagem("The reminder list has been created successfully")
                                .build()
                )
                .build();
    }

    private ReminderListDTO toDTO(AddReminderListRequest addReminderListRequest) {
        return ReminderListDTO.builder()
                .title(addReminderListRequest.getTitle())
                .uuidExternalApp(addReminderListRequest.getUuidExternalApp())
                .uuidExternalUser(addReminderListRequest.getUuidExternalUser())
                .uuidExternalList(UUID.randomUUID())
                .build();
    }
}
