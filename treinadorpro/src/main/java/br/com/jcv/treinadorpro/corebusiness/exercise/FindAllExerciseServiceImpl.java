package br.com.jcv.treinadorpro.corebusiness.exercise;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.repository.ExerciseRepository;
import br.com.jcv.treinadorpro.corelayer.response.ExerciseResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllExerciseServiceImpl implements FindAllExerciseService{

    private final ExerciseRepository exerciseRepository;

    public FindAllExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ControllerGenericResponse<List<ExerciseResponse>> execute(UUID processId, Boolean aBoolean) {
        return ControllerGenericResponseHelper.getInstance(
                "MSG-0923",
                "All exercises were retrieved successfully",
                exerciseRepository.findAll()
                        .stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
