package br.com.jcv.treinadorpro.corelayer.request;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
public class CreatePersonalTrainerProgramFromTemplateRequest implements Serializable {
    private UUID personalTrainerId;
    private Long version;
    private Long modalityId;
    private Long goalId;
    private List<Long> programList;

}
