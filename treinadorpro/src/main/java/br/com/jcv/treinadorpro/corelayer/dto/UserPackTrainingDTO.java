package br.com.jcv.treinadorpro.corelayer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPackTrainingDTO {
    private Long id;
    private UserDTO personalUser;
    private UserDTO studentUser;
    private String description;
    private BigDecimal price;
    private ModalityDTO modality;
    private String startTime;
    private String endTime;
    private String daysOfWeek;
    private List<UserWorkoutCalendarDTO> workoutCalendar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
