package br.com.jcv.treinadorpro.corelayer.model;


import br.com.jcv.treinadorpro.corelayer.enums.PaymentFrequencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
import java.util.UUID;

@Entity
@Table(name = "plan_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PlanTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false)
    private UUID externalId;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "amount_discount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amountDiscount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_frequency", nullable = false)
    private PaymentFrequencyEnum paymentFrequency;

    @Column(name = "qty_contract_allowed", nullable = false)
    private Long qtyContractAllowed;

    @Column(name = "qty_user_student_allowed", nullable = false)
    private Long qtyUserStudentAllowed;


    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
