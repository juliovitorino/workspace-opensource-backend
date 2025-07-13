package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CounterStudentContractsServiceImpl implements CounterStudentContractsService{

    private final ContractRepository contractRepository;
    private final GetLoggedUserService getLoggedUserService;

    public CounterStudentContractsServiceImpl(ContractRepository contractRepository, GetLoggedUserService getLoggedUserService) {
        this.contractRepository = contractRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    public Long execute(UUID processId, SituationEnum situationEnum) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        return contractRepository.countOpenAndActiveContracts(situationEnum, StatusEnum.A, trainer.getId());
    }
}
