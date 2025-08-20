package br.com.jcv.treinadorpro.adapter.v1.business.controller;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corebusiness.usecases.BookingTrainingService;
import br.com.jcv.treinadorpro.corebusiness.usecases.DeleteTrainingSessionService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindAllTrainingSessionCalendarService;
import br.com.jcv.treinadorpro.corebusiness.usecases.FindMostRecentTrainingSessionService;
import br.com.jcv.treinadorpro.corebusiness.usecases.MoveBookingService;
import br.com.jcv.treinadorpro.corebusiness.usecases.SaveTrainingSessionService;
import br.com.jcv.treinadorpro.corelayer.request.BookingDTO;
import br.com.jcv.treinadorpro.corelayer.request.DeleteTrainingSessionRequest;
import br.com.jcv.treinadorpro.corelayer.request.FindAllTrainingSessionCalendarRequest;
import br.com.jcv.treinadorpro.corelayer.request.MoveBookingRequest;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/business/training/session")
public class TrainingSessionController {

    private final SaveTrainingSessionService saveTrainingSessionService;
    private final FindMostRecentTrainingSessionService findMostRecentTrainingSessionService;
    private final BookingTrainingService bookingTrainingService;
    private final FindAllTrainingSessionCalendarService findAllTrainingSessionCalendarService;
    private final DeleteTrainingSessionService deleteTrainingSessionService;
    private final MoveBookingService moveBookingService;

    public TrainingSessionController(SaveTrainingSessionService saveTrainingSessionService,
                                     FindMostRecentTrainingSessionService findMostRecentTrainingSessionService,
                                     BookingTrainingService bookingTrainingService,
                                     FindAllTrainingSessionCalendarService findAllTrainingSessionCalendarService,
                                     DeleteTrainingSessionService deleteTrainingSessionService,
                                     MoveBookingService moveBookingService) {
        this.saveTrainingSessionService = saveTrainingSessionService;
        this.findMostRecentTrainingSessionService = findMostRecentTrainingSessionService;
        this.bookingTrainingService = bookingTrainingService;
        this.findAllTrainingSessionCalendarService = findAllTrainingSessionCalendarService;
        this.deleteTrainingSessionService = deleteTrainingSessionService;
        this.moveBookingService = moveBookingService;
    }

    @PostMapping
    public ResponseEntity<ControllerGenericResponse<Boolean>> saveTrainingSession(@RequestBody TrainingSessionRequest trainingSessionRequest){
        return ResponseEntity.ok(saveTrainingSessionService.execute(UUID.randomUUID(), trainingSessionRequest));

    }

    @PostMapping("/booking")
    public ResponseEntity<ControllerGenericResponse<Boolean>> bookingTrainingSession(@RequestBody BookingDTO bookingDTO){
        return ResponseEntity.ok(bookingTrainingService.execute(UUID.randomUUID(), bookingDTO));

    }

    @GetMapping("/{externalId}")
    public ResponseEntity<ControllerGenericResponse<TrainingSessionRequest>> findMostRecentTrainingSessionService(@PathVariable("externalId") UUID contractExternalId){
        return ResponseEntity.ok(findMostRecentTrainingSessionService.execute(UUID.randomUUID(), contractExternalId));
    }

    @PostMapping("/calendar")
    public ResponseEntity<ControllerGenericResponse<List<TrainingSessionRequest>>> findTrainingSessionCalendar(@RequestBody FindAllTrainingSessionCalendarRequest request){
        return ResponseEntity.ok(findAllTrainingSessionCalendarService.execute(UUID.randomUUID(), request));
    }

    @DeleteMapping("/{contractExternalId}/{trainingSessionExternalId}")
    public ResponseEntity<ControllerGenericResponse<Boolean>> deleteTerainingSession(
            @PathVariable("contractExternalId") UUID contractExternalId,
            @PathVariable("trainingSessionExternalId") UUID trainingSessionExternalId
    ){
        return ResponseEntity.ok(deleteTrainingSessionService.execute(UUID.randomUUID(),
                DeleteTrainingSessionRequest.builder()
                        .contractExternalId(contractExternalId)
                        .trainingSessionExternalId(trainingSessionExternalId)
                        .build())
        );
    }

    @PatchMapping("booking/{contractExternalId}/{trainingSessionExternalId}/move/{newBooking}")
    public ResponseEntity<ControllerGenericResponse<Boolean>> moveBooking(
            @PathVariable("contractExternalId") UUID contractExternalId,
            @PathVariable("trainingSessionExternalId") UUID trainingSessionExternalId,
            @PathVariable("newBooking") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newBookingDate
    ){
        return ResponseEntity.ok(moveBookingService.execute(UUID.randomUUID(),
                MoveBookingRequest.builder()
                        .contractExternalId(contractExternalId)
                        .trainingSessionExternalId(trainingSessionExternalId)
                        .newBookingDate(newBookingDate)
                        .build())
        );
    }
}
