package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.AccountStatement;
import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.model.StudentPaymentsTransaction;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.AccountStatementRepository;
import br.com.jcv.treinadorpro.corelayer.repository.StudentPaymentRepository;
import br.com.jcv.treinadorpro.corelayer.request.ReceiveStudentPaymentRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ReceiveStudentPaymentServiceImpl implements ReceiveStudentPaymentService {

    private final GetLoggedUserService getLoggedUserService;
    private final StudentPaymentRepository studentPaymentRepository;
    private final AccountStatementRepository accountStatementRepository;

    public ReceiveStudentPaymentServiceImpl(GetLoggedUserService getLoggedUserService,
                                            StudentPaymentRepository studentPaymentRepository,
                                            AccountStatementRepository accountStatementRepository) {
        this.getLoggedUserService = getLoggedUserService;
        this.studentPaymentRepository = studentPaymentRepository;
        this.accountStatementRepository = accountStatementRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, ReceiveStudentPaymentRequest request) {
        getLoggedUserService.execute(processId);

        StudentPayment studentPayment = studentPaymentRepository.findByExternalId(request.getStudentPaymentExternalId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Payment ID", HttpStatus.BAD_REQUEST, "MSG-0853"));

        BigDecimal sumPayments = studentPayment.getStudentPaymentsTransactions()
                .stream()
                .map(StudentPaymentsTransaction::getReceivedAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal amountToReceive = studentPayment.getAmount().subtract(sumPayments);

        if (request.getReceivedAmount().compareTo(amountToReceive) > 0) {
            throw new CommoditieBaseException("Received amount is above (" + amountToReceive + ")", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-0906");
        }

        StudentPaymentsTransaction studentPaymentsTransactionInstance = getStudentPaymentsTransactionInstance(request);
        studentPaymentsTransactionInstance.setStudentPayment(studentPayment);
        studentPayment.getStudentPaymentsTransactions().add(studentPaymentsTransactionInstance);

        AccountStatement accountStatementInstance = getAccountStatementInstance(studentPaymentsTransactionInstance, studentPayment);

        studentPaymentRepository.save(studentPayment);
        accountStatementRepository.save(accountStatementInstance);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-0856",
                "Student payment has been recorded successfully",
                Boolean.TRUE
        );
    }

    private AccountStatement getAccountStatementInstance(StudentPaymentsTransaction transaction,
                                                         StudentPayment studentPayment) {
        User trainer = studentPayment.getContract().getTrainingPack().getPersonalUser();
        User student = studentPayment.getContract().getStudentUser();
        String descriptionText = "Contract %s :: Student %s :: %s";
        return AccountStatement.builder()
                .externalId(UUID.randomUUID())
                .personalUser(trainer)
                .studentUser(student)
                .amount(transaction.getReceivedAmount())
                .type(AccountStatement.EntryType.CREDIT)
                .paymentMethod(transaction.getPaymentMethod())
                .entryDate(transaction.getPaymentDate())
                .status(StatusEnum.A)
                .description(String.format(
                                descriptionText,
                                studentPayment.getContract().getDescription(),
                                student.getName(),
                                transaction.getComment()
                        )
                )
                .build();
    }

    private StudentPaymentsTransaction getStudentPaymentsTransactionInstance(ReceiveStudentPaymentRequest request) {
        return StudentPaymentsTransaction.builder()
                .externalId(UUID.randomUUID())
                .paymentDate(request.getPaymentDate())
                .paymentMethod(request.getPaymentMethod())
                .receivedAmount(request.getReceivedAmount())
                .comment(request.getComment())
                .status(StatusEnum.A)
                .build();
    }
}
