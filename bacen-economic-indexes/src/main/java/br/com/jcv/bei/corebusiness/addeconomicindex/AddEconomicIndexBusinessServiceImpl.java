package br.com.jcv.bei.corebusiness.addeconomicindex;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jcv.bei.adapter.v1.business.controller.addeconomicindex.AddEconomicIndexRequest;
import br.com.jcv.bei.corelayer.service.EconomicIndexService;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.bei.infrastructure.helper.BeiHelperService;
import br.com.jcv.bei.infrastructure.response.BaseResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddEconomicIndexBusinessServiceImpl implements AddEconomicIndexBusinessService {

    private final EconomicIndexService economicIndexService;
    private final BeiHelperService beiHelperService;

    @Override
    public BaseResponse execute(UUID processId, AddEconomicIndexRequest addEconomicIndexRequest) {
        EconomicIndexDTO saved = economicIndexService.salvar(getEntityInstance(addEconomicIndexRequest));
        economicIndexService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return beiHelperService.getResponse(
                "BEI-0001",
                "The economic index named "+addEconomicIndexRequest.getEconomicIndex()+"has been included"
        );
    }

    private EconomicIndexDTO getEntityInstance(AddEconomicIndexRequest addEconomicIndexRequest) {
        return EconomicIndexDTO.builder()
                .economicIndex(addEconomicIndexRequest.getEconomicIndex())
                .bacenSerieCode(addEconomicIndexRequest.getBacenSerieCode())
                .build();
    }
}
