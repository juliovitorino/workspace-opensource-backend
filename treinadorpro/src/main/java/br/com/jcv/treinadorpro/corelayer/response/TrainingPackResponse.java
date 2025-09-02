package br.com.jcv.treinadorpro.corelayer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPackResponse {

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("personalTrainer")
    private PersonalTrainerResponse personalUser;

    @JsonProperty("modality")
    private ModalityResponse modality;

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

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("totalStudentsPack")
    private Long totalStudentsPack;
}
