package br.com.jcv.treinadorpro.corelayer.mapper;

import br.com.jcv.treinadorpro.corelayer.dto.ModalityExerciseDTO;
import br.com.jcv.treinadorpro.corelayer.model.ModalityExercise;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModalityExerciseMapper {

    private final ModelMapper modelMapper;

    public ModalityExerciseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ModalityExerciseDTO toDTO(ModalityExercise entity) {
        return modelMapper.map(entity, ModalityExerciseDTO.class);
    }

    public ModalityExercise toEntity(ModalityExerciseDTO dto) {
        return modelMapper.map(dto, ModalityExercise.class);
    }
}
