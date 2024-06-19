package br.com.jcv.reaction.adapter.controller.v1.business.pode.me.apagar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.restclient.brcities.BrazilianCitiesRestClientConsumer;
import br.com.jcv.restclient.brcities.response.UfResponse;

@RestController
@RequestMapping("/v1/api/business/test")
public class TestController {
    @Autowired private BrazilianCitiesRestClientConsumer brazilianCitiesRestClientConsumer;

    @GetMapping
    public ResponseEntity<List<UfResponse>> getUf() {
        return ResponseEntity.ok(brazilianCitiesRestClientConsumer.listAllFederationUnit());
    }
}
