package br.com.jcv.treinadorpro.infrastructure.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResultRequest<T> implements Serializable {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("size")
    private Integer size = 20;

    @JsonProperty("request")
    private T request;
}
