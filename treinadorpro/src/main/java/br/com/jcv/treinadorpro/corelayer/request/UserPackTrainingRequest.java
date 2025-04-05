package br.com.jcv.treinadorpro.corelayer.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class UserPackTrainingRequest {
    private Long id;
    private Long personalUserId;
    private Long studentUserId;
    private String description;
    private BigDecimal price;
    private Long modalityId;
    private String startTime;
    private String endTime;
    private String daysOfWeek;
}
