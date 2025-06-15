package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.usecases.CreateNewContractService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllStudentsFromTrainerService;
import br.com.jcv.treinadorpro.corelayer.request.CreateNewStudentContractRequest;
import br.com.jcv.treinadorpro.corelayer.response.CreateNewStudentContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;
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


    public ContractController(CreateNewContractService createNewContractService,
                              FindAllStudentsFromTrainerService findAllStudentsFromTrainerService) {
        this.createNewContractService = createNewContractService;
        this.findAllStudentsFromTrainerService = findAllStudentsFromTrainerService;
    }

    @PostMapping
    public ResponseEntity<ControllerGenericResponse<CreateNewStudentContractResponse>> createNewStudentContract(
            @RequestBody CreateNewStudentContractRequest request)
    {
        return ResponseEntity.ok(createNewContractService.execute(UUID.randomUUID(), request));
    }


    @GetMapping("{externalId}")
    public ResponseEntity<ControllerGenericResponse<List<StudentsFromTrainerResponse>>> findAllStudentsFromTrainerService(@PathVariable("externalId")UUID externalId){
        return ResponseEntity.ok(findAllStudentsFromTrainerService.execute(UUID.randomUUID(), externalId));
    }

}
