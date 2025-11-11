package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.commons.library.commodities.service.BusinessServiceNoOutput;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import br.com.jcv.treinadorpro.corelayer.exception.ContractHasAlreadyCancelledException;
import br.com.jcv.treinadorpro.corelayer.exception.InvalidContractCancelCodeException;
import br.com.jcv.treinadorpro.corelayer.model.AvailableTime;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.request.CancelContractRequest;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Service
@Slf4j
public class CancelContractServiceImpl implements CancelContractService {

    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;

    public CancelContractServiceImpl(GetLoggedUserService getLoggedUserService,
                                     ContractRepository contractRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, CancelContractRequest request) {

        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);

        Contract contract = contractRepository.findByExternalIdAndPersonalId(request.getExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Contract", HttpStatus.BAD_REQUEST, "MSG-0815"));

        validate(contract, request);

        processCancelContractExecutor(processId, contract);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-0812",
                "Contract has been cancelled successfully",
                Boolean.TRUE
        );
    }

    private void processCancelContractExecutor(UUID processId, Contract c) {
        Map<WeekdaysEnum, Supplier<String>> strategyDayTime = new HashMap<>();
        strategyDayTime.put(WeekdaysEnum.MON, c::getMonday);
        strategyDayTime.put(WeekdaysEnum.TUE, c::getTuesday);
        strategyDayTime.put(WeekdaysEnum.WED, c::getWednesday);
        strategyDayTime.put(WeekdaysEnum.THU, c::getThursday);
        strategyDayTime.put(WeekdaysEnum.FRI, c::getFriday);
        strategyDayTime.put(WeekdaysEnum.SAT, c::getSaturday);
        strategyDayTime.put(WeekdaysEnum.SUN, c::getSunday);

        Predicate<AvailableTime> availableTimeValidator = (at) -> strategyDayTime.get(at.getDaysOfWeek()).get() != null &&
                strategyDayTime.get(at.getDaysOfWeek()).get().equals(at.getDayTime());

        log.info("({}) updating cancel status", processId);
        c.setStatus(StatusEnum.B);
        c.setSituation(SituationEnum.CANCELLED);
        c.setPurgeAt(LocalDate.now().plusDays(367));
        c.setCancelAt(LocalDate.now());

        log.info("({}) cancel future installments", processId);
        c.getStudentPaymentList()
                .stream()
                .filter(b -> b.getDuedate().isAfter(LocalDate.now()))
                .forEach(b -> b.setStatus(StatusEnum.I.name()));

        log.info("({}) return available time", processId);
        c.getTrainingPack().getPersonalUser().getAvailableTimeList()
                .forEach(at -> {
                    if (availableTimeValidator.test(at)) {
                        at.setAvailable(Boolean.TRUE);
                    }
                });

        contractRepository.save(c);

    }

    private void validate(Contract contract, CancelContractRequest request) {
        Predicate<SituationEnum> situationValidator = s -> s.equals(contract.getSituation());
        Predicate<String> cancelCodeValidator = c -> contract.getCancelCode() == null || !c.equals(contract.getCancelCode());

        if (situationValidator.test(SituationEnum.CANCELLED)) {
            throw new ContractHasAlreadyCancelledException("Contract has already cancelled", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-0835");
        }

        if (cancelCodeValidator.test(request.getCancelCode())) {
            throw new InvalidContractCancelCodeException("Invalid contract cancel code", HttpStatus.BAD_REQUEST, "MSG-0837");
        }
    }
}
