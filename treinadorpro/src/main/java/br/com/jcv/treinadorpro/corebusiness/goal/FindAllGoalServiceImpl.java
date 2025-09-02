package br.com.jcv.treinadorpro.corebusiness.goal;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.repository.GoalRepository;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllGoalServiceImpl implements FindAllGoalService {

    private final GoalRepository goalRepository;

    public FindAllGoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public ControllerGenericResponse<List<GoalResponse>> execute(UUID processId, Boolean input) {
        return ControllerGenericResponseHelper.getInstance(
                "MSG-0911",
                "All goals were retrieved successsfully",
                goalRepository.findAll()
                        .stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }

}
