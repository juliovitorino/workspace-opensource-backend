package br.com.jcv.bei.corelayer.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorDescriptor;
import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorFieldDescriptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="economic_index")
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/bacen-economic-indexes/src/main/resources",
        project = "",
        fullDescription = "Control all notifications")
public class EconomicIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "tx_economic_index")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Free text for economic index")
    private String economicIndex;

    @Column(name = "cd_serie_bacen")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Series code for economic index")
    private String bacenSerieCode;

    @Column(name = "dt_last_date_value")
    @CodeGeneratorFieldDescriptor(fieldDescription = "last update date for economic index")
    private LocalDate lastDateValue;

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
