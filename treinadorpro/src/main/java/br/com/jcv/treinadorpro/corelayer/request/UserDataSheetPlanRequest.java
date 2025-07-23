package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataSheetPlanRequest implements Serializable {
    @JsonProperty("contract")
    private ContractResponse contract;

    @JsonProperty("modality")
    private ModalityResponse modality;

    @JsonProperty("goal")
    private GoalResponse goal;

    @JsonProperty("program")
    private ProgramResponse program;

    @JsonProperty("plan")
    private Map<String, List<UserWorkoutPlanRequest>> plan;
}
