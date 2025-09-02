package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.users.GetLoggedUserService;
import br.com.jcv.treinadorpro.corelayer.repository.ContractRepository;
import br.com.jcv.treinadorpro.corelayer.request.BookingDTO;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class BookingTrainingServiceImpl implements BookingTrainingService {

    private final SaveTrainingSessionService saveTrainingSessionService;
    private final GetLoggedUserService getLoggedUserService;
    private final ContractRepository contractRepository;

    public BookingTrainingServiceImpl(SaveTrainingSessionService saveTrainingSessionService,
                                      GetLoggedUserService getLoggedUserService, ContractRepository contractRepository) {
        this.saveTrainingSessionService = saveTrainingSessionService;
        this.getLoggedUserService = getLoggedUserService;
        this.contractRepository = contractRepository;
    }

    @Override
    @Transactional
    public ControllerGenericResponse<Boolean> execute(UUID processId, BookingDTO booking) {

        PersonalTrainerResponse trainer = getLoggedUserService.execute(processId);
        contractRepository.findByExternalIdAndPersonalId(booking.getContractExternalId(), trainer.getId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid Contract", HttpStatus.BAD_REQUEST, "MSG-1005"));


        booking.getBookingList().forEach(bookingItem -> {
            booking.getTrainingSession().setBooking(bookingItem);
            booking.getTrainingSession().setProgressStatus("BOOKING");
            booking.getTrainingSession().setSyncStatus("NOT_STARTED");
            booking.getTrainingSession().setStartAt(bookingItem);
            booking.getTrainingSession().setFinishedAt(bookingItem);
            saveTrainingSessionService.execute(processId, booking.getTrainingSession());
        });
        return ControllerGenericResponseHelper.getInstance(
                "MSG-1008",
                "Your training session has been saved",
                Boolean.TRUE
        );
    }
}
