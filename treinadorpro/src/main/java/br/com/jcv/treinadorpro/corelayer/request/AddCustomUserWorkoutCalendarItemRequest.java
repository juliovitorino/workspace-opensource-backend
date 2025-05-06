package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.ExecutionMethodEnum;
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
public class AddCustomUserWorkoutCalendarItemRequest implements Serializable {

    private UUID userPackTrainingId;
    private LocalDate trainingDate;
    private Long modality;
    private Long workGroup;
    private Long goal;
    private Long exercise;
    private Long program;
    private String customExercise;
    private String customProgram;
    private ExecutionMethodEnum executionMethod;
    private Integer qtySeries;
    private Integer qtyReps;
    private String execution;
    private String executionTime;
    private String restTime;
    private Integer weight;
    private String weightUnit;
    private String comments;
    private String obs;
}
