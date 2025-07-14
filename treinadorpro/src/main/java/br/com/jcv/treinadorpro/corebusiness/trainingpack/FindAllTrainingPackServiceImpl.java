package br.com.jcv.treinadorpro.corebusiness.trainingpack;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.TrainingPackRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.PageResultRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.PageResultResponse;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllTrainingPackServiceImpl implements FindAllTrainingPackService{

    private final TrainingPackRepository trainingPackRepository;
    private final UserRepository userRepository;
    private final GetLoggedUserService getLoggedUserService;

    public FindAllTrainingPackServiceImpl(TrainingPackRepository trainingPackRepository,
                                          UserRepository userRepository,
                                          GetLoggedUserService getLoggedUserService) {
        this.trainingPackRepository = trainingPackRepository;
        this.userRepository = userRepository;
        this.getLoggedUserService = getLoggedUserService;
    }

    @Override
    public ControllerGenericResponse<PageResultResponse<TrainingPackResponse>> execute(UUID processId, PageResultRequest<UUID> request) {
        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        User user = userRepository.findByUuidId(trainer.getUuidId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Personal ID", HttpStatus.BAD_REQUEST, "MSG-1737"));

        PageResultResponse<TrainingPackResponse> response = getTrainingPackPageResultResponse(request, user);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1743",
                "Training pack list was retrieve successfully",
                response
        );
    }

    private PageResultResponse<TrainingPackResponse> getTrainingPackPageResultResponse(PageResultRequest<UUID> request, User user) {
        if(request.getPage() < 1)
            throw new CommoditieBaseException("Invalid page number", HttpStatus.BAD_REQUEST, "MSG-0912" );

        PageRequest pageRequest = PageRequest.of(request.getPage()-1, request.getSize());
        Page<TrainingPack> pageAllTrainingPack = trainingPackRepository.findAllByPersonalUserId(user.getId(), pageRequest);
        List<TrainingPackResponse> trainingPackResponseList = pageAllTrainingPack
                .getContent()
                .stream()
                .map(MapperServiceHelper::toResponse)
                .collect(Collectors.toList());

        return PageResultResponse.<TrainingPackResponse>builder()
                .totalPages(pageAllTrainingPack.getTotalPages())
                .page(pageAllTrainingPack.getNumber() + 1)
                .count(pageAllTrainingPack.getTotalElements())
                .content(trainingPackResponseList)
                .build();
    }


}
