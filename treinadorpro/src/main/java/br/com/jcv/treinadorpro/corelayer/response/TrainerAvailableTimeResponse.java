package br.com.jcv.treinadorpro.corelayer.response;

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
public class TrainerAvailableTimeResponse implements Serializable {

    @JsonProperty("trainer")
    private PersonalTrainerResponse trainer;

    @JsonProperty("monTotal")
    private Integer monTotal;

    @JsonProperty("tueTotal")
    private Integer tueTotal;

    @JsonProperty("wedTotal")
    private Integer wedTotal;

    @JsonProperty("thuTotal")
    private Integer thuTotal;

    @JsonProperty("friTotal")
    private Integer friTotal;

    @JsonProperty("satTotal")
    private Integer satTotal;

    @JsonProperty("sunTotal")
    private Integer sunTotal;

    @JsonProperty("mondayAvailableTimes")
    private List<String> mondayAvailableTimes;

    @JsonProperty("tuesdayAvailableTimes")
    private List<String> tuesdayAvailableTimes;

    @JsonProperty("wednesdayAvailableTimes")
    private List<String> wednesdayAvailableTimes;

    @JsonProperty("thursdayAvailableTimes")
    private List<String> thursdayAvailableTimes;

    @JsonProperty("fridayAvailableTimes")
    private List<String> fridayAvailableTimes;

    @JsonProperty("saturdayAvailableTimes")
    private List<String> saturdayAvailableTimes;

    @JsonProperty("sundayAvailableTimes")
    private List<String> sundayAvailableTimes;
}
