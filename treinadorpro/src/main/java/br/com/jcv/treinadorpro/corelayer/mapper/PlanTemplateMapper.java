package br.com.jcv.treinadorpro.corelayer.mapper;

import br.com.jcv.treinadorpro.corelayer.dto.PlanTemplateDTO;
import br.com.jcv.treinadorpro.corelayer.model.PlanTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlanTemplateMapper {

    private final ModelMapper modelMapper;

    public PlanTemplateMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PlanTemplateDTO toDTO(PlanTemplate planTemplate){
        return modelMapper.map(planTemplate, PlanTemplateDTO.class);
    }

    public PlanTemplate toEntity(PlanTemplateDTO planTemplateDTO){
        return modelMapper.map(planTemplateDTO, PlanTemplate.class);
    }
}
