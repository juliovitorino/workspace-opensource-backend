package br.com.jcv.treinadorpro.corelayer.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ContractRequest {
    private Long id;
    private Long personalUserId;
    private Long studentUserId;
    private String description;
    private BigDecimal price;
    private String currency;
    private Long modalityId;
    private String startTime;
    private String endTime;
    private List<Integer> daysOfWeek;
}
