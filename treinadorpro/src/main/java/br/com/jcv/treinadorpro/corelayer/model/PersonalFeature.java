package br.com.jcv.treinadorpro.corelayer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "personal_feature")
@ToString
public class PersonalFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_user_id", nullable = false)
    @ToString.Exclude
    private User personalUser;

    @Column(name = "register", length = 50)
    private String register;

    @Column(name = "place", length = 200)
    private String place;

    @Column(name = "experience", length = 50)
    private String experience;

    @Column(name = "specialty", length = 255)
    private String specialty;

    @Column(name = "mon_period", length = 11)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The format should be HH:mm-HH:mm (i.e.: 08:00-12:00)")
    private String monPeriod;

    @Column(name = "tue_period", length = 11)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The format should be HH:mm-HH:mm (i.e.: 08:00-12:00)")
    private String tuePeriod;

    @Column(name = "wed_period", length = 11)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The format should be HH:mm-HH:mm (i.e.: 08:00-12:00)")
    private String wedPeriod;

    @Column(name = "thu_period", length = 11)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The format should be HH:mm-HH:mm (i.e.: 08:00-12:00)")
    private String thuPeriod;

    @Column(name = "fri_period", length = 11)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The format should be HH:mm-HH:mm (i.e.: 08:00-12:00)")
    private String friPeriod;

    @Column(name = "sat_period", length = 11)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The format should be HH:mm-HH:mm (i.e.: 08:00-12:00)")
    private String satPeriod;

    @Column(name = "sun_period", length = 11)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The format should be HH:mm-HH:mm (i.e.: 08:00-12:00)")
    private String sunPeriod;

    @Column(name = "status", length = 1)
    private String status = "A";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
