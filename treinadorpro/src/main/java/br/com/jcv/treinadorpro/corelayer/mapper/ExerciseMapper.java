package br.com.jcv.treinadorpro.corelayer.mapper;

import br.com.jcv.treinadorpro.corelayer.dto.ExerciseDTO;
import br.com.jcv.treinadorpro.corelayer.model.Exercise;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    private final ModelMapper modelMapper;

    public ExerciseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ExerciseDTO toDTO(Exercise entity) {
        return modelMapper.map(entity, ExerciseDTO.class);
    }

    public Exercise toEntity(ExerciseDTO dto) {
        return modelMapper.map(dto, Exercise.class);
    }
}
