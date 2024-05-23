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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jcv.bei.corelayer.model.EconomicIndexData;
import br.com.jcv.bei.infrastructure.constantes.EconomicIndexDataConstantes;

/**
*
* EconomicIndexDataRepository - Interface dos métodos de acesso aos dados da tabela economic_index_data
* Camada de dados EconomicIndexData - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor EconomicIndexData
* @since Thu May 23 17:29:29 BRT 2024
*
*/
@Repository
public interface EconomicIndexDataRepository extends JpaRepository<EconomicIndexData, Long>
{
    @Query(value = "SELECT * FROM economic_index_data WHERE  status = :status", nativeQuery = true)
    List<EconomicIndexData> findAllByStatus(@Param(EconomicIndexDataConstantes.STATUS) String status);

@Query(value = "SELECT * FROM economic_index_data WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:economicIndexId as BIGINT) IS NULL OR id_economic_index = :economicIndexId) " +
        "AND (cast(:indexDate as DATE) IS NULL OR to_char(dt_economic_index, 'YYYY-MM-DD') = :indexDate) " +
        "AND (cast(:indexValue as FLOAT) IS NULL OR vl_economic_index = :indexValue) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<EconomicIndexData> findEconomicIndexDataByFilter(Pageable pageable,
        @Param(EconomicIndexDataConstantes.ID) Long id,
        @Param(EconomicIndexDataConstantes.ECONOMICINDEXID) Long economicIndexId,
        @Param(EconomicIndexDataConstantes.INDEXDATE) String indexDate,
        @Param(EconomicIndexDataConstantes.INDEXVALUE) Double indexValue,
        @Param(EconomicIndexDataConstantes.STATUS) String status,
        @Param(EconomicIndexDataConstantes.DATECREATED) String dateCreated,
        @Param(EconomicIndexDataConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM economic_index_data WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:economicIndexId as BIGINT) IS NULL OR id_economic_index = :economicIndexId) " +
        "AND (cast(:indexDate as DATE) IS NULL OR to_char(dt_economic_index, 'YYYY-MM-DD') = :indexDate) " +
        "AND (cast(:indexValue as FLOAT) IS NULL OR vl_economic_index = :indexValue) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<EconomicIndexData> findEconomicIndexDataByFilter(
        @Param(EconomicIndexDataConstantes.ID) Long id,
        @Param(EconomicIndexDataConstantes.ECONOMICINDEXID) Long economicIndexId,
        @Param(EconomicIndexDataConstantes.INDEXDATE) String indexDate,
        @Param(EconomicIndexDataConstantes.INDEXVALUE) Double indexValue,
        @Param(EconomicIndexDataConstantes.STATUS) String status,
        @Param(EconomicIndexDataConstantes.DATECREATED) String dateCreated,
        @Param(EconomicIndexDataConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index_data WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index_data WHERE id_economic_index = :economicIndexId AND status = :status ", nativeQuery = true)
     Long loadMaxIdByEconomicIndexIdAndStatus(Long economicIndexId, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index_data WHERE dt_economic_index = :indexDate AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIndexDateAndStatus(LocalDate indexDate, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index_data WHERE vl_economic_index = :indexValue AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIndexValueAndStatus(Double indexValue, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index_data WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM economic_index_data WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE economic_index_data SET id_economic_index = :economicIndexId, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateEconomicIndexIdById(@Param("id") Long id, @Param(EconomicIndexDataConstantes.ECONOMICINDEXID) Long economicIndexId);
     @Modifying
     @Query(value = "UPDATE economic_index_data SET dt_economic_index = :indexDate, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateIndexDateById(@Param("id") Long id, @Param(EconomicIndexDataConstantes.INDEXDATE) LocalDate indexDate);
     @Modifying
     @Query(value = "UPDATE economic_index_data SET vl_economic_index = :indexValue, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateIndexValueById(@Param("id") Long id, @Param(EconomicIndexDataConstantes.INDEXVALUE) Double indexValue);
     @Modifying
     @Query(value = "UPDATE economic_index_data SET status = :status, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(EconomicIndexDataConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByEconomicIndexIdAndStatus(Long economicIndexId, String status);
     long countByIndexDateAndStatus(LocalDate indexDate, String status);
     long countByIndexValueAndStatus(Double indexValue, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM economic_index_data WHERE id = (SELECT MAX(id) AS maxid FROM economic_index_data WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndexData> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM economic_index_data WHERE id = (SELECT MAX(id) AS maxid FROM economic_index_data WHERE id_economic_index = :economicIndexId AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndexData> findByEconomicIndexIdAndStatus(Long economicIndexId, String status);
    @Query(value = "SELECT * FROM economic_index_data WHERE id = (SELECT MAX(id) AS maxid FROM economic_index_data WHERE dt_economic_index = :indexDate AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndexData> findByIndexDateAndStatus(LocalDate indexDate, String status);
    @Query(value = "SELECT * FROM economic_index_data WHERE id = (SELECT MAX(id) AS maxid FROM economic_index_data WHERE vl_economic_index = :indexValue AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndexData> findByIndexValueAndStatus(Double indexValue, String status);
    @Query(value = "SELECT * FROM economic_index_data WHERE id = (SELECT MAX(id) AS maxid FROM economic_index_data WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndexData> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM economic_index_data WHERE id = (SELECT MAX(id) AS maxid FROM economic_index_data WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<EconomicIndexData> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM economic_index_data WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<EconomicIndexData> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM economic_index_data WHERE id_economic_index = :economicIndexId AND  status = :status ", nativeQuery = true)
     List<EconomicIndexData> findAllByEconomicIndexIdAndStatus(Long economicIndexId, String status);
     @Query(value = "SELECT * FROM economic_index_data WHERE dt_economic_index = :indexDate AND  status = :status ", nativeQuery = true)
     List<EconomicIndexData> findAllByIndexDateAndStatus(LocalDate indexDate, String status);
     @Query(value = "SELECT * FROM economic_index_data WHERE vl_economic_index = :indexValue AND  status = :status ", nativeQuery = true)
     List<EconomicIndexData> findAllByIndexValueAndStatus(Double indexValue, String status);
     @Query(value = "SELECT * FROM economic_index_data WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<EconomicIndexData> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM economic_index_data WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<EconomicIndexData> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM economic_index_data WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(EconomicIndexDataConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM economic_index_data WHERE id_economic_index = :economicIndexId", nativeQuery = true)
    void deleteByEconomicIndexId(@Param(EconomicIndexDataConstantes.ECONOMICINDEXID) Long economicIndexId);
    @Modifying
    @Query(value = "DELETE FROM economic_index_data WHERE dt_economic_index = :indexDate", nativeQuery = true)
    void deleteByIndexDate(@Param(EconomicIndexDataConstantes.INDEXDATE) LocalDate indexDate);
    @Modifying
    @Query(value = "DELETE FROM economic_index_data WHERE vl_economic_index = :indexValue", nativeQuery = true)
    void deleteByIndexValue(@Param(EconomicIndexDataConstantes.INDEXVALUE) Double indexValue);
    @Modifying
    @Query(value = "DELETE FROM economic_index_data WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(EconomicIndexDataConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM economic_index_data WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(EconomicIndexDataConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM economic_index_data WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(EconomicIndexDataConstantes.DATEUPDATED) Date dateUpdated);

}
