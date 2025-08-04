package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.ExecutionMethodEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.ExerciseResponse;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import br.com.jcv.treinadorpro.corelayer.response.UserTrainingExecutionSetResponse;
import br.com.jcv.treinadorpro.corelayer.response.WorkGroupResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkoutPlanRequest implements Serializable {

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("contract")
    private ContractResponse contract;

    @JsonProperty("modality")
    private ModalityResponse modality;

    @JsonProperty("goal")
    private GoalResponse goal;

    @JsonProperty("program")
    private ProgramResponse program;

    @JsonProperty("workGroup")
    private WorkGroupResponse workGroup;

    @JsonProperty("exercise")
    private ExerciseResponse exercise;

    @JsonProperty("customExercise")
    private String customExercise;

    @JsonProperty("customProgram")
    private String customProgram;

    @JsonProperty("executionMethod")
    private ExecutionMethodEnum executionMethod;

    @JsonProperty("qtySeries")
    private Integer qtySeries;

    @JsonProperty("qtyReps")
    private String qtyReps;

    @JsonProperty("execution")
    private String execution;

    @JsonProperty("executionTime")
    private String executionTime;

    @JsonProperty("restTime")
    private String restTime;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty("obs")
    private String obs;

    @JsonProperty("control")
    private String control;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("userExecutionSetList")
    private List<UserTrainingExecutionSetResponse> userExecutionSetList;

}
