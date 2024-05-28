package br.com.jcv.reaction.corelayer.model;

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
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/reaction/src/main/resources",
        project = "",
        fullDescription = "Control all general groups")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_group_reaction")
public class GroupReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "group primary key")
    private Long id;

    @Column(name = "id_group")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Group Id")
    private Long groupId;

    @Column(name = "id_reaction")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Reaction Id")
    private Long reactionId;

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
