package br.com.jcv.restclient.brcities;

import java.util.List;

import br.com.jcv.restclient.brcities.response.UfResponse;

public interface BrazilianCitiesRestClientConsumer {
    List<UfResponse> listAllFederationUnit();
}
