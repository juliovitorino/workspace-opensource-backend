package br.com.jcv.notifier.model;

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
@CodeGeneratorDescriptor(outputDir = "/Users/juliovitorino/workspaces/workspace-opensource-backend/notifier/src/main/resources",
        project = "",
        fullDescription = "Control all notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_notifier")
public class Notifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notifier")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "Notifier primary key")
    private Long id;

    @Column(name = "id_application_uuid")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Application Sender UUID")
    private UUID applicationUUID;

    @Column(name = "id_user_uuid")
    @CodeGeneratorFieldDescriptor(fieldDescription = "User UUID message target")
    private UUID userUUID;

    @Column(name = "in_type")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Notifier Type")
    private String type;

    @Column(name = "tx_title")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Title for notifier")
    private String title;

    @Column(name = "tx_description")
    @CodeGeneratorFieldDescriptor(fieldDescription = "description for notifier")
    private String description;

    @Column(name = "in_readed")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Read flag")
    private String isReaded;

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
