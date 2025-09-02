package br.com.jcv.treinadorpro.corelayer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractScheduleModifierRequest implements Serializable {
    @JsonProperty("contractExternalId")
    private UUID contractExternalId;

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
}
