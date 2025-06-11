package br.com.jcv.treinadorpro.corelayer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponse implements Serializable {

    private Long id;

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("namePt")
    private String namePt;

    @JsonProperty("nameEn")
    private String nameEn;

    @JsonProperty("nameEs")
    private String nameEs;

    @JsonProperty("videoUrlPt")
    private String videoUrlPt;

    @JsonProperty("videoUrlEn")
    private String videoUrlEn;

    @JsonProperty("videoUrlEs")
    private String videoUrlEs;

    @JsonProperty("imageUUID")
    private UUID imageUUID;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
