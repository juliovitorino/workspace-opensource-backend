package br.com.jcv.notifier.controller.v1.business.addnotifier;

import br.com.jcv.notifier.controller.ControllerGenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/notifier/add")
public class AddNotifierController {

    @Autowired private AddNotifierBusinessService service;
    @PostMapping
    public ResponseEntity<ControllerGenericResponse> addNotification(@RequestBody AddNotifierRequest request) {
        return ResponseEntity.ok(service.execute(UUID.randomUUID(), request));
    }

}
