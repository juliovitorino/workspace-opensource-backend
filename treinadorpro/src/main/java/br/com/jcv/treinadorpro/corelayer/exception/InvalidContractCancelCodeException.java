package br.com.jcv.treinadorpro.corelayer.exception;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class InvalidContractCancelCodeException extends CommoditieBaseException {
    public InvalidContractCancelCodeException(String input, HttpStatus httpStatus, String msgcode, Map<String, String> mapParams) {
        super(input, httpStatus, msgcode, mapParams);
    }

    public InvalidContractCancelCodeException(String input, HttpStatus httpStatus, String msgcode) {
        super(input, httpStatus, msgcode);
    }

    public InvalidContractCancelCodeException(String input, HttpStatus httpStatus) {
        super(input, httpStatus);
    }

    public InvalidContractCancelCodeException(String input, int httpStatus) {
        super(input, httpStatus);
    }
}
