package br.com.jcv.treinadorpro.corelayer.request;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
public class CreateWorkoutCalendarFromTemplateRequest implements Serializable {
    private Long userPackTrainingId;
    private List<UUID> programTemplateExternalIdList;
    private List<LocalDate> trainingDateList;

}
