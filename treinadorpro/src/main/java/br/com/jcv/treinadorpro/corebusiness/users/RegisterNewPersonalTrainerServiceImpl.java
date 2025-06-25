package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.template.ReaderTemplate;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import br.com.jcv.restclient.guardian.request.RegisterResponse;
import br.com.jcv.treinadorpro.corebusiness.AbstractTreinadorProService;
import br.com.jcv.treinadorpro.corelayer.dto.ActivePersonalPlanDTO;
import br.com.jcv.treinadorpro.corelayer.dto.PlanTemplateDTO;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.enums.MasterLanguageEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import br.com.jcv.treinadorpro.corelayer.mapper.ActivePersonalPlanMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.PlanTemplateMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.model.ActivePersonalPlan;
import br.com.jcv.treinadorpro.corelayer.model.AvailableTime;
import br.com.jcv.treinadorpro.corelayer.model.PlanTemplate;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ActivePersonalPlanRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ParameterRepository;
import br.com.jcv.treinadorpro.corelayer.repository.PlanTemplateRepository;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.infrastructure.config.TreinadorProConfig;
import br.com.jcv.treinadorpro.infrastructure.email.EmailService;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RegisterNewPersonalTrainerServiceImpl extends AbstractTreinadorProService implements RegisterNewPersonalTrainerService {

    private final UserRepository userRepository;
    private final TrainingPackRepository trainingPackRepository;
    private final UserMapper userMapper;
    private final ActivePersonalPlanMapper activePersonalPlanMapper;
    private final PlanTemplateMapper planTemplateMapper;
    private final TreinadorProConfig config;
    private final ModelMapper modelMapper;
    private final GuardianRestClientConsumer guardianRestClientConsumer;
    private final PlanTemplateRepository planTemplateRepository;
    private final ActivePersonalPlanRepository activePersonalPlanRepository;
    private final ParameterRepository parameterRepository;
    private final EmailService emailService;

    public RegisterNewPersonalTrainerServiceImpl(UserRepository userRepository,
                                                 TrainingPackRepository trainingPackRepository,
                                                 UserMapper userMapper,
                                                 ActivePersonalPlanMapper activePersonalPlanMapper,
                                                 PlanTemplateMapper planTemplateMapper,
                                                 TreinadorProConfig config,
                                                 ModelMapper modelMapper,
                                                 GuardianRestClientConsumer guardianRestClientConsumer,
                                                 PlanTemplateRepository planTemplateRepository,
                                                 ActivePersonalPlanRepository activePersonalPlanRepository,
                                                 ParameterRepository parameterRepository,
                                                 EmailService emailService) {
        super(userRepository, trainingPackRepository);
        this.userRepository = userRepository;
        this.trainingPackRepository = trainingPackRepository;
        this.userMapper = userMapper;
        this.activePersonalPlanMapper = activePersonalPlanMapper;
        this.planTemplateMapper = planTemplateMapper;
        this.config = config;
        this.modelMapper = modelMapper;
        this.guardianRestClientConsumer = guardianRestClientConsumer;
        this.planTemplateRepository = planTemplateRepository;
        this.activePersonalPlanRepository = activePersonalPlanRepository;
        this.parameterRepository = parameterRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<RegisterResponse> execute(UUID processId, RegisterRequest registerRequest) {

        checkExistingEmail(registerRequest);

        PlanTemplate planTemplate = planTemplateRepository.findByExternalId(registerRequest.getPlanExternalId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Plan", HttpStatus.BAD_REQUEST, "MSG-1634"));

        CreateNewAccountRequest createNewAccountRequest = getInstanceCreateNewAccountRequest(registerRequest);
        ControllerGenericResponse<RegisterResponse> accountGuardianResponse = guardianRestClientConsumer.createNewAccount(createNewAccountRequest);

        UserDTO userDTO = getInstanceUserDTO(registerRequest, accountGuardianResponse);
        User userEntity = userMapper.toEntity(userDTO);
        userEntity.setAvailableTimeList(createAvailableTimeList(userEntity));
        User userSaved = userRepository.save(userEntity);

        ActivePersonalPlanDTO activePersonalPlanDTO = ActivePersonalPlanDTO.builder()
                .externalId(UUID.randomUUID())
                .price(planTemplate.getPrice())
                .amountDiscount(planTemplate.getAmountDiscount())
                .qtyContractAllowed(planTemplate.getQtyContractAllowed())
                .paymentFrequency(planTemplate.getPaymentFrequency())
                .planExpirationDate(LocalDate.now().plusDays(30))
                .description(planTemplate.getDescription())
                .qtyContractAllowed(planTemplate.getQtyContractAllowed())
                .qtyUserStudentAllowed(planTemplate.getQtyUserStudentAllowed())
                .amountDiscount(planTemplate.getAmountDiscount())
                .build();
        ActivePersonalPlan activePersonalPlan = activePersonalPlanMapper.toEntity(activePersonalPlanDTO);
        activePersonalPlan.setPersonalUser(userSaved);
        activePersonalPlanRepository.save(activePersonalPlan);


        try {
            emailService.sendHtmlEmail(
                    userSaved.getEmail(),
                    "Validate your account",
                    ReaderTemplate.read("templates/account-validation.html"),
                    Map.of("CODE", accountGuardianResponse.getObjectResponse().getCode())
            );
        }  catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1643",
                accountGuardianResponse.getResponse().getMensagem(),
                RegisterResponse.builder()
                        .externalUserId(userSaved.getUuidId())
                        .code(accountGuardianResponse.getObjectResponse().getCode())
                        .build()

        );
    }

    private List<AvailableTime> createAvailableTimeList(User userEntity) {

        List<AvailableTime> availableTimeList = new ArrayList<>();
        List<String> availableTimes = IntStream.rangeClosed(5, 23)
                .mapToObj(hour -> String.format("%02d:00", hour))
                .collect(Collectors.toList());

        for (WeekdaysEnum day : WeekdaysEnum.values()) {
            for (String availableTime : availableTimes) {
                availableTimeList.add(
                        AvailableTime.builder()
                                .externalId(UUID.randomUUID())
                                .available(Boolean.TRUE)
                                .daysOfWeek(day)
                                .dayTime(availableTime)
                                .personalUser(userEntity)
                                .build()
                );
            }

        }

        return availableTimeList;
    }


    private PlanTemplateDTO getFreemiumPlanTemplate() {
        PlanTemplateDTO planTemplateDTO;
        Long freemiumPlanId = Long.valueOf(parameterRepository.findByKeytag("FREEMIUM_PLAN_ID")
                .orElseThrow(this::freemiumKeytagNotFound)
                .getValuetag());

        planTemplateDTO = planTemplateMapper.toDTO(planTemplateRepository.findById(freemiumPlanId)
                .orElseThrow(this::invalidFreemiumId));
        return planTemplateDTO;
    }

    private CommoditieBaseException freemiumKeytagNotFound() {
        return new CommoditieBaseException("Freemium Keytag not found.", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1649");
    }

    private CommoditieBaseException invalidFreemiumId() {
        return new CommoditieBaseException("Invalid Freemium Id.", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1653");
    }

    private CommoditieBaseException invalidPlan() {
        return new CommoditieBaseException("Invalid Plan", HttpStatus.BAD_REQUEST, "MSG-0914");
    }

    private UserDTO getInstanceUserDTO(RegisterRequest registerRequest, ControllerGenericResponse<RegisterResponse> accountGuardianResponse) {
        MasterLanguageEnum masterLanguageEnum = MasterLanguageEnum.findByString(registerRequest.getMasterLanguage());
        UserDTO userDTO = toDTO(registerRequest);
        userDTO.setUuidId(UUID.randomUUID());
        userDTO.setUserProfile(UserProfileEnum.PERSONAL_TRAINER);
        userDTO.setStatus(StatusEnum.A);
        userDTO.setMasterLanguage(Objects.isNull(masterLanguageEnum) ? MasterLanguageEnum.EN_US : masterLanguageEnum);
        userDTO.setGuardianIntegrationUUID(accountGuardianResponse.getObjectResponse().getExternalUserId());
        return userDTO;
    }

    private CreateNewAccountRequest getInstanceCreateNewAccountRequest(RegisterRequest registerRequest) {
        CreateNewAccountRequest createNewAccountRequest = new CreateNewAccountRequest();
        createNewAccountRequest.setEmail(registerRequest.getEmail());
        createNewAccountRequest.setPasswd(registerRequest.getPasswd());
        createNewAccountRequest.setPasswdCheck(registerRequest.getPasswdCheck());
        createNewAccountRequest.setExternalApplicationUUID(config.getApiKeyUUID());
        createNewAccountRequest.setName(registerRequest.getName().trim());
        return createNewAccountRequest;
    }

    private static UserDTO toDTO(RegisterRequest registerRequest) {
        return UserDTO.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .cellphone(registerRequest.getCellphone())
                .birthday(registerRequest.getBirthday())
                .gender(registerRequest.getGender())
                .build();
    }
}
