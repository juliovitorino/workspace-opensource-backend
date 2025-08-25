package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.VideoPlatformEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trainer_exercise_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerExerciseVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personal_user_id", nullable = false)
    private User personalUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "video_url", nullable = false, columnDefinition = "text")
    private String videoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", length = 20, nullable = false)
    private VideoPlatformEnum videoPlatform;

    @Column(name = "status", length = 1)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
