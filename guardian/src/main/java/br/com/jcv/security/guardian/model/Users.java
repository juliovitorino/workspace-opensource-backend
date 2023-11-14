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
@Table(name = "tb_user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "tx_name")
    @CodeGeneratorFieldDescriptor(fieldDescription = "User name")
    private String name;

    @Column(name = "tx_encoded_pass_phrase")
    @CodeGeneratorFieldDescriptor(fieldDescription = "encoded pass phrase")
    private String encodedPassPhrase;

    @Column(name = "id_user_uuid", unique = true)
    @CodeGeneratorFieldDescriptor(fieldDescription = "user external token")
    private UUID idUserUUID;

    @Column(name = "dt_birthday")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Birth day")
    private LocalDate birthday;

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
