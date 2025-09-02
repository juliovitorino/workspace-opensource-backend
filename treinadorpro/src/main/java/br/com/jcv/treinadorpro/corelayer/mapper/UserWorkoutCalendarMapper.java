package br.com.jcv.treinadorpro.corelayer.mapper;

import br.com.jcv.treinadorpro.corelayer.dto.UserWorkoutCalendarDTO;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutPlan;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserWorkoutCalendarMapper {

    private final ModelMapper modelMapper;

    public UserWorkoutCalendarMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserWorkoutCalendarDTO toDTO(UserWorkoutPlan entity) {
        return modelMapper.map(entity, UserWorkoutCalendarDTO.class);
    }

    public UserWorkoutPlan toEntity(UserWorkoutCalendarDTO dto) {
        return modelMapper.map(dto, UserWorkoutPlan.class);
    }
}
