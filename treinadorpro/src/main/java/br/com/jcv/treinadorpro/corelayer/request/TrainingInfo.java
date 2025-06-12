package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TrainingInfo implements Serializable {

    @JsonProperty("goal")
    private String goal;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("weekdays")
    private List<WeekdaysEnum> weekdays;
}
