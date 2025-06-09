package br.com.jcv.treinadorpro.corebusiness.program;

import br.com.jcv.treinadorpro.corelayer.repository.ProgramRepository;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
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
    public List<ProgramResponse> execute(UUID processId, Boolean input) {
        return programRepository.findAll()
                .stream()
                .map(MapperServiceHelper::toResponse)
                .collect(Collectors.toList());
    }

}
