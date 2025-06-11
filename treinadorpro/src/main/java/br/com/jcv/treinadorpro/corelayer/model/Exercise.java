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
@Table(name = "exercise")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "name_pt", length = 100)
    private String namePt;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "name_es", length = 100)
    private String nameEs;

    @Column(name = "video_url_pt", length = 1000)
    private String videoUrlPt;

    @Column(name = "video_url_en", length = 1000)
    private String videoUrlEn;

    @Column(name = "video_url_es", length = 1000)
    private String videoUrlEs;

    @Column(name = "image_uuid")
    private UUID imageUUID;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
