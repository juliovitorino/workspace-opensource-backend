package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.ExecutionMethodEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PersonalTrainerProgramRequest {
    private UUID personalUserUUID;
    private Long modalityId;
    private Long workGroupId;
    private Long goalId;
    private Long exerciseId;
    private Long programId;
    private String customExercise;
    private String customProgram;
    private ExecutionMethodEnum executionMethod;
    private String execution;
    private String executionTime;
    private String restTime;
    private Integer weight;
    private String weightUnit;
    private String comments;
    private String obs;
}
