package br.com.jcv.brcities.adapter.controller.v1.business.uf;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.brcities.businesslayer.uf.ListAllFederationUnitBusinessService;

@RestController
@RequestMapping("/v1/api/business/uf")
public class ListAllFederationUnitController {
    @Autowired private ListAllFederationUnitBusinessService listAllFederationUnitBusinessService;

    @GetMapping
    public ResponseEntity<List<UfResponse>> listAllFederationUnit() {
        return ResponseEntity.ok(listAllFederationUnitBusinessService.execute(UUID.randomUUID(),Boolean.TRUE));
    }
}
