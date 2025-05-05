package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.ExecutionMethodEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_workout_calendar")
@Builder
@AllArgsConstructor
public class UserWorkoutCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pack_training_id", nullable = false)
    private UserPackTraining userPackTraining;

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId;

    @Column(name = "training_date")
    private LocalDate trainingDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "modality_id", nullable = false)
    private Modality modality;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_group_id", nullable = false)
    private WorkGroup workGroup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "custom_exercise", length = 100)
    private String customExercise;

    @Column(name = "custom_program", length = 100)
    private String customProgram;

    @Column(name = "execution_method", length = 100)
    @Enumerated(EnumType.STRING)
    private ExecutionMethodEnum executionMethod;

    @Column(name = "qty_series")
    private Integer qtySeries;

    @Column(name = "qty_reps")
    private Integer qtyReps;

    @Column(name = "execution", length = 100)
    private String execution;

    @Column(name = "execution_time", length = 5)
    private String executionTime;

    @Column(name = "rest_time", length = 5)
    private String restTime;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "weight_unit", length = 10)
    private String weightUnit;

    @Column(name = "comments", length = 500)
    private String comments;

    @Column(name = "obs", length = 500)
    private String obs;

    @Column(name = "status", length = 1)
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.A;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "UserWorkoutCalendar{" +
                "id=" + id +
                ", userPackTraining=" + userPackTraining +
                ", externalId=" + externalId +
                ", trainingDate=" + trainingDate +
                ", modality=" + modality +
                ", workGroup=" + workGroup +
                ", goal=" + goal +
                ", exercise=" + exercise +
                ", program=" + program +
                ", customExercise='" + customExercise + '\'' +
                ", customProgram='" + customProgram + '\'' +
                ", executionMethod=" + executionMethod +
                ", qtySeries=" + qtySeries +
                ", qtyReps=" + qtyReps +
                ", execution='" + execution + '\'' +
                ", executionTime='" + executionTime + '\'' +
                ", restTime='" + restTime + '\'' +
                ", weight=" + weight +
                ", weightUnit='" + weightUnit + '\'' +
                ", comments='" + comments + '\'' +
                ", obs='" + obs + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
