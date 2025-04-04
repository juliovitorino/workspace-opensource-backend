package br.com.jcv.brcities.corelayer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorDescriptor;
import br.com.jcv.codegen.codegenerator.annotation.CodeGeneratorFieldDescriptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@CodeGeneratorDescriptor(outputDir = "/home/julio/workspaces/workspace-opensource-backend/brazilian-cities/src/main/resources",
        project = "",
        fullDescription = "Control all city and federation unit")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "city_item")
public class CityUf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @CodeGeneratorFieldDescriptor(fieldReferenceInDto = "id",fieldDescription = "City x UF primary key")
    private Long id;

    @Column(name = "id_city")
    @CodeGeneratorFieldDescriptor(fieldDescription = "City Id")
    private Long cityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city", nullable = false, insertable = false, updatable = false)
    private City city;

    @Column(name = "id_uf")
    @CodeGeneratorFieldDescriptor(fieldDescription = "UF Id")
    private String ufId;

    @Column(name = "tx_ddd")
    @CodeGeneratorFieldDescriptor(fieldDescription = "DDD Number")
    private String ddd;

    @Column(name = "nr_lat")
    @CodeGeneratorFieldDescriptor(fieldDescription = "GEO Latitude")
    private Double latitude;

    @Column(name = "nr_long")
    @CodeGeneratorFieldDescriptor(fieldDescription = "GEO Longitude")
    private Double longitude;

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

