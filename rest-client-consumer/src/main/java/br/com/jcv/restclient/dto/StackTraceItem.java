package br.com.jcv.restclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StackTraceItem {

    @JsonProperty("class")
    private String className;

    @JsonProperty("lineNumber")
    private int lineNumber;
}
