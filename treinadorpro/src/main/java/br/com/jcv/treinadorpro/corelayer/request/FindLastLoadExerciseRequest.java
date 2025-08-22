package br.com.jcv.treinadorpro.corelayer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindLastLoadExerciseRequest implements Serializable
{
    @JsonProperty("contractExternalId")
    @NotBlank
    @NotBlank
    private UUID contractExternalId;

    @JsonProperty("exerciseExternalId")
    private UUID exerciseExternalId;

    @JsonProperty("customExercise")
    private String customExercise;
}
