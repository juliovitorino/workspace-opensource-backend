package br.com.jcv.preferences.infrastructure.mapper;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;

public interface IPreferencesMapper<T,R, DTO> {
    DTO toDTO(T source);
    R getInstance(MensagemResponse mensagemResponse) ;
    R getInstance(String msgcode, String mensagem);
}
