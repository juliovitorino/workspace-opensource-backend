package br.com.jcv.bei.infrastructure.helper;

import org.springframework.stereotype.Component;

import br.com.jcv.bei.corebusiness.addeconomicindex.AddEconomicIndexResponse;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;

@Component
public class BeiHelperService {

    public AddEconomicIndexResponse getResponse(String msgcode, String message){
        return AddEconomicIndexResponse.builder()
                .mensagemResponse(
                        MensagemResponse.builder()
                                .mensagem(message)
                                .msgcode(msgcode)
                                .build()
                )
                .build();

    }
}
