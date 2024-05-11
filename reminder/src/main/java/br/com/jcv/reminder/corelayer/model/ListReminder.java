package br.com.jcv.reminder.corelayer.model;

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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="tb_list")
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/reminder/src/main/resources",
        project = "",
        fullDescription = "Control all reminder list")
public class ListReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_list")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "uuid_external_app")
    @CodeGeneratorFieldDescriptor(fieldDescription = "External Application UUID reference")
    private UUID uuidExternalApp;

    @Column(name = "uuid_external_user")
    @CodeGeneratorFieldDescriptor(fieldDescription = "External User UUID reference")
    private UUID uuidExternalUser;

    @Column(name = "uuid_external_list")
    @CodeGeneratorFieldDescriptor(fieldDescription = "External Reminder List UUID reference")
    private UUID uuidExternalList;

    @Column(name = "tx_title")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Title for reminder list")
    private String title;

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
