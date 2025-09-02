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
public class PageResultResponse<T> implements Serializable {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("totalPages")
    private Integer totalPages;

    @JsonProperty("count")
    private Long count;

    @JsonProperty("content")
    private List<T> content;
}
