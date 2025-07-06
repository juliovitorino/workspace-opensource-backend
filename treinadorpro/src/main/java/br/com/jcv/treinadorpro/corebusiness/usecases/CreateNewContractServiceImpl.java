package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.enums.MasterLanguageEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import br.com.jcv.treinadorpro.corelayer.model.AvailableTime;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.AvailableTimeRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.CreateNewStudentContractRequest;
import br.com.jcv.treinadorpro.corelayer.request.Instalment;
import br.com.jcv.treinadorpro.corelayer.request.TrainingInfo;
import br.com.jcv.treinadorpro.corelayer.response.CreateNewStudentContractResponse;
import br.com.jcv.treinadorpro.infrastructure.helper.TreinadorProHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import br.com.jcv.treinadorpro.shared.DataIntegrityViolationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CreateNewContractServiceImpl implements CreateNewContractService{

    private final ContractRepository contractRepository;
    private final DataIntegrityViolationMapper dataIntegrityViolationMapper;
    private final TreinadorProHelper treinadorProHelper;
    private final AvailableTimeRepository availableTimeRepository;

    public CreateNewContractServiceImpl(TrainingPackRepository trainingPackRepository,
                                        UserRepository userRepository,
                                        ContractRepository contractRepository,
                                        DataIntegrityViolationMapper dataIntegrityViolationMapper,
                                        TreinadorProHelper treinadorProHelper, AvailableTimeRepository availableTimeRepository) {
        this.contractRepository = contractRepository;
        this.dataIntegrityViolationMapper = dataIntegrityViolationMapper;
        this.treinadorProHelper = treinadorProHelper;
        this.availableTimeRepository = availableTimeRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<CreateNewStudentContractResponse> execute(UUID processId, CreateNewStudentContractRequest request) {

        User personalUser = treinadorProHelper.checkActivePersonalTrainerUUID(request.getPersonalTrainerExternalId());
        validate(request, personalUser);

        Long personalId = personalUser.getId();

        TrainingPack trainingPack = treinadorProHelper.checkTrainingPackByExternalIdAndPersonalUserId(request.getTrainingPackExternalId(), personalId);
        User existingStudent = getExistingStudent(request);
        User newStudent = getInstanceNewStudent(request);
        Contract contract = getInstanceTrainingPack(request, trainingPack, existingStudent, newStudent);
        List<StudentPayment> instalments = getInstalments(request, contract);
        contract.setStudentPaymentList(instalments);

        try {
            log.info("({}) saving contract", processId);
            contractRepository.save(contract);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            throw new CommoditieBaseException(
                    dataIntegrityViolationMapper.getFriendlyMessage(e.getMessage()),
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "MSG-1938");
        }

        log.info("({}) disable free time on weekdays for trainer {}", processId, personalId);
        updateAvailableTime(personalId, request);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1610",
                "New provision contract service was created successfully",
                CreateNewStudentContractResponse.builder()
                        .contractExternalId(contract.getExternalId())
                        .build()
        );
    }

    private void validate(CreateNewStudentContractRequest request, User personalUser) {
        checkAvailableTime(request, personalUser);
        checkNullableRequestParameters(request);
    }

    private void checkAvailableTime(CreateNewStudentContractRequest request, User personalUser) {
        Map<WeekdaysEnum, Supplier<Optional<AvailableTime>>> strategyMap = Map.of(
                WeekdaysEnum.MON, () -> availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(personalUser.getId(), WeekdaysEnum.MON, request.getTrainingInfo().getMonday(), Boolean.TRUE),
                WeekdaysEnum.TUE, () -> availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(personalUser.getId(), WeekdaysEnum.TUE, request.getTrainingInfo().getTuesday(), Boolean.TRUE),
                WeekdaysEnum.WED, () -> availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(personalUser.getId(), WeekdaysEnum.WED, request.getTrainingInfo().getWednesday(), Boolean.TRUE),
                WeekdaysEnum.THU, () -> availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(personalUser.getId(), WeekdaysEnum.THU, request.getTrainingInfo().getThursday(), Boolean.TRUE),
                WeekdaysEnum.FRI, () -> availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(personalUser.getId(), WeekdaysEnum.FRI, request.getTrainingInfo().getFriday(), Boolean.TRUE),
                WeekdaysEnum.SAT, () -> availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(personalUser.getId(), WeekdaysEnum.SAT, request.getTrainingInfo().getSaturday(), Boolean.TRUE),
                WeekdaysEnum.SUN, () -> availableTimeRepository.findByPersonalIdAndDayofweekAndDaytimeAndAvailable(personalUser.getId(), WeekdaysEnum.SUN, request.getTrainingInfo().getSunday(), Boolean.TRUE)
        );
        TrainingInfo trainingInfo = request.getTrainingInfo();

        if(trainingInfo.getMonday() != null){
            Optional<AvailableTime> availableTime = strategyMap.get(WeekdaysEnum.MON).get();
            if(availableTime.isEmpty()){
                throw new CommoditieBaseException("Schedule on Monday is busy", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1025");
            }
        }

        if(trainingInfo.getTuesday() != null){
            Optional<AvailableTime> availableTime = strategyMap.get(WeekdaysEnum.TUE).get();
            if(availableTime.isEmpty()){
                throw new CommoditieBaseException("Schedule on Tuesday is busy", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1026");
            }
        }

        if(trainingInfo.getWednesday() != null){
            Optional<AvailableTime> availableTime = strategyMap.get(WeekdaysEnum.WED).get();
            if(availableTime.isEmpty()){
                throw new CommoditieBaseException("Schedule on Wednesday is busy", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1027");
            }
        }

        if(trainingInfo.getThursday() != null){
            Optional<AvailableTime> availableTime = strategyMap.get(WeekdaysEnum.THU).get();
            if(availableTime.isEmpty()){
                throw new CommoditieBaseException("Schedule on Thursday is busy", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1028");
            }
        }

        if(trainingInfo.getFriday() != null){
            Optional<AvailableTime> availableTime = strategyMap.get(WeekdaysEnum.FRI).get();
            if(availableTime.isEmpty()){
                throw new CommoditieBaseException("Schedule on Friday is busy", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1029");
            }
        }

        if(trainingInfo.getSaturday() != null){
            Optional<AvailableTime> availableTime = strategyMap.get(WeekdaysEnum.SAT).get();
            if(availableTime.isEmpty()){
                throw new CommoditieBaseException("Schedule on Saturday is busy", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1030");
            }
        }

        if(trainingInfo.getSunday() != null){
            Optional<AvailableTime> availableTime = strategyMap.get(WeekdaysEnum.SUN).get();
            if(availableTime.isEmpty()){
                throw new CommoditieBaseException("Schedule on Sunday is busy", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1031");
            }
        }
    }

    private void updateAvailableTime(Long personalId, CreateNewStudentContractRequest request) {
        updateAvailableTime(personalId, WeekdaysEnum.SUN, request.getTrainingInfo().getSunday());
        updateAvailableTime(personalId, WeekdaysEnum.SAT, request.getTrainingInfo().getSaturday());
        updateAvailableTime(personalId, WeekdaysEnum.FRI, request.getTrainingInfo().getFriday());
        updateAvailableTime(personalId, WeekdaysEnum.THU, request.getTrainingInfo().getThursday());
        updateAvailableTime(personalId, WeekdaysEnum.WED, request.getTrainingInfo().getWednesday());
        updateAvailableTime(personalId, WeekdaysEnum.TUE, request.getTrainingInfo().getTuesday());
        updateAvailableTime(personalId, WeekdaysEnum.MON, request.getTrainingInfo().getMonday());
    }

    private void updateAvailableTime(Long id, WeekdaysEnum weekday, String time) {
        if(Objects.nonNull(time))
            availableTimeRepository.updateAvailableTime(Boolean.FALSE, id, weekday, time);
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
