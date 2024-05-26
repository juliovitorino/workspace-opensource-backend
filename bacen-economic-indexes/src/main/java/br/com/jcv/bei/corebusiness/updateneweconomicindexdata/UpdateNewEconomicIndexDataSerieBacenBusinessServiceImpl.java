package br.com.jcv.bei.corebusiness.updateneweconomicindexdata;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jcv.bei.adapter.v1.business.controller.addeconomicindexdata.AddDataEconomicIndexRequest;
import br.com.jcv.bei.corebusiness.addeconomicindexdata.AddDataEconomicIndexBusinessService;
import br.com.jcv.bei.corebusiness.loadeconomicindexdata.LoadEconomicIndexDataSerieBacenBusinessService;
import br.com.jcv.bei.corelayer.repository.EconomicIndexRepository;
import br.com.jcv.bei.corelayer.service.EconomicIndexService;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.bei.infrastructure.enums.EconomicIndexStatusProcessEnum;
import br.com.jcv.bei.infrastructure.helper.EconomicIndexHelperService;
import br.com.jcv.bei.infrastructure.response.BacenSerieItemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateNewEconomicIndexDataSerieBacenBusinessServiceImpl implements UpdateNewEconomicIndexDataSerieBacenBusinessService {

    public static final String PATTERN_BACEN_DD_MM_YYYY = "dd/MM/yyyy";

    private final EconomicIndexService economicIndexService;
    private final EconomicIndexRepository economicIndexRepository;
    private final EconomicIndexHelperService indicadoresEconomicosHelperService;
    private final AddDataEconomicIndexBusinessService addDataEconomicIndexBusinessService;

    @Override
    public Boolean execute(UUID processId, Boolean aBoolean) {
        List<EconomicIndexDTO> economicIndexList = economicIndexRepository.findByLastDateValueNotNullAndStatusAndStatusProcess(
                GenericStatusEnums.ATIVO.getShortValue(), EconomicIndexStatusProcessEnum.DONE)
                .stream()
                .map(economicIndexService::toDTO)
                .collect(Collectors.toList());
        if(economicIndexList.isEmpty()) {
            log.info("There is no new data serie to process");
            return Boolean.FALSE;
        }

        economicIndexList.forEach(
                economicIndex -> economicIndexService.updateStatusProcessById(economicIndex.getId(), EconomicIndexStatusProcessEnum.WAITING)
        );
        economicIndexList.forEach(this::processEconomicIndexDataSerie);
        log.info("execute:: process for update new economic data index has been finished.");

        return Boolean.TRUE;
    }

    private void processEconomicIndexDataSerie(EconomicIndexDTO economicIndexDTO) {
        log.info("processEconomicIndexDataSerie :: full charge {} ", economicIndexDTO.getBacenSerieCode());
        economicIndexService.updateStatusProcessById(economicIndexDTO.getId(), EconomicIndexStatusProcessEnum.WORKING);

        List<BacenSerieItemResponse> dadosBacenSerie =
                indicadoresEconomicosHelperService.acessarSerieHistoricaBacen(economicIndexDTO);

        List<BacenSerieItemResponse> newUpdateSeries = dadosBacenSerie.stream().filter(
                bacenSerieItemResponse -> DateUtility.toLocalDate(bacenSerieItemResponse.getData(), PATTERN_BACEN_DD_MM_YYYY).isAfter(economicIndexDTO.getLastDateValue())
        ).collect(Collectors.toList());

        if(newUpdateSeries.isEmpty()) {
            log.info("processEconomicIndexDataSerie :: No new economic index has been found.");
        }
        newUpdateSeries.forEach(newSerie -> salvarIndicador(newSerie, economicIndexDTO ));
        economicIndexService.updateStatusProcessById(economicIndexDTO.getId(), EconomicIndexStatusProcessEnum.DONE);

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

        addDataEconomicIndexBusinessService.execute(UUID.randomUUID(),addDataEconomicIndexRequest);
        LocalDate dataEmProcessamento = DateUtility.toLocalDate(bacenSerieItemResponse.getData(), PATTERN_BACEN_DD_MM_YYYY);

        if(dataEmProcessamento.isAfter(indicadorEconomico.getLastDateValue())) {
            economicIndexService.updateLastDateValueById(indicadorEconomico.getId(),dataEmProcessamento);
            indicadorEconomico.setLastDateValue(dataEmProcessamento);
        }
    }


}
