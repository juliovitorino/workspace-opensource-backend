package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.DistinctUserPlanProjection;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutPlan;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutPlanRepository;
import br.com.jcv.treinadorpro.corelayer.request.UserDataSheetPlanRequest;
import br.com.jcv.treinadorpro.corelayer.request.UserWorkoutPlanRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FindUserWorkoutDataSheetPlanServiceImpl implements FindUserWorkoutDataSheetPlanService{

    private final UserWorkoutPlanRepository userWorkoutPlanRepository;
    private final GetLoggedUserService getLoggedUserService;

    public FindUserWorkoutDataSheetPlanServiceImpl(UserWorkoutPlanRepository userWorkoutPlanRepository,
                                                   GetLoggedUserService getLoggedUserService) {
        this.userWorkoutPlanRepository = userWorkoutPlanRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<UserDataSheetPlanRequest> execute(UUID processId, UUID contractExternalId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        List<DistinctUserPlanProjection> distinctCombinations = userWorkoutPlanRepository.findDistinctCombinations(contractExternalId, trainer.getId());

        List<UserWorkoutPlan> allUserDataSheetPlan = userWorkoutPlanRepository.findAllUserDataSheetPlanByContractExternalIdAndTrainerId(
                contractExternalId, trainer.getId());

        UserDataSheetPlanRequest response = getResponse(distinctCombinations, allUserDataSheetPlan);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1819",
                "All User Data Sheet Plan have been retrieved",
                response
        );
    }

    private UserDataSheetPlanRequest getResponse(List<DistinctUserPlanProjection> distinctCombinations,
                                                 List<UserWorkoutPlan> allUserDataSheetPlan) {
        if(allUserDataSheetPlan.isEmpty()) return null;

        UserDataSheetPlanRequest response = new UserDataSheetPlanRequest();
        response.setContract(MapperServiceHelper.toResponse(allUserDataSheetPlan.get(0).getContract()));
        response.setModality(MapperServiceHelper.toResponse(allUserDataSheetPlan.get(0).getModality()));
        response.setGoal(MapperServiceHelper.toResponse(allUserDataSheetPlan.get(0).getGoal()));
        response.setProgram(MapperServiceHelper.toResponse(allUserDataSheetPlan.get(0).getProgram()));
        response.setPlan(buildPlan(distinctCombinations, allUserDataSheetPlan));

        return response;
    }

    private Map<String, List<UserWorkoutPlanRequest>> buildPlan(List<DistinctUserPlanProjection> distinctCombinations,
                                                                List<UserWorkoutPlan> allUserDataSheetPlan) {
        Map<String,List<UserWorkoutPlanRequest>> plan = new HashMap<>();
        distinctCombinations.forEach(group -> {
            UserWorkoutPlan userWorkoutPlan = allUserDataSheetPlan.stream()
                    .filter(
                            exerciseItem -> group.getModalityId() == exerciseItem.getModality().getId() &&
                                    group.getGoalId() == exerciseItem.getGoal().getId() &&
                                    group.getProgramId() == exerciseItem.getProgram().getId() &&
                                    group.getWorkGroupId() == exerciseItem.getWorkGroup().getId()
                    )
                    .findFirst()
                    .orElse(null);

            assert userWorkoutPlan != null;
            plan.put(userWorkoutPlan.getWorkGroup().getNamePt(), buildExercises(userWorkoutPlan,allUserDataSheetPlan));

        });
        return plan;
    }

    private List<UserWorkoutPlanRequest> buildExercises(UserWorkoutPlan group,
                                                        List<UserWorkoutPlan> allUserDataSheetPlan) {
        List<UserWorkoutPlan> collect = allUserDataSheetPlan.stream()
                .filter(
                        exerciseItem -> Objects.equals(group.getModality().getId(), exerciseItem.getModality().getId()) &&
                                Objects.equals(group.getGoal().getId(), exerciseItem.getGoal().getId()) &&
                                Objects.equals(group.getProgram().getId(), exerciseItem.getProgram().getId()) &&
                                Objects.equals(group.getWorkGroup().getId(), exerciseItem.getWorkGroup().getId())
                )
                .collect(Collectors.toList());

        return collect.stream().map(MapperServiceHelper::toResponse).collect(Collectors.toList());
    }
}
