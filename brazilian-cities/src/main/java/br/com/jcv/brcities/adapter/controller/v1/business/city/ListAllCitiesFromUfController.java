package br.com.jcv.brcities.adapter.controller.v1.business.city;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.brcities.businesslayer.city.ListAllCitiesFromUfBusinessService;

@RestController@RequestMapping("/v1/api/business/city")
public class ListAllCitiesFromUfController {

    @Autowired private ListAllCitiesFromUfBusinessService listAllCitiesFromUfBusinessService;

    @GetMapping("{uf}")
    public ResponseEntity<CitiesResponse> ListAllCitiesFromUf(@Valid @PathVariable(name = "uf") String uf) {
        return ResponseEntity.ok(listAllCitiesFromUfBusinessService.execute(UUID.randomUUID(), uf.toUpperCase()));
    }
}
