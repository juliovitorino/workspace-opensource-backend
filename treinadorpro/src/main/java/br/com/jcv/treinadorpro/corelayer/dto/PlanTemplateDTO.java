package br.com.jcv.treinadorpro.corelayer.dto;


import br.com.jcv.treinadorpro.corelayer.enums.PaymentFrequencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Long qtyUserPackTrainingAllowed;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
