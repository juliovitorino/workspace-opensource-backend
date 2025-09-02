package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.helper.TreinadorProHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllStudentsFromTrainerServiceImpl implements FindAllStudentsFromTrainerService {

    private final ContractRepository contractRepository;
    private final TreinadorProHelper treinadorProHelper;

    public FindAllStudentsFromTrainerServiceImpl(ContractRepository contractRepository,
                                                 TreinadorProHelper treinadorProHelper) {
        this.contractRepository = contractRepository;
        this.treinadorProHelper = treinadorProHelper;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<List<StudentsFromTrainerResponse>> execute(UUID processId, UUID personalTrainerExternalId) {

        User trainerUser = treinadorProHelper.checkActivePersonalTrainerUUID(personalTrainerExternalId);

        List<User> distinctStudentsByPersonalTrainerTrainingPacks =
                contractRepository.findDistinctStudentsByPersonalTrainerTrainingPacks(trainerUser.getId());

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1116",
                "All Students were retireved successfully",
                distinctStudentsByPersonalTrainerTrainingPacks.stream()
                        .map(MapperServiceHelper::toResponseStudents)
                        .collect(Collectors.toList())
        );
    }


}
