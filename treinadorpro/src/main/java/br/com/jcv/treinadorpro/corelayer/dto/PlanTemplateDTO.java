package br.com.jcv.treinadorpro.corelayer.dto;


import br.com.jcv.treinadorpro.corelayer.enums.PaymentFrequencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlanTemplateDTO {

    private Long id;
    private String description;
    private BigDecimal price;
    private BigDecimal amountDiscount;
    private PaymentFrequencyEnum paymentFrequency;
    private Long qtyContractAllowed;
    private Long qtyUserStudentAllowed;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
