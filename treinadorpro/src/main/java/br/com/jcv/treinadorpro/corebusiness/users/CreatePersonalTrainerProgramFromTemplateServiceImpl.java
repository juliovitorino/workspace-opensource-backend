package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.PersonalTrainerProgram;
import br.com.jcv.treinadorpro.corelayer.model.ProgramTemplate;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.PersonalTrainerProgramRepository;
import br.com.jcv.treinadorpro.corelayer.repository.ProgramTemplateRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.CreatePersonalTrainerProgramFromTemplateRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreatePersonalTrainerProgramFromTemplateServiceImpl extends AbstractUserService implements CreatePersonalTrainerProgramFromTemplateService{

    private final ProgramTemplateRepository programTemplateRepository;
    private final PersonalTrainerProgramRepository personalTrainerProgramRepository;
    private final ModelMapper modelMapper;
    protected CreatePersonalTrainerProgramFromTemplateServiceImpl(UserRepository userRepository,
                                                                  ProgramTemplateRepository programTemplateRepository,
                                                                  PersonalTrainerProgramRepository personalTrainerProgramRepository,
                                                                  ModelMapper modelMapper) {
        super(userRepository);
        this.programTemplateRepository = programTemplateRepository;
        this.personalTrainerProgramRepository = personalTrainerProgramRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId,
                                                      CreatePersonalTrainerProgramFromTemplateRequest request) {

        checkPersonalTrainerUUID(request.getPersonalTrainerId());
        User userByUUID = getUserByUUID(request.getPersonalTrainerId());

        List<ProgramTemplate> allProgramTemplate = programTemplateRepository.findAllProgramTemplateByVersionAndModalityAndGoalAndProgramList(
                request.getVersion(),
                request.getModalityId(),
                request.getGoalId(),
                request.getProgramList()
        );

        List<PersonalTrainerProgram> personalTrainerProgramList = allProgramTemplate.stream()
                .map(item -> createPersonalProgram(userByUUID, item))
                .collect(Collectors.toList());
        personalTrainerProgramRepository.saveAll(personalTrainerProgramList);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-0001","Personal Trainer Program has been created succesfully.", Boolean.TRUE);
    }

    private PersonalTrainerProgram createPersonalProgram(User userByUUID, ProgramTemplate item) {
        PersonalTrainerProgram personalTrainerProgram = modelMapper.map(item, PersonalTrainerProgram.class);
        personalTrainerProgram.setPersonalUser(userByUUID);
        return personalTrainerProgram;
    }
}
