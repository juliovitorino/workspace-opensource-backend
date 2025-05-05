package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.ProgramTemplate;
import br.com.jcv.treinadorpro.corelayer.model.UserPackTraining;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutCalendar;
import br.com.jcv.treinadorpro.corelayer.repository.ProgramTemplateRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserPackTrainingRepository;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutCalendarRepository;
import br.com.jcv.treinadorpro.corelayer.request.CreateWorkoutCalendarServiceRequest;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CreateWorkoutCalendarFromProgramTemplateServiceImpl implements CreateWorkoutCalendarFromProgramTemplateService {

    private final UserPackTrainingRepository userPackTrainingRepository;
    private final ProgramTemplateRepository programTemplateRepository;
    private final ModelMapper modelMapper;
    private final UserWorkoutCalendarRepository userWorkoutCalendarRepository;

    public CreateWorkoutCalendarFromProgramTemplateServiceImpl(UserPackTrainingRepository userPackTrainingRepository,
                                                               ProgramTemplateRepository programTemplateRepository,
                                                               ModelMapper modelMapper,
                                                               UserWorkoutCalendarRepository userWorkoutCalendarRepository) {
        this.userPackTrainingRepository = userPackTrainingRepository;
        this.programTemplateRepository = programTemplateRepository;
        this.modelMapper = modelMapper;
        this.userWorkoutCalendarRepository = userWorkoutCalendarRepository;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, CreateWorkoutCalendarServiceRequest request) {

        UserPackTraining userPackTrainingEntity = userPackTrainingRepository.findById(request.getUserPackTrainingId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid User Pack Training", HttpStatus.BAD_REQUEST, "MSG-1511"));

        List<ProgramTemplate> allProgramTemplateByExternalId =
                programTemplateRepository.findAllByExternalId(request.getProgramTemplateExternalIdList());

        List<UserWorkoutCalendar> userWorkoutCalendarEntityToSaveList = new ArrayList<>();
        for(LocalDate dateItem : request.getTrainingDateList()) {
            log.info("Date = {}", dateItem);
            List<UserWorkoutCalendar> collect = allProgramTemplateByExternalId
                    .stream()
                    .map(programTemplate -> getEntity(dateItem, userPackTrainingEntity, programTemplate))
                    .collect(Collectors.toList());
            userWorkoutCalendarEntityToSaveList.addAll(collect);
        }
        userWorkoutCalendarEntityToSaveList.forEach(i -> {
            i.setExternalId(UUID.randomUUID());
        });

        userWorkoutCalendarRepository.saveAll(userWorkoutCalendarEntityToSaveList);

        return ControllerGenericResponseHelper.getInstance(
                "MSG-0001",
                "Your Workout calendar has been created",
                Boolean.TRUE);
    }

    private UserWorkoutCalendar getEntity(LocalDate trainingDate,
                                          UserPackTraining userWorkoutEntity,
                                          ProgramTemplate programTemplate) {
        UserWorkoutCalendar entity = UserWorkoutCalendar.builder()
                .userPackTraining(userWorkoutEntity)
                .trainingDate(trainingDate)
                .build();

        modelMapper.map(programTemplate,entity);
        return entity;

    }
}
