package br.com.jcv.restclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("stackTraceList")
    private List<StackTraceItem> stackTraceList;

    @JsonProperty("errors")
    private Map<String, Object> errors;

    @JsonProperty("msgcode")
    private String msgcode;
}
