package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import br.com.jcv.treinadorpro.corelayer.dto.ActivePersonalPlanDTO;
import br.com.jcv.treinadorpro.corelayer.dto.PlanTemplateDTO;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.enums.MasterLanguageEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.mapper.ActivePersonalPlanMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.PlanTemplateMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.model.ActivePersonalPlan;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ActivePersonalPlanRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ParameterRepository;
import br.com.jcv.treinadorpro.corelayer.repository.PlanTemplateRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.infrastructure.config.TreinadorProConfig;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
public class RegisterNewPersonalTrainerServiceImpl extends AbstractUserService implements RegisterNewPersonalTrainerService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ActivePersonalPlanMapper activePersonalPlanMapper;
    private final PlanTemplateMapper planTemplateMapper;
    private final TreinadorProConfig config;
    private final ModelMapper modelMapper;
    private final GuardianRestClientConsumer guardianRestClientConsumer;
    private final PlanTemplateRepository planTemplateRepository;
    private final ActivePersonalPlanRepository activePersonalPlanRepository;
    private final ParameterRepository parameterRepository;

    public RegisterNewPersonalTrainerServiceImpl(UserRepository userRepository,
                                                 UserMapper userMapper, ActivePersonalPlanMapper activePersonalPlanMapper,
                                                 PlanTemplateMapper planTemplateMapper,
                                                 TreinadorProConfig config,
                                                 ModelMapper modelMapper,
                                                 GuardianRestClientConsumer guardianRestClientConsumer,
                                                 PlanTemplateRepository planTemplateRepository,
                                                 ActivePersonalPlanRepository activePersonalPlanRepository, ParameterRepository parameterRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.activePersonalPlanMapper = activePersonalPlanMapper;
        this.planTemplateMapper = planTemplateMapper;
        this.config = config;
        this.modelMapper = modelMapper;
        this.guardianRestClientConsumer = guardianRestClientConsumer;
        this.planTemplateRepository = planTemplateRepository;
        this.activePersonalPlanRepository = activePersonalPlanRepository;
        this.parameterRepository = parameterRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<UserDTO> execute(UUID processId, RegisterRequest registerRequest) {

        checkExistingEmail(registerRequest);

        PlanTemplateDTO planTemplateDTO = getInstancePlanTemplate(registerRequest);

        CreateNewAccountRequest createNewAccountRequest = getInstanceCreateNewAccountRequest(registerRequest);
        ControllerGenericResponse<UUID> accountGuardianResponse = guardianRestClientConsumer.createNewAccount(createNewAccountRequest);

        UserDTO userDTO = getInstanceUserDTO(registerRequest, accountGuardianResponse);
        User userEntity = userMapper.toEntity(userDTO);
        User userSaved = userRepository.save(userEntity);

        ActivePersonalPlanDTO activePersonalPlanDTO = ActivePersonalPlanDTO.builder()
                .price(planTemplateDTO.getPrice())
                .amountDiscount(planTemplateDTO.getAmountDiscount())
                .qtyContractAllowed(planTemplateDTO.getQtyContractAllowed())
                .paymentFrequency(planTemplateDTO.getPaymentFrequency())
                .planExpirationDate(LocalDate.now())
                .description(planTemplateDTO.getDescription())
                .qtyContractAllowed(planTemplateDTO.getQtyContractAllowed())
                .qtyUserStudentAllowed(planTemplateDTO.getQtyUserStudentAllowed())
                .amountDiscount(planTemplateDTO.getAmountDiscount())
                .build();
        ActivePersonalPlan activePersonalPlan = activePersonalPlanMapper.toEntity(activePersonalPlanDTO);
        activePersonalPlan.setPersonalUser(userSaved);
        activePersonalPlanRepository.save(activePersonalPlan);

        ControllerGenericResponse<UserDTO> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                        .msgcode("MSG-0001")
                        .mensagem(accountGuardianResponse.getResponse().getMensagem())
                .build());

        return response;
    }

    private PlanTemplateDTO getInstancePlanTemplate(RegisterRequest registerRequest) {
        return Objects.isNull(registerRequest.getPlanRequest())
                ? getFreemiumPlanTemplate()
                : getCustomPlanTemplate(registerRequest);
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

    private PlanTemplateDTO getCustomPlanTemplate(RegisterRequest registerRequest) {
        PlanTemplateDTO planTemplateDTO;
        planTemplateDTO = planTemplateMapper.toDTO(planTemplateRepository.findByDescriptionAndPaymentFrequencyAndStatus(
                        registerRequest.getPlanRequest().getDescription(),
                        registerRequest.getPlanRequest().getFrequency(),
                        "A")
                .orElseThrow(this::invalidPlan));
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

    private UserDTO getInstanceUserDTO(RegisterRequest registerRequest, ControllerGenericResponse<UUID> accountGuardianResponse) {
        MasterLanguageEnum masterLanguageEnum = MasterLanguageEnum.findByString(registerRequest.getMasterLanguage());
        UserDTO userDTO = modelMapper.map(registerRequest, UserDTO.class);
        userDTO.setUuidId(UUID.randomUUID());
        userDTO.setUserProfile(UserProfileEnum.PERSONAL_TRAINER);
        userDTO.setStatus(StatusEnum.A);
        userDTO.setMasterLanguage(Objects.isNull(masterLanguageEnum) ? MasterLanguageEnum.EN_US : masterLanguageEnum);
        userDTO.setGuardianIntegrationUUID(accountGuardianResponse.getObjectResponse());
        return userDTO;
    }

    private CreateNewAccountRequest getInstanceCreateNewAccountRequest(RegisterRequest registerRequest) {
        CreateNewAccountRequest createNewAccountRequest = new CreateNewAccountRequest();
        createNewAccountRequest.setEmail(registerRequest.getEmail());
        createNewAccountRequest.setPasswd(registerRequest.getPasswd());
        createNewAccountRequest.setPasswdCheck(registerRequest.getPasswdCheck());
        createNewAccountRequest.setExternalApplicationUUID(config.getApiKeyUUID());
        createNewAccountRequest.setName(
                String.join(" ",
                        registerRequest.getFirstName(),
                        registerRequest.getMiddleName(),
                        registerRequest.getLastName()
                ).trim()
        );
        return createNewAccountRequest;
    }
}
