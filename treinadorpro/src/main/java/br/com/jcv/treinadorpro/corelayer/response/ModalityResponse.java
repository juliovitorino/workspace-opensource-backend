package br.com.jcv.treinadorpro.corelayer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModalityResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("namePt")
    private String namePt;

    @JsonProperty("nameEn")
    private String nameEn;

    @JsonProperty("nameEs")
    private String nameEs;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
