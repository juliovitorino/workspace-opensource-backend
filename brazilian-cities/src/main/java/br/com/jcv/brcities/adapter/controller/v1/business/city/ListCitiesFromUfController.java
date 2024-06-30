package br.com.jcv.brcities.adapter.controller.v1.business.city;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.brcities.businesslayer.city.ListCitiesFromUfBusinessService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/business/city")
@RequiredArgsConstructor
public class ListCitiesFromUfController {

    private final ListCitiesFromUfBusinessService listCitiesFromUfBusinessService;

    @GetMapping(params = "uf")
    public ResponseEntity<Boolean> ListAllCitiesFromUf(@Valid @RequestParam(name = "uf") String uf) {
        return ResponseEntity.ok(listCitiesFromUfBusinessService.execute(UUID.randomUUID(), uf.toUpperCase()));
    }
}
