package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.repository.StudentPaymentRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentPaymentResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FindAllFutureRevenueServiceImpl implements FindAllFutureRevenueService{

    private final GetLoggedUserService getLoggedUserService;
    private final StudentPaymentRepository studentPaymentRepository;

    public FindAllFutureRevenueServiceImpl(GetLoggedUserService getLoggedUserService,
                                           StudentPaymentRepository studentPaymentRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.studentPaymentRepository = studentPaymentRepository;
    }

    @Override
    public ControllerGenericResponse<List<StudentPaymentResponse>> execute(UUID processId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        List<StudentPayment> allFutureRevenue = studentPaymentRepository.findAllFutureRevenue(trainer.getId());
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1809",
                "All future revenue have been retrieved successfully",
                allFutureRevenue.stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
