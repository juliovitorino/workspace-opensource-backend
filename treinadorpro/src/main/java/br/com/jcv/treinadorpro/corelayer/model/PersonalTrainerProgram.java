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

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "personal_trainer_program")
@ToString
@Builder
@AllArgsConstructor
public class PersonalTrainerProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_user_id", nullable = false)
    private User personalUser;

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

    @OneToMany(mappedBy = "personalTrainerProgram", fetch = FetchType.LAZY)
    private List<UserWorkoutCalendar> userWorkoutCalendarList;
}
