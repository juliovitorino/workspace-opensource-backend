package br.com.jcv.reminder.adapter.business.addreminderlist;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/business/reminder-list")
public class AddReminderListController {

    @Autowired
    private AddReminderListBusinessService addReminderListBusinessService;

    @PostMapping
    public ResponseEntity<AddReminderListResponse> addReminderList(@RequestBody AddReminderListRequest addReminderListRequest) {
        return ResponseEntity.ok(addReminderListBusinessService.execute(UUID.randomUUID(), addReminderListRequest));
    }
}
