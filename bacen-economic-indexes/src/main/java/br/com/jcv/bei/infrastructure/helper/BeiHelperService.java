package br.com.jcv.bei.infrastructure.helper;

import org.springframework.stereotype.Component;

import br.com.jcv.bei.infrastructure.response.BaseResponse;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;

@Component
public class BeiHelperService {

    public BaseResponse getResponse(String msgcode, String message){
        return BaseResponse.builder()
                .mensagemResponse(
                        MensagemResponse.builder()
                                .mensagem(message)
                                .msgcode(msgcode)
                                .build()
                )
                .build();

    }
}
