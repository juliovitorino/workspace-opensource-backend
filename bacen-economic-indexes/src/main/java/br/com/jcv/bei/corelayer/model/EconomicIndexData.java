package br.com.jcv.bei.corelayer.model;

import java.time.LocalDate;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="economic_index_data")
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/bacen-economic-indexes/src/main/resources",
        project = "",
        fullDescription = "Control all notifications")
public class EconomicIndexData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "primary key")
    private Long id;

    @Column(name = "id_economic_index")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Foreign Key for Economic Index")
    private Long economicIndexId;

    @Column(name = "dt_economic_index")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Index date")
    private LocalDate indexDate;

    @Column(name = "vl_economic_index")
    @CodeGeneratorFieldDescriptor(fieldDescription = "Index value")
    private Double indexValue;

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
