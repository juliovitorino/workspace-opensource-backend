package br.com.jcv.reaction.corelayer.model;

import java.util.Date;
import java.util.UUID;

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
@Table(name = "tb_reaction_event")
public class ReactionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "group primary key")
    private Long id;

    @Column(name = "id_reaction")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Group name")
    private Long reactionId;

    @Column(name = "uuid_external_item")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Item UUID")
    private UUID externalItemUUID;

    @Column(name = "uuid_external_app")
    @CodeGeneratorFieldDescriptor(fieldDescription = "App UUID")
    private UUID externalAppUUID;

    @Column(name = "uuid_external_user")
    @CodeGeneratorFieldDescriptor(fieldDescription = "User UUID")
    private UUID externalUserUUID;

    @Column(name = "tx_hash_md5")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Reaction MD5 hash")
    private String hashMD5;

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
