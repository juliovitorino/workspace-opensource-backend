package br.com.jcv.treinadorpro.corelayer.response;

import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.request.UserWorkoutPlanRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingExecutionSetResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("userWorkPlan")
    private UserWorkoutPlanRequest userWorkPlan;

    @JsonProperty("startedAt")
    private LocalDateTime startedAt;

    @JsonProperty("finishedAt")
    private LocalDateTime finishedAt;

    @JsonProperty("setNumber")
    private Integer setNumber;

    @JsonProperty("reps")
    private Integer reps;

    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("weightUnit")
    private String weightUnit;

    @JsonProperty("elapsedTime")
    private Integer elapsedTime;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
