package br.com.jcv.preferences.corelayer.model;

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
import br.com.jcv.preferences.infrastructure.util.StringJsonUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="tb_system_preferences")
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/preferences/src/main/resources",
        project = "",
        fullDescription = "Control all system preference configuration")
@TypeDefs( {@TypeDef( name= "StringJsonObject", typeClass = StringJsonUserType.class)})
public class SystemPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_system_preference")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;


    @Column(name = "uuid_external_app")
    @CodeGeneratorFieldDescriptor(fieldDescription = "External Application UUID reference")
    private UUID uuidExternalApp;

    @Column(name = "tx_key")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Key selector for system preference")
    private String key;


    @Column(name = "tx_preference")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Any valid Json object preference")
    @Type(type = "StringJsonObject")
    private String preference;

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
