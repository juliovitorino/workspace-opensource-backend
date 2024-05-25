/*
Copyright <YEAR> <COPYRIGHT HOLDER>

This software is Open Source and is under MIT license agreement

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the “Software”), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions
of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
OR OTHER DEALINGS IN THE SOFTWARE.
*/


package br.com.jcv.bei.corelayer.repository;

import java.time.LocalDate;
import java.util.List;
import br.com.jcv.bei.corelayer.model.EconomicIndex;
import br.com.jcv.bei.infrastructure.constantes.EconomicIndexConstantes;
import br.com.jcv.bei.infrastructure.enums.EconomicIndexStatusProcessEnum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.UUID;

/**
*
* EconomicIndexRepository - Interface dos métodos de acesso aos dados da tabela economic_index
* Camada de dados EconomicIndex - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor EconomicIndex
* @since Thu May 23 16:41:19 BRT 2024
*
*/
@Repository
public interface EconomicIndexRepository extends JpaRepository<EconomicIndex, Long>
{

    List<EconomicIndex> findByLastDateValueNullAndStatus(String status);

    @Query(value = "SELECT * FROM economic_index WHERE  status = :status", nativeQuery = true)
    List<EconomicIndex> findAllByStatus(@Param(EconomicIndexConstantes.STATUS) String status);

@Query(value = "SELECT * FROM economic_index WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:economicIndex as TEXT) IS NULL OR tx_economic_index = :economicIndex) " +
        "AND (cast(:bacenSerieCode as TEXT) IS NULL OR cd_serie_bacen = :bacenSerieCode) " +
        "AND (cast(:lastDateValue as DATE) IS NULL OR to_char(dt_last_date_value, 'YYYY-MM-DD') = :lastDateValue) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<EconomicIndex> findEconomicIndexByFilter(Pageable pageable,
        @Param(EconomicIndexConstantes.ID) Long id,
        @Param(EconomicIndexConstantes.ECONOMICINDEX) String economicIndex,
        @Param(EconomicIndexConstantes.BACENSERIECODE) String bacenSerieCode,
        @Param(EconomicIndexConstantes.LASTDATEVALUE) String lastDateValue,
        @Param(EconomicIndexConstantes.STATUS) String status,
        @Param(EconomicIndexConstantes.DATECREATED) String dateCreated,
        @Param(EconomicIndexConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM economic_index WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:economicIndex as TEXT) IS NULL OR tx_economic_index = :economicIndex) " +
        "AND (cast(:bacenSerieCode as TEXT) IS NULL OR cd_serie_bacen = :bacenSerieCode) " +
        "AND (cast(:lastDateValue as DATE) IS NULL OR to_char(dt_last_date_value, 'YYYY-MM-DD') = :lastDateValue) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<EconomicIndex> findEconomicIndexByFilter(
        @Param(EconomicIndexConstantes.ID) Long id,
        @Param(EconomicIndexConstantes.ECONOMICINDEX) String economicIndex,
        @Param(EconomicIndexConstantes.BACENSERIECODE) String bacenSerieCode,
        @Param(EconomicIndexConstantes.LASTDATEVALUE) String lastDateValue,
        @Param(EconomicIndexConstantes.STATUS) String status,
        @Param(EconomicIndexConstantes.DATECREATED) String dateCreated,
        @Param(EconomicIndexConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index WHERE tx_economic_index = :economicIndex AND status = :status ", nativeQuery = true)
     Long loadMaxIdByEconomicIndexAndStatus(String economicIndex, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index WHERE cd_serie_bacen = :bacenSerieCode AND status = :status ", nativeQuery = true)
     Long loadMaxIdByBacenSerieCodeAndStatus(String bacenSerieCode, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index WHERE dt_last_date_value = :lastDateValue AND status = :status ", nativeQuery = true)
     Long loadMaxIdByLastDateValueAndStatus(LocalDate lastDateValue, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE economic_index SET tx_economic_index = :economicIndex, date_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateEconomicIndexById(@Param("id") Long id, @Param(EconomicIndexConstantes.ECONOMICINDEX) String economicIndex);
     @Modifying
     @Query(value = "UPDATE economic_index SET cd_serie_bacen = :bacenSerieCode, date_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateBacenSerieCodeById(@Param("id") Long id, @Param(EconomicIndexConstantes.BACENSERIECODE) String bacenSerieCode);
     @Modifying
     @Query(value = "UPDATE economic_index SET dt_last_date_value = :lastDateValue, date_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateLastDateValueById(@Param("id") Long id, @Param(EconomicIndexConstantes.LASTDATEVALUE) LocalDate lastDateValue);
     @Modifying
     @Query(value = "UPDATE economic_index SET status_process = :statusProcess, date_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusProcessById(@Param("id") Long id, @Param("statusProcess") String statusProcess);
     @Modifying
     @Query(value = "UPDATE economic_index SET status = :status, date_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(EconomicIndexConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByEconomicIndexAndStatus(String economicIndex, String status);
     long countByBacenSerieCodeAndStatus(String bacenSerieCode, String status);
     long countByLastDateValueAndStatus(LocalDate lastDateValue, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM economic_index WHERE id = (SELECT MAX(id) AS maxid FROM economic_index WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndex> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM economic_index WHERE id = (SELECT MAX(id) AS maxid FROM economic_index WHERE tx_economic_index = :economicIndex AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndex> findByEconomicIndexAndStatus(String economicIndex, String status);
    @Query(value = "SELECT * FROM economic_index WHERE id = (SELECT MAX(id) AS maxid FROM economic_index WHERE cd_serie_bacen = :bacenSerieCode AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndex> findByBacenSerieCodeAndStatus(String bacenSerieCode, String status);
    @Query(value = "SELECT * FROM economic_index WHERE id = (SELECT MAX(id) AS maxid FROM economic_index WHERE dt_last_date_value = :lastDateValue AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndex> findByLastDateValueAndStatus(LocalDate lastDateValue, String status);
    @Query(value = "SELECT * FROM economic_index WHERE id = (SELECT MAX(id) AS maxid FROM economic_index WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndex> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM economic_index WHERE id = (SELECT MAX(id) AS maxid FROM economic_index WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndex> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM economic_index WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<EconomicIndex> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM economic_index WHERE tx_economic_index = :economicIndex AND  status = :status ", nativeQuery = true)
     List<EconomicIndex> findAllByEconomicIndexAndStatus(String economicIndex, String status);
     @Query(value = "SELECT * FROM economic_index WHERE cd_serie_bacen = :bacenSerieCode AND  status = :status ", nativeQuery = true)
     List<EconomicIndex> findAllByBacenSerieCodeAndStatus(String bacenSerieCode, String status);
     @Query(value = "SELECT * FROM economic_index WHERE dt_last_date_value = :lastDateValue AND  status = :status ", nativeQuery = true)
     List<EconomicIndex> findAllByLastDateValueAndStatus(LocalDate lastDateValue, String status);
     @Query(value = "SELECT * FROM economic_index WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<EconomicIndex> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM economic_index WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<EconomicIndex> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM economic_index WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(EconomicIndexConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM economic_index WHERE tx_economic_index = :economicIndex", nativeQuery = true)
    void deleteByEconomicIndex(@Param(EconomicIndexConstantes.ECONOMICINDEX) String economicIndex);
    @Modifying
    @Query(value = "DELETE FROM economic_index WHERE cd_serie_bacen = :bacenSerieCode", nativeQuery = true)
    void deleteByBacenSerieCode(@Param(EconomicIndexConstantes.BACENSERIECODE) String bacenSerieCode);
    @Modifying
    @Query(value = "DELETE FROM economic_index WHERE dt_last_date_value = :lastDateValue", nativeQuery = true)
    void deleteByLastDateValue(@Param(EconomicIndexConstantes.LASTDATEVALUE) LocalDate lastDateValue);
    @Modifying
    @Query(value = "DELETE FROM economic_index WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(EconomicIndexConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM economic_index WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(EconomicIndexConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM economic_index WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(EconomicIndexConstantes.DATEUPDATED) Date dateUpdated);

}
