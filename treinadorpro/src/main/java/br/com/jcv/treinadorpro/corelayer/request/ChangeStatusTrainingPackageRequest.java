package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeStatusTrainingPackageRequest {
    private UUID externalId;
    private StatusEnum status;
}
