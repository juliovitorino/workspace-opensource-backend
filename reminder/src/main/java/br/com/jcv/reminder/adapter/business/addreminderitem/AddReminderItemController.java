package br.com.jcv.reminder.adapter.business.addreminderitem;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.reminder.adapter.business.addreminderlist.AddReminderListBusinessService;
import br.com.jcv.reminder.adapter.business.addreminderlist.AddReminderListRequest;
import br.com.jcv.reminder.adapter.business.addreminderlist.AddReminderListResponse;

@RestController
@RequestMapping("/v1/api/business/reminder-item")
public class AddReminderItemController {

    @Autowired
    private AddReminderItemBusinessService addReminderItemBusinessService;

    @PostMapping
    public ResponseEntity<Boolean> addReminderList(@RequestBody AddReminderItemRequest addReminderItemRequest) {
        return ResponseEntity.ok(addReminderItemBusinessService.execute(UUID.randomUUID(), addReminderItemRequest));
    }
}
