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
import java.util.Date;
import java.util.UUID;

@Entity
@CodeGeneratorDescriptor(outputDir = "/Users/juliovitorino/workspaces/workspace-opensource-backend/guardian/src/main/resources",
        project = "",
        fullDescription = "Control all user session state")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_session_state")
public class SessionState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_session_state")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "session state primary key")
    private Long id;

    @Column(name = "id_token")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Session's token")
    private UUID idToken;

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
