package br.com.jcv.treinadorpro.corelayer.response;

import br.com.jcv.treinadorpro.corelayer.enums.PaymentMethodEnum;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentPaymentResponse {

    @JsonProperty("id")
    @JsonIgnore
    private Long id;

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("contract")
    private ContractResponse contract;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("dueDate")
    private LocalDate dueDate;

    @JsonProperty("paymentDate")
    private LocalDate paymentDate;

    @JsonProperty("receivedAmount")
    private BigDecimal receivedAmount;

    @JsonProperty("paymentMethod")
    private PaymentMethodEnum paymentMethod;

    @JsonProperty("comment")
    private String comment;


    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
