package br.com.jcv.treinadorpro.corelayer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModalityDTO implements Serializable {
    private Long id;
    private String namePt;
    private String nameEn;
    private String nameEs;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ModalityExerciseDTO> exercises;
}
