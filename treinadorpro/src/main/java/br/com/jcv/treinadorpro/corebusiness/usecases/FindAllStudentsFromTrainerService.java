package br.com.jcv.treinadorpro.corebusiness.usecases;

import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;

import java.util.List;
import java.util.UUID;

public interface FindAllStudentsFromTrainerService extends BusinessService<UUID, ControllerGenericResponse<List<StudentsFromTrainerResponse>>> {
}
