package br.com.jcv.bei.corebusiness.loadeconomicindexdata;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jcv.bei.adapter.v1.business.addeconomicindexdata.AddDataEconomicIndexRequest;
import br.com.jcv.bei.corebusiness.addeconomicindexdata.AddDataEconomicIndexBusinessService;
import br.com.jcv.bei.corelayer.repository.EconomicIndexRepository;
import br.com.jcv.bei.corelayer.service.EconomicIndexDataService;
import br.com.jcv.bei.corelayer.service.EconomicIndexService;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDataDTO;
import br.com.jcv.bei.infrastructure.helper.EconomicIndexHelperService;
import br.com.jcv.bei.infrastructure.response.BacenSerieItemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoadEconomicIndexDataSerieBacenBusinessServiceImpl implements LoadEconomicIndexDataSerieBacenBusinessService {

    public static final String PATTERN_BACEN_DD_MM_YYYY = "dd/MM/yyyy";

    private final EconomicIndexService economicIndexService;
    private final EconomicIndexDataService economicIndexDataService;
    private final EconomicIndexRepository economicIndexRepository;
    private final EconomicIndexHelperService indicadoresEconomicosHelperService;
    private final AddDataEconomicIndexBusinessService addDataEconomicIndexBusinessService;

    @Override
    public Boolean execute(UUID processId, Boolean aBoolean) {
        List<EconomicIndexDTO> economicIndexList = economicIndexRepository.findByLastDateValueNullAndStatus(GenericStatusEnums.ATIVO.getShortValue())
                .stream()
                .map(economicIndexService::toDTO)
                .collect(Collectors.toList());
        if(economicIndexList.isEmpty()) {
            log.info("There is no data serie to process in full mode");
            return Boolean.FALSE;
        }
        economicIndexList.forEach(this::processEconomicIndexDataSerie);
        log.info("execute:: process for load new economic data index has been finished.");

        return Boolean.TRUE;
    }

    private void processEconomicIndexDataSerie(EconomicIndexDTO economicIndexDTO) {
        log.info("processEconomicIndexDataSerie :: full charge {} ", economicIndexDTO.getBacenSerieCode());
        List<BacenSerieItemResponse> dadosBacenSerie =
                indicadoresEconomicosHelperService.acessarSerieHistoricaBacen(economicIndexDTO);

        dadosBacenSerie.forEach(
                bacenSerieItemResponse -> salvarIndicador(bacenSerieItemResponse, economicIndexDTO )
        );
    }

    private void salvarIndicador(BacenSerieItemResponse bacenSerieItemResponse, EconomicIndexDTO indicadorEconomico) {
        if(Objects.isNull(indicadorEconomico.getLastDateValue())) {
            indicadorEconomico.setLastDateValue(LocalDate.of(1900,1,1));
        }
        AddDataEconomicIndexRequest addDataEconomicIndexRequest = AddDataEconomicIndexRequest.builder()
                .economicIndex(indicadorEconomico.getEconomicIndex())
                .indexDate(DateUtility.toLocalDate(bacenSerieItemResponse.getData(), PATTERN_BACEN_DD_MM_YYYY))
                .indexValue(bacenSerieItemResponse.getValor())
                .build();
//        EconomicIndexDataDTO indicadorEconomicoDados = EconomicIndexDataDTO.builder()
//                .economicIndexId(indicadorEconomico.getId())
//                .indexDate(DateUtility.toLocalDate(bacenSerieItemResponse.getData(), PATTERN_BACEN_DD_MM_YYYY))
//                .indexValue(bacenSerieItemResponse.getValor())
//                .build();
        addDataEconomicIndexBusinessService.execute(UUID.randomUUID(),addDataEconomicIndexRequest);
//        economicIndexDataService.salvar(indicadorEconomicoDados);
        LocalDate dataEmProcessamento = DateUtility.toLocalDate(bacenSerieItemResponse.getData(), PATTERN_BACEN_DD_MM_YYYY);

        if(dataEmProcessamento.isAfter(indicadorEconomico.getLastDateValue())) {
            economicIndexService.updateLastDateValueById(indicadorEconomico.getId(),dataEmProcessamento);
            indicadorEconomico.setLastDateValue(dataEmProcessamento);
        }
    }


}
