package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.ExecutionMethodEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "program_template")
public class ProgramTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false)
    private UUID externalId;

    @Column(name = "version", nullable = false)
    private Long version;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "modality_id", nullable = false)
    private Modality modality;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "work_group_id", nullable = false)
    private WorkGroup workGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

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

    @Column(name = "status", length = 1)
    private String status = "A";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "ProgramTemplate{" +
                "id=" + id +
                ", externalId=" + externalId +
                ", version=" + version +
                ", modality=" + modality +
                ", exercise=" + exercise +
                ", workGroup=" + workGroup +
                ", goal=" + goal +
                ", program=" + program +
                ", executionMethod=" + executionMethod +
                ", qtySeries=" + qtySeries +
                ", qtyReps=" + qtyReps +
                ", execution='" + execution + '\'' +
                ", executionTime='" + executionTime + '\'' +
                ", restTime='" + restTime + '\'' +
                ", weight=" + weight +
                ", weightUnit='" + weightUnit + '\'' +
                ", comments='" + comments + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
