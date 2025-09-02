package br.com.jcv.treinadorpro.corelayer.dto;


import br.com.jcv.treinadorpro.corelayer.enums.PaymentFrequencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ActivePersonalPlanDTO {

    private Long id;
    private UUID externalId;
    private UserDTO personalUserDTO;
    private String description;
    private BigDecimal price;
    private BigDecimal amountDiscount;
    private LocalDate planExpirationDate;
    private PaymentFrequencyEnum paymentFrequency;
    private Long qtyContractAllowed;
    private Long qtyUserStudentAllowed;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
