package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.ExecutionMethodEnum;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class EditPersonalTrainerProgramRequest {
    private Long id;
    private Long workGroupId;
    private Long exerciseId;
    private UUID personalUserUUID;
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
