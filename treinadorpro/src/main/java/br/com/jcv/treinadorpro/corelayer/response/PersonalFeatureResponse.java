package br.com.jcv.treinadorpro.corelayer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalFeatureResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("register")
    private String register;

    @JsonProperty("place")
    private String place;

    @JsonProperty("experience")
    private String experience;

    @JsonProperty("specialty")
    private String specialty;

    @JsonProperty("monPeriod")
    private String monPeriod;

    @JsonProperty("tuePeriod")
    private String tuePeriod;

    @JsonProperty("wedPeriod")
    private String wedPeriod;

    @JsonProperty("thuPeriod")
    private String thuPeriod;

    @JsonProperty("friPeriod")
    private String friPeriod;

    @JsonProperty("satPeriod")
    private String satPeriod;

    @JsonProperty("sunPeriod")
    private String sunPeriod;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
