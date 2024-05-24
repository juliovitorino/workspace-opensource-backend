package br.com.jcv.bei.infrastructure.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jcv.bei.infrastructure.config.BacenEconomicIndexConfig;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.bei.infrastructure.response.BacenSerieItemResponse;
import br.com.jcv.commons.library.http.HttpURLConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EconomicIndexHelperService {
    final static String TAG = "\\{codigo-bacen-serie\\}";

    private final HttpURLConnection httpURLConnection;
    private final BacenEconomicIndexConfig config;

    public List<BacenSerieItemResponse> acessarSerieHistoricaBacen(EconomicIndexDTO indicadorEconomico) {
        log.info("acessarSerieHistoricaBacen :: Processando serie {} ", indicadorEconomico.getBacenSerieCode());
        String url = config.getIndicadoresEconomicosUrlApiBacen().replaceAll(TAG,indicadorEconomico.getBacenSerieCode());
        List<BacenSerieItemResponse> dadosBacenSerie ;

        try {
            String response = httpURLConnection.sendGET(url);
            dadosBacenSerie = new ObjectMapper().readValue(response, new TypeReference<List<BacenSerieItemResponse>>() {});

        } catch (JsonProcessingException e) {
            log.error("Erro ao converter objeto retornado pelo BACEN. Ignorando série {}. Verifique url = {}",
                    indicadorEconomico.getBacenSerieCode(),
                    url);
            dadosBacenSerie = new ArrayList<>();
        } catch (IOException e) {
            log.error("Erro durante a comunicação com BACEN. Ignorando série {}. Verifique url = {}",
                    indicadorEconomico.getBacenSerieCode(),
                    url);
            dadosBacenSerie = new ArrayList<>();
        }
        return dadosBacenSerie;
    }

}
