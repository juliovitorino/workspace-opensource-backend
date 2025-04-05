package br.com.jcv.treinadorpro.corelayer.mapper;

import br.com.jcv.treinadorpro.corelayer.dto.UserPackTrainingDTO;
import br.com.jcv.treinadorpro.corelayer.model.UserPackTraining;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserPackTrainingMapper {

    private final ModelMapper modelMapper;

    public UserPackTrainingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserPackTrainingDTO toDTO(UserPackTraining entity) {
        return modelMapper.map(entity, UserPackTrainingDTO.class);
    }

    public UserPackTraining toEntity(UserPackTrainingDTO dto) {
        return modelMapper.map(dto, UserPackTraining.class);
    }
}
