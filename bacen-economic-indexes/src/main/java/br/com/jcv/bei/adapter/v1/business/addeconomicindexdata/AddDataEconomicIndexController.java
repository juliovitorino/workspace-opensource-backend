package br.com.jcv.bei.adapter.v1.business.addeconomicindexdata;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.bei.corebusiness.addeconomicindexdata.AddDataEconomicIndexBusinessService;
import br.com.jcv.bei.infrastructure.response.BaseResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/business/economic-index/data")
@RequiredArgsConstructor
public class AddDataEconomicIndexController {

    private final AddDataEconomicIndexBusinessService addDataEconomicIndexBusinessService;

    @PostMapping
    public ResponseEntity<BaseResponse> addDataEconomicIndex(@RequestBody AddDataEconomicIndexRequest request) {
        return ResponseEntity.ok(addDataEconomicIndexBusinessService.execute(UUID.randomUUID(), request));
    }
}
