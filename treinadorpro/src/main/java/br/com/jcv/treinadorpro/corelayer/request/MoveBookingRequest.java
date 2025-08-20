package br.com.jcv.treinadorpro.corelayer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveBookingRequest implements Serializable {
    private UUID contractExternalId;
    private UUID trainingSessionExternalId;
    private LocalDate newBookingDate;
}
