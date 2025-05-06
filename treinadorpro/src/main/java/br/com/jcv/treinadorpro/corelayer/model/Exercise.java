package br.com.jcv.treinadorpro.corelayer.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "exercise")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_pt", length = 100)
    private String namePt;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "name_es", length = 100)
    private String nameEs;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    private List<ProgramTemplate> programTemplateList;

    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    private List<PersonalTrainerProgram> personalTrainerProgramList;

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", namePt='" + namePt + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameEs='" + nameEs + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", programTemplateList=" + programTemplateList +
                ", personalTrainerProgramList=" + personalTrainerProgramList +
                '}';
    }
}
