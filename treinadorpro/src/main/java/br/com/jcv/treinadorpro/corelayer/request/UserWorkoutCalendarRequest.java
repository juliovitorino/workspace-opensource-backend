package br.com.jcv.treinadorpro.corelayer.request;

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
public class UserWorkoutCalendarRequest implements Serializable {

    private Long modalityExerciseId;
    private LocalDate trainingDate;
    private String execution;
}
