package br.com.jcv.treinadorpro.corelayer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "personal_feature")
public class PersonalFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_user_id", nullable = false)
    private User personalUser;

    @Column(name = "mon_period", length = 11)
    private String monPeriod;

    @Column(name = "tue_period", length = 11)
    private String tuePeriod;

    @Column(name = "wed_period", length = 11)
    private String wedPeriod;

    @Column(name = "thu_period", length = 11)
    private String thuPeriod;

    @Column(name = "fri_period", length = 11)
    private String friPeriod;

    @Column(name = "sat_period", length = 11)
    private String satPeriod;

    @Column(name = "sun_period", length = 11)
    private String sunPeriod;

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
        return "PersonalFeature{" +
                "id=" + id +
                ", personalUser=" + personalUser +
                ", monPeriod='" + monPeriod + '\'' +
                ", tuePeriod='" + tuePeriod + '\'' +
                ", wedPeriod='" + wedPeriod + '\'' +
                ", thuPeriod='" + thuPeriod + '\'' +
                ", friPeriod='" + friPeriod + '\'' +
                ", satPeriod='" + satPeriod + '\'' +
                ", sunPeriod='" + sunPeriod + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
