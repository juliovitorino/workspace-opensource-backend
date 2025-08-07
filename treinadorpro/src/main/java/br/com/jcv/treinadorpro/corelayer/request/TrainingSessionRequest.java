package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutPlan;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionRequest {

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("contract")
    private ContractResponse contract;

    @JsonProperty("booking")
    private LocalDate booking;

    @JsonProperty("startedAt")
    private LocalDateTime startAt;

    @JsonProperty("finishedAt")
    private LocalDateTime finishedAt;

    @JsonProperty("elapsedTime")
    private Integer elapsedTime;

    @JsonProperty("progressStatus")
    private String progressStatus;

    @JsonProperty("syncStatus")
    private String syncStatus;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("userWorkoutPlanList")
    private List<UserWorkoutPlanRequest> userWorkoutPlanList;
}
