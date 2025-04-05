package br.com.jcv.treinadorpro.corelayer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModalityExerciseDTO {
    private Long id;
    private ModalityDTO modality;
    private ExerciseDTO exercise;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
