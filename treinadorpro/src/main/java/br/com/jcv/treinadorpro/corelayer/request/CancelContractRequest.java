package br.com.jcv.treinadorpro.corelayer.request;

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
public class CancelContractRequest implements Serializable {
    private UUID externalId;
    private String cancelCode;
}
