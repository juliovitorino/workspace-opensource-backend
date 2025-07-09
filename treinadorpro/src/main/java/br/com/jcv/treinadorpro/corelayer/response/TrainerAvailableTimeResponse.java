package br.com.jcv.treinadorpro.corelayer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerAvailableTimeResponse implements Serializable {
    private PersonalTrainerResponse trainer;
    private List<String> mondayAvailableTimes;
    private List<String> tuesdayAvailableTimes;
    private List<String> wednesdayAvailableTimes;
    private List<String> thursdayAvailableTimes;
    private List<String> fridayAvailableTimes;
    private List<String> saturdayAvailableTimes;
    private List<String> sundayAvailableTimes;
}
