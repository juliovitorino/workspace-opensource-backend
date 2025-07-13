package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.repository.StudentPaymentRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class OverdueAmountContractServiceImpl implements OverdueAmountContractService{

    private final StudentPaymentRepository studentPaymentRepository;
    private final GetLoggedUserService getLoggedUserService;

    public OverdueAmountContractServiceImpl(StudentPaymentRepository studentPaymentRepository, GetLoggedUserService getLoggedUserService) {
        this.studentPaymentRepository = studentPaymentRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    public BigDecimal execute(UUID processId) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        return studentPaymentRepository.sumOverduePayments(trainer.getId());
    }
}
