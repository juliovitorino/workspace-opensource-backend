package br.com.jcv.reminder.corelayer.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorDescriptor;
import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorFieldDescriptor;
import br.com.jcv.reminder.infrastructure.enums.EnumPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="tb_reminder")
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/reminder/src/main/resources",
        project = "",
        fullDescription = "Control all reminder item")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reminder")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "id_list")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Reminder list foreign key")
    private Long idList;

    @Column(name = "tx_title")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Title for reminder")
    private String title;

    @Column(name = "tx_note")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Note for reminder")
    private String note;

    @Column(name = "tx_tags")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Tags for reminder")
    private String tags;

    @Column(name = "tx_full_url_image")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Any full url image for reminder")
    private String fullUrlImage;

    @Column(name = "tx_url")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Any url for reminder")
    private String url;

    @Column(name = "in_priority")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Priority (High/Lower) for reminder")
    @Enumerated(value = EnumType.STRING)
    private EnumPriority priority;

    @Column(name = "in_flag")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Flagged for reminder")
    private Boolean flag;

    @Column(name = "dt_duedate")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Due date reminder")
    private Date dueDate;

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
