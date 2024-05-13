package br.com.jcv.reminder.adapter.business.addreminderitem;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.reminder.corelayer.service.ReminderListService;
import br.com.jcv.reminder.corelayer.service.ReminderService;
import br.com.jcv.reminder.infrastructure.dto.ReminderDTO;
import br.com.jcv.reminder.infrastructure.dto.ReminderListDTO;

@Service
public class AddReminderItemBusinessServiceImpl implements AddReminderItemBusinessService {
    @Autowired
    private ReminderListService reminderListService;

    @Autowired
    private ReminderService reminderService;

    @Override
    public Boolean execute(UUID processId, AddReminderItemRequest addReminderItemRequest) {
        ReminderListDTO reminderList = reminderListService.findReminderListByUuidExternalListAndStatus(
                addReminderItemRequest.getUuidExternalReminderList(), GenericStatusEnums.ATIVO.getShortValue());

        ReminderDTO reminderDTO = this.toDTO(addReminderItemRequest, reminderList.getId());
        ReminderDTO saved = reminderService.salvar(reminderDTO);
        reminderService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return Boolean.TRUE;
    }

    private ReminderDTO toDTO(AddReminderItemRequest addReminderItemRequest, Long reminderListId) {
        return ReminderDTO.builder()
                .idList(reminderListId)
                .title(addReminderItemRequest.getTitle())
                .note(addReminderItemRequest.getNote())
                .tags(addReminderItemRequest.getTags())
                .fullUrlImage(addReminderItemRequest.getFullUrlImage())
                .url(addReminderItemRequest.getUrl())
                .priority(addReminderItemRequest.getPriority())
                .flag(addReminderItemRequest.getFlag())
                .dueDate(addReminderItemRequest.getDueDate())
                .build();
    }
}
