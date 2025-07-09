package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.dto.SessionStateDTO;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.treinadorpro.corebusiness.users.FindPersonalTrainerByGuardianIdService;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.helper.TreinadorProHelper;
import br.com.jcv.treinadorpro.infrastructure.interceptor.RequestTokenHolder;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FindAllActiveContractsServiceImpl implements FindAllActiveContractsService {

    private final ContractRepository contractRepository;
    private final GetLoggedUserService getLoggedUserService;

    public FindAllActiveContractsServiceImpl(ContractRepository contractRepository,
                                             GetLoggedUserService getLoggedUserService) {
        this.contractRepository = contractRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<List<ContractResponse>> execute(UUID processId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId).getObjectResponse();

        List<Contract> allContracts = contractRepository.findAllContracts(trainer.getId(), StatusEnum.A);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1749",
                "All active contracts have been retrieved successfully",
                allContracts.stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
