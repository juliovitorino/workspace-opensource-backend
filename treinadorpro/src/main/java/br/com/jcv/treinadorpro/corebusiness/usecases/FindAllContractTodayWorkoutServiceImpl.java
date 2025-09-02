package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class FindAllContractTodayWorkoutServiceImpl implements FindAllContractTodayWorkoutService{

    private final ContractRepository contractRepository;
    private final GetLoggedUserService getLoggedUserService;

    public FindAllContractTodayWorkoutServiceImpl(ContractRepository contractRepository, GetLoggedUserService getLoggedUserService) {
        this.contractRepository = contractRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    public ControllerGenericResponse<List<ContractResponse>> execute(UUID processId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        Map<String, Function<Contract, String>> dayOfWeekStrategy = new HashMap<>();
        dayOfWeekStrategy.put("MONDAY", Contract::getMonday);
        dayOfWeekStrategy.put("TUESDAY", Contract::getTuesday);
        dayOfWeekStrategy.put("WEDNESDAY", Contract::getWednesday);
        dayOfWeekStrategy.put("THURSDAY", Contract::getThursday);
        dayOfWeekStrategy.put("FRIDAY", Contract::getFriday);
        dayOfWeekStrategy.put("SATURDAY", Contract::getSaturday);
        dayOfWeekStrategy.put("SUNDAY", Contract::getSunday);

        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        List<String> expiredTime = getExpiredTime();

        List<Contract> allContractTodayWorkout = contractRepository.findAllContractTodayWorkout(
                SituationEnum.OPEN.name(),
                StatusEnum.A.name(),
                trainer.getId()
        );

        List<Contract> contractsToRemove = allContractTodayWorkout.stream()
                .filter(contract -> expiredTime.contains(dayOfWeekStrategy.get(dayOfWeek.toString()).apply(contract)))
                .collect(Collectors.toList());

        allContractTodayWorkout.removeAll(contractsToRemove);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1503",
                "Today contracts have been retrieved successfully",
                allContractTodayWorkout.stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }

    private List<String> getExpiredTime() {
        LocalTime now = LocalTime.now();
        int nowHH = now.getHour();

        return IntStream.range(1, nowHH)
                .mapToObj(i -> String.format("%02d:00", i))
                .collect(Collectors.toList());
    }


}
