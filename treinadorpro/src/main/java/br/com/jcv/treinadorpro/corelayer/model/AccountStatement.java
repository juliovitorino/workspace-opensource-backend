package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.PaymentMethodEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account_statement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personal_user_id", nullable = false)
    private User personalUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_user_id")
    private User studentUser;

    @Column(name = "type", length = 10)
    @Enumerated(EnumType.STRING)
    private EntryType type;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "payment_method", length = 100)
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "status", length = 1)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum EntryType {
        CREDIT, DEBIT, BALANCE
    }
}
