package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import br.com.jcv.treinadorpro.corelayer.repository.AvailableTimeRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.TrainerAvailableTimeResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FindTrainerAvailableTimeServiceImpl implements FindTrainerAvailableTimeService{

    private final GetLoggedUserService getLoggedUserService;
    private final AvailableTimeRepository availableTimeRepository;

    public FindTrainerAvailableTimeServiceImpl(GetLoggedUserService getLoggedUserService,
                                               AvailableTimeRepository availableTimeRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.availableTimeRepository = availableTimeRepository;
    }

    @Override
    public ControllerGenericResponse<TrainerAvailableTimeResponse> execute(UUID processId) {

        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        List<String> mondayAvailable = availableTimeRepository.findAvailableTimeByTrainerId(trainer.getId(), WeekdaysEnum.MON);
        List<String> tuesdayAvailable = availableTimeRepository.findAvailableTimeByTrainerId(trainer.getId(), WeekdaysEnum.TUE);
        List<String> wednesdayAvailable = availableTimeRepository.findAvailableTimeByTrainerId(trainer.getId(), WeekdaysEnum.WED);
        List<String> thursdayAvailable = availableTimeRepository.findAvailableTimeByTrainerId(trainer.getId(), WeekdaysEnum.THU);
        List<String> fridayAvailable = availableTimeRepository.findAvailableTimeByTrainerId(trainer.getId(), WeekdaysEnum.FRI);
        List<String> saturdayAvailable = availableTimeRepository.findAvailableTimeByTrainerId(trainer.getId(), WeekdaysEnum.SAT);
        List<String> sundayAvailable = availableTimeRepository.findAvailableTimeByTrainerId(trainer.getId(), WeekdaysEnum.SUN);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1618",
                "Your available time have been retrieved successfully",
                TrainerAvailableTimeResponse.builder()
                        .trainer(trainer)
                        .monTotal(mondayAvailable.size())
                        .tueTotal(tuesdayAvailable.size())
                        .wedTotal(wednesdayAvailable.size())
                        .thuTotal(thursdayAvailable.size())
                        .friTotal(fridayAvailable.size())
                        .satTotal(saturdayAvailable.size())
                        .sunTotal(sundayAvailable.size())
                        .mondayAvailableTimes(mondayAvailable)
                        .tuesdayAvailableTimes(tuesdayAvailable)
                        .wednesdayAvailableTimes(wednesdayAvailable)
                        .thursdayAvailableTimes(thursdayAvailable)
                        .fridayAvailableTimes(fridayAvailable)
                        .saturdayAvailableTimes(saturdayAvailable)
                        .sundayAvailableTimes(sundayAvailable)
                        .build()
        );
    }
}
