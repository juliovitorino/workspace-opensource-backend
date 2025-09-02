package br.com.jcv.treinadorpro.corebusiness.program;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.repository.ProgramRepository;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllProgramServiceImpl implements FindAllProgramService {

    private final ProgramRepository programRepository;

    public FindAllProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public ControllerGenericResponse<List<ProgramResponse>> execute(UUID processId, Boolean input) {
        return ControllerGenericResponseHelper.getInstance(
                "MSG-0914",
                "All programs were retrieved successfully",
                programRepository.findAll()
                        .stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }

}
