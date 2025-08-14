package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.repository.StudentPaymentRepository;
import br.com.jcv.treinadorpro.corelayer.request.ReceiveStudentPaymentRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class ReceiveStudentPaymentServiceImpl implements ReceiveStudentPaymentService {

    private final GetLoggedUserService getLoggedUserService;
    private final StudentPaymentRepository studentPaymentRepository;

    public ReceiveStudentPaymentServiceImpl(GetLoggedUserService getLoggedUserService,
                                            StudentPaymentRepository studentPaymentRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.studentPaymentRepository = studentPaymentRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, ReceiveStudentPaymentRequest request) {
        getLoggedUserService.execute(processId);
        StudentPayment studentPayment = studentPaymentRepository.findByExternalId(request.getStudentPaymentExternalId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Payment ID", HttpStatus.BAD_REQUEST, "MSG-0853"));

        BigDecimal amountToReceive = studentPayment.getAmount().subtract(
                studentPayment.getReceivedAmount() == null
                        ? BigDecimal.ZERO
                        : studentPayment.getReceivedAmount()
        );

        if (request.getReceivedAmount().compareTo(amountToReceive) > 0) {
            throw new CommoditieBaseException("Received amount is above (" + amountToReceive + ")", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-0906");
        }

        studentPayment.setPaymentDate(request.getPaymentDate());
        studentPayment.setPaymentMethod(request.getPaymentMethod());
        studentPayment.setComment(request.getComment());

        BigDecimal existingReceivedAmount = studentPayment.getReceivedAmount() == null
                ? request.getReceivedAmount()
                : studentPayment.getReceivedAmount().add(request.getReceivedAmount());
        studentPayment.setReceivedAmount(existingReceivedAmount);

        studentPaymentRepository.save(studentPayment);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-0856",
                "Student payment has been recorded successfully",
                Boolean.TRUE
        );
    }
}
