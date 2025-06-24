package br.com.jcv.treinadorpro.corebusiness.plantemplate;


import br.com.jcv.treinadorpro.corelayer.enums.PaymentFrequencyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlanTemplateResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("amountDiscount")
    private BigDecimal amountDiscount;

    @JsonProperty("paymentFrequency")
    private PaymentFrequencyEnum paymentFrequency;

    @JsonProperty("qtyContractAllowed")
    private Long qtyContractAllowed;

    @JsonProperty("qtyUserStudentAllowed")
    private Long qtyUserStudentAllowed;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}