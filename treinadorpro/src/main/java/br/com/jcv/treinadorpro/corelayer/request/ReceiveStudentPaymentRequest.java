package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.PaymentMethodEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiveStudentPaymentRequest implements Serializable {

    @JsonIgnore
    private UUID studentPaymentExternalId;

    @JsonProperty("paymentDate")
    private LocalDate paymentDate;

    @JsonProperty("receivedAmount")
    private BigDecimal receivedAmount;

    @JsonProperty("paymentMethod")
    private PaymentMethodEnum paymentMethod;

    @JsonProperty("comment")
    private String comment;

}
