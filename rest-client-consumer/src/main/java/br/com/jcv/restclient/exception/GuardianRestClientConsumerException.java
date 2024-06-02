package br.com.jcv.restclient.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;

public class GuardianRestClientConsumerException extends CommoditieBaseException {
    public GuardianRestClientConsumerException(String input, HttpStatus httpStatus, String msgcode, Map<String, String> mapParams) {
        super(input,httpStatus,msgcode,mapParams);
    }

    public GuardianRestClientConsumerException(String input, HttpStatus httpStatus, String msgcode) {
        this(input, httpStatus, msgcode, new HashMap());
    }

    public GuardianRestClientConsumerException(String input, HttpStatus httpStatus) {
        this(input, httpStatus, null, new HashMap());
    }

    public GuardianRestClientConsumerException(String input, int httpStatus) {
        this(input, HttpStatus.valueOf(httpStatus), null, new HashMap());
    }

}
