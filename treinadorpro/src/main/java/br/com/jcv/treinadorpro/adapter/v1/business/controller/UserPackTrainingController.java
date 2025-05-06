package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.CreateUserPackTrainingService;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.CreateWorkoutCalendarFromProgramTemplateService;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.DeleteWorkoutCalendarService;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.EditUserPackTrainingItemService;
import br.com.jcv.treinadorpro.corebusiness.userpacktraining.FindAllMyStudentsService;
import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.request.CreateWorkoutCalendarServiceRequest;
import br.com.jcv.treinadorpro.corelayer.request.EditUserWorkoutCalendarItemRequest;
import br.com.jcv.treinadorpro.corelayer.request.UserPackTrainingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/userpacktraining")
public class UserPackTrainingController {

    private final CreateUserPackTrainingService createUserPackTrainingService;
    private final FindAllMyStudentsService findAllMyStudentsService;
    private final CreateWorkoutCalendarFromProgramTemplateService createWorkoutCalendarFromProgramTemplateService;
    private final DeleteWorkoutCalendarService deleteWorkoutCalendarService;
    private final EditUserPackTrainingItemService editUserPackTrainingItemService;

    public UserPackTrainingController(CreateUserPackTrainingService createUserPackTrainingService,
                                      FindAllMyStudentsService findAllMyStudentsService,
                                      CreateWorkoutCalendarFromProgramTemplateService createWorkoutCalendarFromProgramTemplateService,
                                      DeleteWorkoutCalendarService deleteWorkoutCalendarService,
                                      EditUserPackTrainingItemService editUserPackTrainingItemService) {
        this.createUserPackTrainingService = createUserPackTrainingService;
        this.findAllMyStudentsService = findAllMyStudentsService;
        this.createWorkoutCalendarFromProgramTemplateService = createWorkoutCalendarFromProgramTemplateService;
        this.deleteWorkoutCalendarService = deleteWorkoutCalendarService;
        this.editUserPackTrainingItemService = editUserPackTrainingItemService;
    }

    @PostMapping("/add")
    public ResponseEntity<ControllerGenericResponse<Boolean>> createUserPackTrainig(@RequestBody UserPackTrainingRequest request) {
        return ResponseEntity.ok(createUserPackTrainingService.execute(UUID.randomUUID(), request));
    }

    @PostMapping("/workout-calendar/add")
    public ResponseEntity<ControllerGenericResponse<Boolean>> createWorkoutCalendar(@RequestBody CreateWorkoutCalendarServiceRequest request){
        return ResponseEntity.ok(createWorkoutCalendarFromProgramTemplateService.execute(UUID.randomUUID(), request));
    }

    @DeleteMapping("/workout-calendar")
    public ResponseEntity<ControllerGenericResponse<Boolean>> deleteWorkoutCalendar(@RequestBody List<UUID> request){
        return ResponseEntity.ok(deleteWorkoutCalendarService.execute(UUID.randomUUID(), request));
    }

    @PutMapping("/workout-calendar/{externalId}")
    public ResponseEntity<ControllerGenericResponse<Boolean>> EditUserPackTrainingItemService(@PathVariable("externalId") UUID externalId,
                                                                                              @RequestBody EditUserWorkoutCalendarItemRequest request) {
        request.setExternalId(externalId);
        return ResponseEntity.ok(editUserPackTrainingItemService.execute(UUID.randomUUID(), request));
    }

    @GetMapping("/list/{personalUserId}")
    public ResponseEntity<ControllerGenericResponse<List<UserPackTrainingDTO>>> findAllMyStudents(@PathVariable("personalUserId") Long personalUserId) {
        return ResponseEntity.ok(findAllMyStudentsService.execute(UUID.randomUUID(),personalUserId));
    }

}
