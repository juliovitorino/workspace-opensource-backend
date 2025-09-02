package br.com.jcv.treinadorpro.corelayer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@AllArgsConstructor
@Builder
public class CreateNewStudentContractResponse implements Serializable {
    @JsonProperty("externalId")
    private UUID contractExternalId;
}
