package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.restclient.guardian.GuardianRestClientConsumer;
import br.com.jcv.restclient.guardian.LoginRequest;
import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import br.com.jcv.restclient.guardian.request.RegisterResponse;
import br.com.jcv.restclient.guardian.request.ValidateSixCodeRequest;
import br.com.jcv.treinadorpro.corelayer.enums.LoginSocialProviderEnum;
import br.com.jcv.treinadorpro.corelayer.enums.MasterLanguageEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import br.com.jcv.treinadorpro.corelayer.model.ActivePersonalPlan;
import br.com.jcv.treinadorpro.corelayer.model.PlanTemplate;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ActivePersonalPlanRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ParameterRepository;
import br.com.jcv.treinadorpro.corelayer.repository.PlanTemplateRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import br.com.jcv.treinadorpro.infrastructure.config.TreinadorProConfig;
import br.com.jcv.treinadorpro.infrastructure.decoder.IPayloadLoginSocial;
import br.com.jcv.treinadorpro.infrastructure.helper.TreinadorProHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class RegisterNewPersonalTrainerGoogleServiceImpl implements RegisterNewPersonalTrainerGoogleService {

    private final TreinadorProHelper treinadorProHelper;
    private final ParameterRepository parameterRepository;
    private final PlanTemplateRepository planTemplateRepository;
    private final TreinadorProConfig config;
    private final GuardianRestClientConsumer guardianRestClientConsumer;
    private final UserRepository userRepository;
    private final ActivePersonalPlanRepository activePersonalPlanRepository;
    private final ValidateSixCodeService validateSixCodeService;
    private final LoginService loginService;

    public RegisterNewPersonalTrainerGoogleServiceImpl(TreinadorProHelper treinadorProHelper,
                                                       ParameterRepository parameterRepository,
                                                       PlanTemplateRepository planTemplateRepository,
                                                       TreinadorProConfig config,
                                                       GuardianRestClientConsumer guardianRestClientConsumer,
                                                       UserRepository userRepository,
                                                       ActivePersonalPlanRepository activePersonalPlanRepository,
                                                       ValidateSixCodeService validateSixCodeService,
                                                       LoginService loginService) {
        this.treinadorProHelper = treinadorProHelper;
        this.parameterRepository = parameterRepository;
        this.planTemplateRepository = planTemplateRepository;
        this.config = config;
        this.guardianRestClientConsumer = guardianRestClientConsumer;
        this.userRepository = userRepository;
        this.activePersonalPlanRepository = activePersonalPlanRepository;
        this.validateSixCodeService = validateSixCodeService;
        this.loginService = loginService;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<String> execute(UUID processId, IPayloadLoginSocial payload) {

        log.info("({}) Checking email {}", processId, payload.getEmail());

        treinadorProHelper.checkExistingEmail(
                RegisterRequest.builder()
                        .email(payload.getEmail())
                        .build()
        );

        log.info("({}) Your account is being created at Guardian", processId);
        CreateNewAccountRequest createNewAccountRequest = getInstanceCreateNewAccountRequest(payload);
        ControllerGenericResponse<RegisterResponse> accountGuardianResponse = guardianRestClientConsumer.createNewAccount(createNewAccountRequest);

        log.info("({}) Creating local account", processId);
        User userSaved = userRepository.save(getInstanceUser(payload, accountGuardianResponse));

        log.info("({}) Creating FREEMIUM plan for personal trainer", processId);
        activePersonalPlanRepository.save(Objects.requireNonNull(getActivePersonalPlan(userSaved)));

        log.info("({}) Turn on personal trainer account :: Status PENDING to ACTIVE", processId);
        validateSixCodeService.execute(
                processId,
                ValidateSixCodeRequest.builder()
                        .externalAppUUID(config.getApiKeyUUID())
                        .externalUserUUID(userSaved.getUuidId())
                        .requiredCode(accountGuardianResponse.getObjectResponse().getCode())
                        .build()
        );

        log.info("({}) performing login account :: recovering guardian session", processId);
        ControllerGenericResponse<String> guardianSession = loginService.execute(processId,
                LoginRequest.builder()
                        .applicationExternalUUID(config.getApiKeyUUID())
                        .codePass(createNewAccountRequest.getPasswd())
                        .email(userSaved.getEmail())
                        .build()
        );

        log.info("({}) returning guardian session token", processId);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1647",
                accountGuardianResponse.getResponse().getMensagem(),
                guardianSession.getObjectResponse()

        );
    }

    private ActivePersonalPlan getActivePersonalPlan(User userSaved) {
        PlanTemplate planTemplate = getFreemiumPlanTemplate();
        ActivePersonalPlan activePersonalPlan = ActivePersonalPlan.builder()
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
        activePersonalPlan.setPersonalUser(userSaved);
        return activePersonalPlan;
    }

    private PlanTemplate getFreemiumPlanTemplate() {
        Long freemiumPlanId = Long.valueOf(parameterRepository.findByKeytag("FREEMIUM_PLAN_ID")
                .orElseThrow(this::freemiumKeytagNotFound)
                .getValuetag());

        return planTemplateRepository.findById(freemiumPlanId)
                .orElseThrow(this::invalidFreemiumId);

    }

    private CommoditieBaseException invalidFreemiumId() {
        return new CommoditieBaseException("Invalid Freemium Id.", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1610");
    }

    private CommoditieBaseException freemiumKeytagNotFound() {
        return new CommoditieBaseException("Freemium Keytag not found.", HttpStatus.UNPROCESSABLE_ENTITY, "MSG-1605");
    }


    private CreateNewAccountRequest getInstanceCreateNewAccountRequest(IPayloadLoginSocial payload) {
        final String pwd = UUID.randomUUID().toString();
        CreateNewAccountRequest createNewAccountRequest = new CreateNewAccountRequest();
        createNewAccountRequest.setEmail(payload.getEmail());
        createNewAccountRequest.setPasswd(pwd);
        createNewAccountRequest.setPasswdCheck(pwd);
        createNewAccountRequest.setExternalApplicationUUID(config.getApiKeyUUID());
        createNewAccountRequest.setName(payload.getName().trim());
        return createNewAccountRequest;
    }

    private User getInstanceUser(IPayloadLoginSocial payload, ControllerGenericResponse<RegisterResponse> accountGuardianResponse) {
        User user = new User();
        user.setName(payload.getName());
        user.setEmail(payload.getEmail());
        user.setCellphone("+00 00 00000 0000");
        user.setBirthday(LocalDate.now());
        user.setUrlPhotoProfile(payload.getPicture());
        user.setUuidId(UUID.randomUUID());
        user.setUserProfile(UserProfileEnum.PERSONAL_TRAINER);
        user.setStatus(StatusEnum.P);
        user.setMasterLanguage(MasterLanguageEnum.PT_BR.getLanguage());
        user.setGuardianIntegrationUUID(accountGuardianResponse.getObjectResponse().getExternalUserId());
        user.setLastLogin(LocalDateTime.now());
        user.setProvider(LoginSocialProviderEnum.google);
        user.setIdGoogle(payload.getSub());
        return user;
    }
}
