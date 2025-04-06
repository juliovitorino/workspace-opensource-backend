package br.com.jcv.treinadorpro.corelayer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserWorkoutCalendarDTO implements Serializable {

    private Long id;
    private Long userPackTrainingId;
    private Long modalityExerciseId;
    private LocalDate trainingDate;
    private String startTime;
    private String endTime;
    private String execution;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
