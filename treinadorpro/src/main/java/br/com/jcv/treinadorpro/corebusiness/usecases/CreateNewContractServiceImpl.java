package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.enums.MasterLanguageEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.CreateNewStudentContractRequest;
import br.com.jcv.treinadorpro.corelayer.request.Instalment;
import br.com.jcv.treinadorpro.corelayer.response.CreateNewStudentContractResponse;
import br.com.jcv.treinadorpro.infrastructure.helper.TreinadorProHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import br.com.jcv.treinadorpro.shared.DataIntegrityViolationMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateNewContractServiceImpl implements CreateNewContractService{

    private final ContractRepository contractRepository;
    private final DataIntegrityViolationMapper dataIntegrityViolationMapper;
    private final TreinadorProHelper treinadorProHelper;

    public CreateNewContractServiceImpl(TrainingPackRepository trainingPackRepository,
                                        UserRepository userRepository,
                                        ContractRepository contractRepository,
                                        DataIntegrityViolationMapper dataIntegrityViolationMapper,
                                        TreinadorProHelper treinadorProHelper) {
        this.contractRepository = contractRepository;
        this.dataIntegrityViolationMapper = dataIntegrityViolationMapper;
        this.treinadorProHelper = treinadorProHelper;
    }

    @Override
    public ControllerGenericResponse<CreateNewStudentContractResponse> execute(UUID processId, CreateNewStudentContractRequest request) {
        checkNullableRequestParameters(request);

        User personalUser = treinadorProHelper.checkActivePersonalTrainerUUID(request.getPersonalTrainerExternalId());
        TrainingPack trainingPack = treinadorProHelper.checkTrainingPackByExternalIdAndPersonalUserId(request.getTrainingPackExternalId(), personalUser.getId());
        User existingStudent = getExistingStudent(request);
        User newStudent = getInstanceNewStudent(request);
        Contract contract = getInstanceTrainingPack(request, trainingPack, existingStudent, newStudent);
        List<StudentPayment> instalments = getInstalments(request, contract);
        contract.setStudentPaymentList(instalments);

        try {
            contractRepository.save(contract);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            throw new CommoditieBaseException(
                    dataIntegrityViolationMapper.getFriendlyMessage(e.getMessage()),
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "MSG-1938");
        }

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1610",
                "New provision contract service was created successfully",
                CreateNewStudentContractResponse.builder()
                        .contractExternalId(contract.getExternalId())
                        .build()
        );
    }

    private List<StudentPayment> getInstalments(CreateNewStudentContractRequest request, Contract contract){
        return request.getInstalmentList()
                .stream()
                .map(item -> getInstalments(item, contract))
                .collect(Collectors.toList());
    }

    private StudentPayment getInstalments(Instalment instalment, Contract contract){
        return StudentPayment.builder()
                .contract(contract)
                .externalId(UUID.randomUUID())
                .duedate(instalment.getDuedate())
                .amount(instalment.getAmount())
                .status(StatusEnum.A.name())
                .build();
    }

    private Contract getInstanceTrainingPack(CreateNewStudentContractRequest request,
                                             TrainingPack trainingPack,
                                             User existingStudent,
                                             User newStudent) {
        return Contract.builder()
                .externalId(UUID.randomUUID())
                .price(trainingPack.getPrice())
                .currency(trainingPack.getCurrency())
                .status(StatusEnum.A)
                .trainingPack(trainingPack)
                .studentUser(existingStudent != null? existingStudent : newStudent)
                .description(request.getTrainingInfo().getGoal())
                .monday(request.getTrainingInfo().getMonday())
                .tuesday(request.getTrainingInfo().getTuesday())
                .wednesday(request.getTrainingInfo().getWednesday())
                .thursday(request.getTrainingInfo().getThursday())
                .friday(request.getTrainingInfo().getFriday())
                .saturday(request.getTrainingInfo().getSaturday())
                .sunday(request.getTrainingInfo().getSunday())
                .duration(request.getTrainingInfo().getDuration())
                .build();
    }

    private User getInstanceNewStudent(CreateNewStudentContractRequest request){
        return request.getNewStudent() == null
                ? null
                : User.builder()
                .uuidId(UUID.randomUUID())
                .name(request.getNewStudent().getName())
                .birthday(request.getNewStudent().getBirthday())
                .cellphone(request.getNewStudent().getPhone())
                .email(request.getNewStudent().getEmail())
                .gender(request.getNewStudent().getGender())
                .userProfile(UserProfileEnum.STUDENT)
                .masterLanguage(MasterLanguageEnum.EN_US.getLanguage())
                .status(StatusEnum.A)
                .build();
    }

    private User getExistingStudent(CreateNewStudentContractRequest request){
        return request.getExistingStudentExternalId() != null
                ? treinadorProHelper.checkActiveStudentUUID(request.getExistingStudentExternalId())
                : null;
    }

    private void checkNullableRequestParameters(CreateNewStudentContractRequest request){
        if(request.getExistingStudentExternalId() == null && request.getNewStudent() == null){
            throw new CommoditieBaseException("Both existingStudentExternalId and newStudent request are invalid",
                    HttpStatus.BAD_REQUEST,
                    "MSG-1654");
        }

        if(request.getTrainingInfo() == null){
            throw new CommoditieBaseException("Training Pack Info must not be null", HttpStatus.BAD_REQUEST, "MSG-1718");
        }

        if(request.getInstalmentList().isEmpty()){
            throw new CommoditieBaseException("Instalments is empty", HttpStatus.BAD_REQUEST, "MSG-1807");
        }
    }
}
