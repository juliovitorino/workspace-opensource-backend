package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.infrastructure.utils.StringArrayListUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false)
    private UUID externalId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pack_training_id", nullable = false)
    private TrainingPack trainingPack;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "student_user_id", nullable = false)
    private User studentUser;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 10)
    private String currency;

    @Column(name = "monday", length = 5)
    private String monday;

    @Column(name = "tuesday", length = 5)
    private String tuesday;

    @Column(name = "wednesday", length = 5)
    private String wednesday;

    @Column(name = "thursday", length = 5)
    private String thursday;

    @Column(name = "friday", length = 5)
    private String friday;

    @Column(name = "saturday", length = 5)
    private String saturday;

    @Column(name = "sunday", length = 5)
    private String sunday;

    @Column(name = "duration",  length = 5)
    private String duration;

    @Column(name = "situation", nullable = false)
    @Enumerated(EnumType.STRING)
    private SituationEnum situation = SituationEnum.OPEN;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.A;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserWorkoutCalendar> userWorkoutCalendarList;

    @ToString.Exclude
    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentPayment> studentPaymentList;

}
