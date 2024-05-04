package br.com.jcv.notifier.corelayer.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorDescriptor;
import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorFieldDescriptor;
import br.com.jcv.notifier.infrastructure.util.StringJsonUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="tb_notifier")
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/notifier/src/main/resources",
        project = "",
        fullDescription = "Control all notifications")
@TypeDefs( {@TypeDef( name= "StringJsonObject", typeClass = StringJsonUserType.class)})
public class Notifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notifier")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "uuid_external_app")
    @CodeGeneratorFieldDescriptor(fieldDescription = "External Application UUID reference")
    private UUID uuidExternalApp;

    @Column(name = "uuid_external_user")
    @CodeGeneratorFieldDescriptor(fieldDescription = "External User UUID reference")
    private UUID uuidExternalUser;

    @Column(name = "tx_type")
    @CodeGeneratorFieldDescriptor(fieldDescription = "type selector for notification")
    private String type;

    @Column(name = "tx_title")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Title for notification")
    private String title;

    @Column(name = "tx_description")
    @CodeGeneratorFieldDescriptor(fieldDescription = "descritpion for notification")
    private String description;

    @Column(name = "tx_url_image")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Url image that define the notification")
    private String urlImage;

    @Column(name = "tx_icon_class")
    @CodeGeneratorFieldDescriptor(fieldDescription = "frontend icon class")
    private String iconClass;

    @Column(name = "tx_url_follow")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Any url to fast forward")
    private String urlFollow;

    @Column(name = "tx_object")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Any valid Json object to save")
    @Type(type = "StringJsonObject")
    private String objectFree;

    @CodeGeneratorFieldDescriptor(fieldDescription = "Status field")
    @Column(name = "in_seen", length = 1)
    private String seenIndicator;

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
