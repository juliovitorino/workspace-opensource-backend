package br.com.jcv.treinadorpro.corelayer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuilderDashboardResponse implements Serializable {

    @JsonProperty("activeStudentContract")
    private Long activeStudentContract;

    @JsonProperty("overdueAmountContracts")
    private BigDecimal overdueAmountContracts;

    @JsonProperty("totalTrainingPack")
    private Long totalTrainingPack;

    @JsonProperty("totalTodayWorkout")
    private Long totalTodayWorkout;

    @JsonProperty("totalAmountReceivedMonth")
    private BigDecimal totalAmountReceivedMonth;
}
