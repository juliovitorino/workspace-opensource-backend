package br.com.jcv.treinadorpro.corelayer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindMostRecentTrainingSessionRequest {
    private UUID contractExternalId;
    private String progressStatus;
    private LocalDateTime bookingDate;
}
