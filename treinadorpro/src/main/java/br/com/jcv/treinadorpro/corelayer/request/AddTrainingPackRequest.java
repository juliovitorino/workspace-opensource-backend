package br.com.jcv.treinadorpro.corelayer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTrainingPackRequest {

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("modalityId")
    private Long modalityId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("durationDays")
    private Integer durationDays;

    @JsonProperty("weeklyFrequency")
    private Integer weeklyFrequency;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("currency")
    private String currency;

}
