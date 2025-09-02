package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class FindContractServiceImpl implements FindContractService{

    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;

    public FindContractServiceImpl(GetLoggedUserService getLoggedUserService, ContractRepository contractRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
    }

    @Override
    public ControllerGenericResponse<ContractResponse> execute(UUID processId, UUID contractExternalId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        Contract contract = contractRepository.findByExternalIdAndPersonalId(contractExternalId, trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid contract", HttpStatus.BAD_REQUEST, "MSG-1545"));
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1546",
                "Contract has been retrieved successfully",
                MapperServiceHelper.toResponse(contract)
        );
    }


}
