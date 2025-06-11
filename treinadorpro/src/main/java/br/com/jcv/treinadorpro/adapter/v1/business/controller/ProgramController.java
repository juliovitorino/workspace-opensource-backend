package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.program.FindAllProgramService;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/program")
public class ProgramController {

    private final FindAllProgramService findAllProgramService;

    public ProgramController(FindAllProgramService findAllProgramService) {
        this.findAllProgramService = findAllProgramService;
    }

    @GetMapping
    public ResponseEntity<ControllerGenericResponse<List<ProgramResponse>>> findAllProgram() {
        return ResponseEntity.ok(findAllProgramService.execute(UUID.randomUUID(), Boolean.TRUE));
    }
}
