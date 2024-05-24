package br.com.jcv.bei.adapter.v1.business.loadeconomicindexdata;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.bei.corebusiness.loadeconomicindexdata.LoadEconomicIndexDataSerieBacenBusinessService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/business/economic-index/load")
@RequiredArgsConstructor
public class LoadEconomicIndexDataSerieBacenController {

    private final LoadEconomicIndexDataSerieBacenBusinessService loadEconomicIndexDataSerieBacenBusinessService;

    @GetMapping
    public ResponseEntity<Boolean> load() {
        return ResponseEntity.ok(loadEconomicIndexDataSerieBacenBusinessService.execute(UUID.randomUUID(),Boolean.TRUE));
    }
}
