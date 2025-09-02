package br.com.jcv.treinadorpro.corelayer.mapper;

import br.com.jcv.treinadorpro.corelayer.dto.ActivePersonalPlanDTO;
import br.com.jcv.treinadorpro.corelayer.model.ActivePersonalPlan;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ActivePersonalPlanMapper {

    private final ModelMapper modelMapper;

    public ActivePersonalPlanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ActivePersonalPlanDTO toDTO(ActivePersonalPlan activePersonalPlan){
        return modelMapper.map(activePersonalPlan, ActivePersonalPlanDTO.class);
    }

    public ActivePersonalPlan toEntity(ActivePersonalPlanDTO activePersonalPlanDTO) {
        return modelMapper.map(activePersonalPlanDTO, ActivePersonalPlan.class);
    }
}
