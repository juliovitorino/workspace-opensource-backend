package br.com.jcv.treinadorpro.corelayer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindLastLoadExerciseResponse implements Serializable
{
    @JsonProperty("setNumber")
    private Long setNumber;

    @JsonProperty("reps")
    private Long reps;

    @JsonProperty("weight")
    private Long weight;
}
