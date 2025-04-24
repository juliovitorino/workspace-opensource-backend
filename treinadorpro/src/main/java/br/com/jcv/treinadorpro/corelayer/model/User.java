package br.com.jcv.treinadorpro.corelayer.model;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.description.type.TypeList;
import org.hibernate.annotations.CreationTimestamp;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String cellphone;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(length = 1)
    @Enumerated(value = EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "url_photo_profile", columnDefinition = "TEXT")
    private String urlPhotoProfile;

    @Column(name = "user_profile", length = 50)
    @Enumerated(value = EnumType.STRING)
    private UserProfileEnum userProfile = UserProfileEnum.PERSONAL_TRAINER;

    @Column(name = "master_language", length = 10)
    private String masterLanguage = "pt-BR";

    @Column(name = "guardian_integration")
    private UUID guardianIntegrationUUID;

    @Column(length = 1)
    private String status = "A"; // A = Active, B = Blocked, I = Inactive

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "personalUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivePersonalPlan> activePersonalPlanList;

    @OneToMany(mappedBy = "personalUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPackTraining> personalUserPackTrainingList;

    @OneToMany(mappedBy = "studentUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPackTraining> studentUserPackTrainingList;

}
