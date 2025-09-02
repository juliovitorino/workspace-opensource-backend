package br.com.jcv.treinadorpro.corelayer.response;

import br.com.jcv.treinadorpro.corelayer.enums.PaymentMethodEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPaymentsTransactionResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("externalId")
    private UUID externalId;

    @JsonProperty("studentPayment")
    private StudentPaymentResponse studentPayment;

    @JsonProperty("paymentDate")
    private LocalDate paymentDate;

    @JsonProperty("receivedAmount")
    private BigDecimal receivedAmount;

    @JsonProperty("paymentMethod")
    private PaymentMethodEnum paymentMethod;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
