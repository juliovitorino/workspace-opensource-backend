package br.com.jcv.treinadorpro.corebusiness.userpacktraining;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.repository.UserWorkoutCalendarRepository;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeleteWorkoutCalendarServiceImpl implements DeleteWorkoutCalendarService{

    private final UserWorkoutCalendarRepository userWorkoutCalendarRepository;

    public DeleteWorkoutCalendarServiceImpl(UserWorkoutCalendarRepository userWorkoutCalendarRepository) {
        this.userWorkoutCalendarRepository = userWorkoutCalendarRepository;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, List<UUID> uuids) {
        userWorkoutCalendarRepository.deleteByExternalId(uuids);
        return ControllerGenericResponseHelper.getInstance(
                "MSG-0001",
                "UserWorkoutCalendar have been deleted.",
                 Boolean.TRUE
        );
    }
}
