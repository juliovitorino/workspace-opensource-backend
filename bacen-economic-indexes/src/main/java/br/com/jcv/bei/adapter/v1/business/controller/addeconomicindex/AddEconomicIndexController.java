package br.com.jcv.bei.adapter.v1.business.controller.addeconomicindex;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.bei.corebusiness.addeconomicindex.AddEconomicIndexBusinessService;
import br.com.jcv.bei.infrastructure.response.BaseResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/business/economic-index")
@RequiredArgsConstructor
public class AddEconomicIndexController {

    private final AddEconomicIndexBusinessService addEconomicIndexBusinessService;

    @PostMapping
    public ResponseEntity<BaseResponse> addEconomicIndex(@RequestBody AddEconomicIndexRequest request) {
        return ResponseEntity.ok(addEconomicIndexBusinessService.execute(UUID.randomUUID(), request));
    }
}
