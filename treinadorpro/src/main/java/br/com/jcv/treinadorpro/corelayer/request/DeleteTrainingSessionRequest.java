package br.com.jcv.treinadorpro.corelayer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteTrainingSessionRequest implements Serializable {

    @JsonProperty("contractExternalId")
    private UUID contractExternalId;

    @JsonProperty("trainingSessionExternalId")
    private UUID trainingSessionExternalId;
}
