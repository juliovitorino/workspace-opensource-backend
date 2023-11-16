package br.com.jcv.security.guardian.model;

import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorDescriptor;
import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorFieldDescriptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@CodeGeneratorDescriptor(outputDir = "/Users/juliovitorino/workspaces/workspace-opensource-backend/guardian/src/main/resources",
        project = "",
        fullDescription = "Control all users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_application_user")
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application_user")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "id_application")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Application primary key")
    private Long idApplication;

    @Column(name = "id_user")
    @CodeGeneratorFieldDescriptor(fieldDescription = "User primary key")
    private Long idUser;

    @Column(name = "tx_email")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Email")
    private String email;

    @Column(name = "tx_encoded_pass_phrase")
    @CodeGeneratorFieldDescriptor(fieldDescription = "encoded pass phrase")
    private String encodedPassPhrase;

    @Column(name = "cd_external_uuid", unique = true)
    @CodeGeneratorFieldDescriptor(fieldDescription = "user external token")
    private UUID externalAppUserUUID;

    @Column(name = "cd_url_token_activation")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Token for URL activation")
    private String urlTokenActivation;

    @Column(name = "cd_activation")
    @CodeGeneratorFieldDescriptor(fieldDescription = "6-digit activation code")
    private String activationCode;

    @Column(name = "dt_due_activation")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Due date for account activation")
    private Date dueDateActivation;

    @CodeGeneratorFieldDescriptor(fieldDescription = "Status field")
    @Column(length = 1)
    private String status;

    @CodeGeneratorFieldDescriptor(fieldDescription = "record created at")
    @Column(name = "date_created")
    private Date dateCreated;

    @CodeGeneratorFieldDescriptor(fieldDescription = "record updated at")
    @Column(name = "date_updated")
    private Date dateUpdated;

}
