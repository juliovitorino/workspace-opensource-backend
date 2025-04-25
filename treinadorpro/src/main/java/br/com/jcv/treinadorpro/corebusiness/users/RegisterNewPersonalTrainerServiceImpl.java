package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import br.com.jcv.treinadorpro.corelayer.dto.ActivePersonalPlanDTO;
import br.com.jcv.treinadorpro.corelayer.dto.PlanTemplateDTO;
import br.com.jcv.treinadorpro.corelayer.dto.UserDTO;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.mapper.ActivePersonalPlanMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.PlanTemplateMapper;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.model.ActivePersonalPlan;
import br.com.jcv.treinadorpro.corelayer.model.Parameter;
import br.com.jcv.treinadorpro.corelayer.model.PlanTemplate;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterNewPersonalTrainerServiceImpl implements RegisterNewPersonalTrainerService {

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

        Optional<User> userByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (userByEmail.isPresent()) {
            throw new CommoditieBaseException("Email has already exist!", HttpStatus.BAD_REQUEST,"MSG-1922");
        }

        PlanTemplateDTO planTemplateDTO;
        if(Objects.isNull(registerRequest.getPlanRequest())) {
            Long freemiumPlanId = Long.valueOf(parameterRepository.findByKeytag("FREEMIUM_PLAN_ID")
                    .orElseThrow(() -> new CommoditieBaseException("Freemium Keytag not found.", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1649"))
                    .getValuetag());

            planTemplateDTO = planTemplateMapper.toDTO(planTemplateRepository.findById(freemiumPlanId)
                    .orElseThrow(() -> new CommoditieBaseException("Invalid Freemium Id.", HttpStatus.UNPROCESSABLE_ENTITY,"MSG-1653")));
        } else {
            planTemplateDTO = planTemplateMapper.toDTO(planTemplateRepository.findByDescriptionAndPaymentFrequencyAndStatus(
                            registerRequest.getPlanRequest().getDescription(),
                            registerRequest.getPlanRequest().getFrequency(),
                            "A")
                    .orElseThrow(() -> new CommoditieBaseException("Invalid Plan", HttpStatus.BAD_REQUEST, "MSG-0914")));
        }

        CreateNewAccountRequest createNewAccountRequest = getInstanceCreateNewAccountRequest(registerRequest);
        ControllerGenericResponse<UUID> accountGuardianResponse = guardianRestClientConsumer.createNewAccount(createNewAccountRequest);

        UserDTO userDTO = getInstanceUserDTO(registerRequest, accountGuardianResponse);
        User userEntity = userMapper.toEntity(userDTO);
        User userSaved = userRepository.save(userEntity);

        ActivePersonalPlanDTO activePersonalPlanDTO = ActivePersonalPlanDTO.builder()
                .price(planTemplateDTO.getPrice())
                .amountDiscount(planTemplateDTO.getAmountDiscount())
                .qtyUserPackTrainingAllowed(planTemplateDTO.getQtyUserPackTrainingAllowed())
                .paymentFrequency(planTemplateDTO.getPaymentFrequency())
                .planExpirationDate(LocalDate.now())
                .description(planTemplateDTO.getDescription())
                .qtyUserPackTrainingAllowed(planTemplateDTO.getQtyUserPackTrainingAllowed())
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

    private UserDTO getInstanceUserDTO(RegisterRequest registerRequest, ControllerGenericResponse<UUID> accountGuardianResponse) {
        UserDTO userDTO = modelMapper.map(registerRequest, UserDTO.class);
        userDTO.setUserProfile(UserProfileEnum.PERSONAL_TRAINER);
        userDTO.setStatus("A");
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
