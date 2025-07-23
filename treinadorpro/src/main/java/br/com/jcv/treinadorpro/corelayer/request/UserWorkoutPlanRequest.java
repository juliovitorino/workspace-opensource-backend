package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.ExecutionMethodEnum;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.ExerciseResponse;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import br.com.jcv.treinadorpro.corelayer.response.WorkGroupResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkoutPlanRequest implements Serializable {

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

//    @JsonProperty("weight")
//    private Integer weight;
//
//    @JsonProperty("weightUnit")
//    private String weightUnit;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty("obs")
    private String obs;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

}
