package br.com.jcv.treinadorpro.corelayer.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class CreateTrainingPackRequest {

    @NotNull
    @Positive
    @JsonProperty("personalUserUUID")
    private UUID personalUserUUID;

    @NotNull
    @Positive
    @JsonProperty("modalityId")
    private Long modalityId;

    @NotBlank
    @Size(max = 255)
    @JsonProperty("description")
    private String description;

    @NotNull
    @Positive
    @JsonProperty("durationDays")
    private Integer durationDays;

    @NotNull
    @Min(1)
    @Max(7)
    @JsonProperty("weeklyFrequency")
    private Integer weeklyFrequency;

    @NotBlank
    @Size(max = 255)
    @JsonProperty("notes")
    private String notes;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    @JsonProperty("price")
    private BigDecimal price;

    @Size(max = 10)
    @JsonProperty("currency")
    private String currency;
}
