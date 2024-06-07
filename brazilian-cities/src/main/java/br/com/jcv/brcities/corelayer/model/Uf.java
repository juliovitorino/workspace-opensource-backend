package br.com.jcv.brcities.corelayer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorDescriptor;
import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorFieldDescriptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/brazilian-cities/src/main/resources",
        project = "",
        fullDescription = "Control all general groups")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "uf")
public class Uf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "UF primary key")
    private Long id;

    @Column(name = "sg_uf")
    @CodeGeneratorFieldDescriptor(fieldDescription = "nickname")
    private String nickname;

    @Column(name = "nm_uf")
    @CodeGeneratorFieldDescriptor(fieldDescription = "UF name")
    private String name;

    @CodeGeneratorFieldDescriptor(fieldDescription = "Status field")
    @Column(length = 1)
    private String status;

    @CodeGeneratorFieldDescriptor(fieldDescription = "record created at")
    @Column(name = "dt_created")
    private Date dateCreated;

    @CodeGeneratorFieldDescriptor(fieldDescription = "record updated at")
    @Column(name = "dt_updated")
    private Date dateUpdated;



}

