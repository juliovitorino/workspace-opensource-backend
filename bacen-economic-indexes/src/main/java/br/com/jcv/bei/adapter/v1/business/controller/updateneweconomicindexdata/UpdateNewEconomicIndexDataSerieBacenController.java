package br.com.jcv.bei.adapter.v1.business.controller.updateneweconomicindexdata;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.bei.corebusiness.loadeconomicindexdata.LoadEconomicIndexDataSerieBacenBusinessService;
import br.com.jcv.bei.corebusiness.updateneweconomicindexdata.UpdateNewEconomicIndexDataSerieBacenBusinessService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/business/economic-index/update-new")
@RequiredArgsConstructor
public class UpdateNewEconomicIndexDataSerieBacenController {

    private final UpdateNewEconomicIndexDataSerieBacenBusinessService updateNewEconomicIndexDataSerieBacenBusinessService;

    @GetMapping
    public ResponseEntity<Boolean> updateNew() {
        return ResponseEntity.ok(updateNewEconomicIndexDataSerieBacenBusinessService.execute(UUID.randomUUID(),Boolean.TRUE));
    }
}
