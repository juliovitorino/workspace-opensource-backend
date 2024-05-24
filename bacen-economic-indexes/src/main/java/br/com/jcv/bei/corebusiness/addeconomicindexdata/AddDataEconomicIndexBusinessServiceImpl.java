package br.com.jcv.bei.corebusiness.addeconomicindexdata;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jcv.bei.adapter.v1.business.addeconomicindexdata.AddDataEconomicIndexRequest;
import br.com.jcv.bei.corelayer.service.EconomicIndexDataService;
import br.com.jcv.bei.corelayer.service.EconomicIndexService;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDataDTO;
import br.com.jcv.bei.infrastructure.helper.BeiHelperService;
import br.com.jcv.bei.infrastructure.response.BaseResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddDataEconomicIndexBusinessServiceImpl implements AddDataEconomicIndexBusinessService {

    private final EconomicIndexService economicIndexService;
    private final EconomicIndexDataService economicIndexDataService;
    private final BeiHelperService beiHelperService;

    @Override
    public BaseResponse execute(UUID processId, AddDataEconomicIndexRequest addDataEconomicIndexRequest) {
        EconomicIndexDTO economicIndexByEconomicIndexAndStatus =
                economicIndexService.findEconomicIndexByEconomicIndexAndStatus(
                        addDataEconomicIndexRequest.getEconomicIndex(),
                        GenericStatusEnums.ATIVO.getShortValue()
                );

        EconomicIndexDataDTO saved = economicIndexDataService.salvar(
                getEntityInstance(economicIndexByEconomicIndexAndStatus, addDataEconomicIndexRequest)
        );
        economicIndexDataService.updateStatusById(saved.getId(), GenericStatusEnums.ATIVO.getShortValue());
        return beiHelperService.getResponse(
                "BEI-0001",
                "The data economic index named "+addDataEconomicIndexRequest.getEconomicIndex()+ " has been added."
        );
    }

    private EconomicIndexDataDTO getEntityInstance(EconomicIndexDTO economicIndexDTO, AddDataEconomicIndexRequest addDataEconomicIndexRequest) {
        return EconomicIndexDataDTO.builder()
                .economicIndexId(economicIndexDTO.getId())
                .indexDate(addDataEconomicIndexRequest.getIndexDate())
                .indexValue(addDataEconomicIndexRequest.getIndexValue())
                .build();
    }
}
