package br.com.jcv.treinadorpro.corebusiness.modality;

import br.com.jcv.treinadorpro.corelayer.repository.ModalityRepository;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllModalityServiceImpl implements FindAllModalityService{

    private final ModalityRepository modalityRepository;

    public FindAllModalityServiceImpl(ModalityRepository modalityRepository) {
        this.modalityRepository = modalityRepository;
    }

    @Override
    public List<ModalityResponse> execute(UUID processId, Boolean aBoolean) {
        return modalityRepository.findAll()
                .stream()
                .map(MapperServiceHelper::toResponse)
                .collect(Collectors.toList());    }
}
