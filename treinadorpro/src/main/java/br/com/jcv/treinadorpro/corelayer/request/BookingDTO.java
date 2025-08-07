package br.com.jcv.treinadorpro.corelayer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookingDTO implements Serializable {

    @JsonProperty("contractExternalId")
    private UUID contractExternalId;

    @JsonProperty("bookingList")
    private List<LocalDate> bookingList;

    @JsonProperty("trainingSession")
    private TrainingSessionRequest trainingSession;
}
