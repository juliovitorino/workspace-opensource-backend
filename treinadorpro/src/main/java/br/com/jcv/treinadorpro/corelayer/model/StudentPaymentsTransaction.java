package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.PaymentMethodEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "student_payments_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPaymentsTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_payments_id", nullable = false)
    private StudentPayment studentPayment;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "received_amount", precision = 10, scale = 2)
    private BigDecimal receivedAmount;

    @Column(name = "payment_method", length = 100)
    @Enumerated(value = EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(length = 1)
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status = StatusEnum.A;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
