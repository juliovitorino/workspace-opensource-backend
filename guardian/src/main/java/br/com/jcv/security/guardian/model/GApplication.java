package br.com.jcv.security.guardian.model;

import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorDescriptor;
import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorFieldDescriptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@CodeGeneratorDescriptor(outputDir = "/Users/juliovitorino/workspaces/workspace-opensource-backend/guardian/src/main/resources",
        project = "",
        fullDescription = "Control all applications")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_application")
public class GApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "tx_name")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Application name")
    private String name;

    @Column(name = "cd_external")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Application external code")
    private UUID externalCodeUUID;

    @Column(name = "nu_jwt_ttl")
    @CodeGeneratorFieldDescriptor(fieldDescription = "JWT Time to Live in millisecs")
    private Long jwtTimeToLive;

    @CodeGeneratorFieldDescriptor(fieldDescription = "Status field")
    @Column(length = 1)
    private String status;

    @CodeGeneratorFieldDescriptor(fieldDescription = "record created at")
    @Column(name = "date_created",updatable = false)
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @CodeGeneratorFieldDescriptor(fieldDescription = "record updated at")
    @Column(name = "date_updated")
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

}
