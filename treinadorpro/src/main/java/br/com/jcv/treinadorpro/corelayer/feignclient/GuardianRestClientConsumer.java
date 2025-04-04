package br.com.jcv.treinadorpro.corelayer.feignclient;

import br.com.jcv.restclient.guardian.request.CreateNewAccountRequest;
import br.com.jcv.restclient.guardian.response.CreateNewAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "guardianClient", url = "${guardian.api.url}")
public interface GuardianRestClientConsumer {

    @PostMapping("/v1/api/business/create-account")
    CreateNewAccountResponse createNewAccount(@RequestBody CreateNewAccountRequest request);
}
