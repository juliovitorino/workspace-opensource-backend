package br.com.jcv.treinadorpro.corelayer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewStudentContractRequest implements Serializable {

    @JsonProperty("personalTrainerExternalId")
    private UUID personalTrainerExternalId;

    @JsonProperty("trainingPackExternalId")
    private UUID trainingPackExternalId;

    @JsonProperty("existingStudentExternalId")
    private UUID existingStudentExternalId;

    @JsonProperty("newStudent")
    private NewStudent newStudent;

    @JsonProperty("trainingInfo")
    private TrainingInfo trainingInfo;

    @JsonProperty("instalments")
    private List<Instalment> instalmentList;
}
