package br.com.jcv.treinadorpro.corelayer.response;

import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("trainingPack")
    private TrainingPackResponse trainingPack;

    @JsonProperty("studentUser")
    private UserResponse studentUser;

    @JsonProperty("description")
    private String description;

    @JsonProperty("workoutSite")
    private String workoutSite;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("monday")
    private String monday;

    @JsonProperty("tuesday")
    private String tuesday;

    @JsonProperty("wednesday")
    private String wednesday;

    @JsonProperty("thursday")
    private String thursday;

    @JsonProperty("friday")
    private String friday;

    @JsonProperty("saturday")
    private String saturday;

    @JsonProperty("sunday")
    private String sunday;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
