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
import java.util.UUID;

@Entity
@Table(name = "modality")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Modality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false)
    private UUID externalId;

    @Column(name = "name_pt", length = 100)
    private String namePt;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "name_es", length = 100)
    private String nameEs;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "modality", fetch = FetchType.LAZY)
    private List<TrainingPack> trainingPackList;

}
