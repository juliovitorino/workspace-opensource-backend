package br.com.jcv.treinadorpro.corebusiness.modality;

import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;

import java.util.List;

public interface FindAllModalityService extends BusinessService<Boolean, List<ModalityResponse>> {
}
