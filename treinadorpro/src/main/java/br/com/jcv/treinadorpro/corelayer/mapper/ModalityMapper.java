package br.com.jcv.treinadorpro.corelayer.mapper;

import br.com.jcv.treinadorpro.corelayer.dto.ModalityDTO;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModalityMapper {

    private final ModelMapper modelMapper;

    public ModalityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ModalityDTO toDTO(Modality entity) {
        return modelMapper.map(entity, ModalityDTO.class);
    }

    public Modality toEntity(ModalityDTO dto) {
        return modelMapper.map(dto, Modality.class);
    }
}
