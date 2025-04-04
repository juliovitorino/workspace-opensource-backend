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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@CodeGeneratorDescriptor(outputDir = "/Users/juliovitorino/workspaces/workspace-opensource-backend/guardian/src/main/resources",
        project = "",
        fullDescription = "Control all user x role relationship")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_role")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "role primary key")
    private Long id;

    @Column(name = "id_role")
    @CodeGeneratorFieldDescriptor(fieldDescription = "role ID")
    private Long idRole;

    @Column(name = "id_user")
    @CodeGeneratorFieldDescriptor(fieldDescription = "User ID")
    private Long idUser;

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
