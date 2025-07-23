package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.usecases.CreateNewContractService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllActiveContractsService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllContractTodayWorkoutService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllOverduePaymentService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllReceivedPaymentsCurrentMonthService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllStudentsFromTrainerService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindContractService;
import br.com.jcv.treinadorpro.corelayer.request.CreateNewStudentContractRequest;
import br.com.jcv.treinadorpro.corelayer.request.UserDataSheetPlanRequest;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.CreateNewStudentContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentPaymentResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/contract")
public class ContractController {

    private final CreateNewContractService createNewContractService;
    private final FindAllStudentsFromTrainerService findAllStudentsFromTrainerService;
    private final FindAllActiveContractsService findAllActiveContractsService;
    private final FindAllContractTodayWorkoutService findAllContractTodayWorkoutService;
    private final FindAllOverduePaymentService findAllOverduePaymentService;
    private final FindAllReceivedPaymentsCurrentMonthService findAllReceivedPaymentsCurrentMonthService;
    private final FindContractService findContractService;

    public ContractController(CreateNewContractService createNewContractService,
                              FindAllStudentsFromTrainerService findAllStudentsFromTrainerService,
                              FindAllActiveContractsService findAllActiveContractsService,
                              FindAllContractTodayWorkoutService findAllContractTodayWorkoutService,
                              FindAllOverduePaymentService findAllOverduePaymentService,
                              FindAllReceivedPaymentsCurrentMonthService findAllReceivedPaymentsCurrentMonthService,
                              FindContractService findContractService) {
        this.createNewContractService = createNewContractService;
        this.findAllStudentsFromTrainerService = findAllStudentsFromTrainerService;
        this.findAllActiveContractsService = findAllActiveContractsService;
        this.findAllContractTodayWorkoutService = findAllContractTodayWorkoutService;
        this.findAllOverduePaymentService = findAllOverduePaymentService;
        this.findAllReceivedPaymentsCurrentMonthService = findAllReceivedPaymentsCurrentMonthService;
        this.findContractService = findContractService;
    }

    @PostMapping
    public ResponseEntity<ControllerGenericResponse<CreateNewStudentContractResponse>> createNewStudentContract(
            @RequestBody CreateNewStudentContractRequest request)
    {
        return ResponseEntity.ok(createNewContractService.execute(UUID.randomUUID(), request));
    }


    @GetMapping("{externalId}")
    public ResponseEntity<ControllerGenericResponse<List<StudentsFromTrainerResponse>>> findAllStudentsFromTrainerService(
            @PathVariable("externalId")UUID externalId){
        return ResponseEntity.ok(findAllStudentsFromTrainerService.execute(UUID.randomUUID(), externalId));
    }

    @GetMapping("/trainer/active")
    public ResponseEntity<ControllerGenericResponse<List<ContractResponse>>> findAllActiveContracts(){
        return ResponseEntity.ok(findAllActiveContractsService.execute(UUID.randomUUID()));
    }

    @GetMapping("/trainer/today")
    public ResponseEntity<ControllerGenericResponse<List<ContractResponse>>> findAllContractTodayWorkoutService() {
        return ResponseEntity.ok(findAllContractTodayWorkoutService.execute(UUID.randomUUID()));
    }

    @GetMapping("/trainer/student/overdue-payments")
    public ResponseEntity<ControllerGenericResponse<List<StudentPaymentResponse>>> findAllOverduePayment() {
        return ResponseEntity.ok(findAllOverduePaymentService.execute(UUID.randomUUID()));
    }

    @GetMapping("/trainer/student/received-payments")
    public ResponseEntity<ControllerGenericResponse<List<StudentPaymentResponse>>> findAllReceivedPaymentsCurrentMonthService() {
        return ResponseEntity.ok(findAllReceivedPaymentsCurrentMonthService.execute(UUID.randomUUID()));
    }

    @GetMapping("/number/{externalId}")
    public ResponseEntity<ControllerGenericResponse<ContractResponse>> findContract(@PathVariable("externalId") UUID externalId) {
        return ResponseEntity.ok(findContractService.execute(UUID.randomUUID(), externalId));
    }

    @PostMapping("student/data-sheet-plan/save")
    public ResponseEntity<ControllerGenericResponse<Boolean>> saveUserDataSheetPlan(@RequestBody UserDataSheetPlanRequest request){
        return ResponseEntity.ok(ControllerGenericResponseHelper.getInstance(
                "MSG-1528",
                "Request Accepted",
                Boolean.TRUE
        ));
    }

}
