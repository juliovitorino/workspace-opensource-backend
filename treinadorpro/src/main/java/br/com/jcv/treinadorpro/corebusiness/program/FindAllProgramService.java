package br.com.jcv.treinadorpro.corebusiness.program;

import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;

import java.util.List;

public interface FindAllProgramService extends BusinessService<Boolean, List<ProgramResponse>> {
}
