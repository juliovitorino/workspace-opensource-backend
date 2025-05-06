package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "personal_trainer_payments")
public class PersonalTrainerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "active_personal_plan_id", nullable = false, foreignKey = @ForeignKey(name = "fk_personal_trainer_payment_active_personal_plan"))
    private ActivePersonalPlan activePersonalPlan;

    @Column(name = "expected_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal expectedAmount;

    @Column(name = "expected_date", nullable = false)
    private LocalDate expectedDate;

    @Column(name = "amount_discount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amountDiscount;

    @Column(name = "amount_paid", precision = 10, scale = 2, nullable = false)
    private BigDecimal amountPaid;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Column(name = "status", length = 1)
    private StatusEnum status = StatusEnum.A;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "PersonalTrainerPayment{" +
                "id=" + id +
                ", activePersonalPlan=" + activePersonalPlan +
                ", expectedAmount=" + expectedAmount +
                ", expectedDate=" + expectedDate +
                ", amountDiscount=" + amountDiscount +
                ", amountPaid=" + amountPaid +
                ", paidDate=" + paidDate +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
