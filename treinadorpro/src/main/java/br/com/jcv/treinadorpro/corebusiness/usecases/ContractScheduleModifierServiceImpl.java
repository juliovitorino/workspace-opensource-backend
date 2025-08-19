package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import br.com.jcv.treinadorpro.corelayer.model.AvailableTime;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.AvailableTimeRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.request.ContractScheduleModifierRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@Slf4j
public class ContractScheduleModifierServiceImpl implements ContractScheduleModifierService {
    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;
    private final AvailableTimeRepository availableTimeRepository;

    public ContractScheduleModifierServiceImpl(GetLoggedUserService getLoggedUserService,
                                               ContractRepository contractRepository,
                                               AvailableTimeRepository availableTimeRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
        this.availableTimeRepository = availableTimeRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, ContractScheduleModifierRequest request) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        Contract contract = contractRepository.findByExternalIdAndPersonalId(request.getContractExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Contract", HttpStatus.BAD_REQUEST, "MSG-1227"));

        validateDayOfWeek(request::getMonday, contract::getMonday, WeekdaysEnum.MON, contract::setMonday, contract);
        validateDayOfWeek(request::getTuesday, contract::getTuesday, WeekdaysEnum.TUE, contract::setTuesday, contract);
        validateDayOfWeek(request::getWednesday, contract::getWednesday, WeekdaysEnum.WED, contract::setWednesday, contract);
        validateDayOfWeek(request::getThursday, contract::getThursday, WeekdaysEnum.THU, contract::setThursday, contract);
        validateDayOfWeek(request::getFriday, contract::getFriday, WeekdaysEnum.FRI, contract::setFriday, contract);
        validateDayOfWeek(request::getSaturday, contract::getSaturday, WeekdaysEnum.SAT, contract::setSaturday, contract);
        validateDayOfWeek(request::getSunday, contract::getSunday, WeekdaysEnum.SUN, contract::setSunday, contract);

        // salva o contrato com as modificações
        contractRepository.save(contract);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1226",
                "Your Contract Schedule has been modified",
                Boolean.TRUE
        );
    }

    private void validateDayOfWeek(Supplier<String> requestedDay,
                                   Supplier<String> contractDay,
                                   WeekdaysEnum weekday,
                                   Consumer<String> changeContractSchedule,
                                   Contract contract) {

        String newRequestedSchedule = requestedDay.get();
        String oldSchedule = contractDay.get();
        User personalUser = contract.getTrainingPack().getPersonalUser();

        if (!Objects.equals(newRequestedSchedule, oldSchedule)) {
            changeContractSchedule.accept(newRequestedSchedule);

            if (newRequestedSchedule != null) {
                AvailableTime newAvailabelTime = availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(
                                personalUser.getId(),
                                weekday,
                                newRequestedSchedule,
                                Boolean.TRUE
                        )
                        .orElseThrow(() -> new CommoditieBaseException(
                                String.format("Horário às %s está ocupado", newRequestedSchedule),
                                HttpStatus.UNPROCESSABLE_ENTITY,
                                "MSG-1250"));

                // lock time
                newAvailabelTime.setAvailable(Boolean.FALSE);
                availableTimeRepository.save(newAvailabelTime);
            }

            if(oldSchedule != null){

                // unlock time
                AvailableTime oldUnavailabelTime = availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(
                                personalUser.getId(),
                                weekday,
                                oldSchedule,
                                Boolean.FALSE
                        )
                        .orElseThrow(() -> new CommoditieBaseException(
                                String.format("Horário às %s está livre", newRequestedSchedule),
                                HttpStatus.UNPROCESSABLE_ENTITY,
                                "MSG-1250"));

                // unlock time
                oldUnavailabelTime.setAvailable(Boolean.TRUE);
                availableTimeRepository.save(oldUnavailabelTime);

            }

            System.out.println("interno");

        }
        System.out.println("parada");

    }
}
