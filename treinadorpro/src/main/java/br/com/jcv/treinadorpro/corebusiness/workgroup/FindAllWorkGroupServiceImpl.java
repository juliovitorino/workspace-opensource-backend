package br.com.jcv.treinadorpro.corebusiness.workgroup;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.repository.WorkGroupRepository;
import br.com.jcv.treinadorpro.corelayer.response.WorkGroupResponse;
import br.com.jcv.treinadorpro.corelayer.service.MapperServiceHelper;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FindAllWorkGroupServiceImpl implements FindAllWorkGroupService{

    private final WorkGroupRepository workGroupRepository;

    public FindAllWorkGroupServiceImpl(WorkGroupRepository workGroupRepository) {
        this.workGroupRepository = workGroupRepository;
    }

    @Override
    public ControllerGenericResponse<List<WorkGroupResponse>> execute(UUID processId, Boolean aBoolean) {
        return ControllerGenericResponseHelper.getInstance(
                "MSG-0916",
                "All Workgroup were retrieved successfully",
                workGroupRepository.findAll()
                        .stream()
                        .map(MapperServiceHelper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
