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


package br.com.jcv.brcities.corelayer.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.brcities.infrastructure.constantes.CityUfConstantes;

/**
*
* CityUfRepository - Interface dos métodos de acesso aos dados da tabela city_item
* Camada de dados CityUf - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor CityUf
* @since Wed Jun 05 13:47:11 BRT 2024
*
*/
@Repository
public interface CityUfRepository extends JpaRepository<CityUf, Long>, JpaSpecificationExecutor<CityUf>
{
    @Query(value = "SELECT * FROM city_item WHERE  status = :status", nativeQuery = true)
    List<CityUf> findAllByStatus(@Param(CityUfConstantes.STATUS) String status);

@Query(value = "SELECT * FROM city_item WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:cityId as BIGINT) IS NULL OR id_city = :cityId) " +
        "AND (cast(:ufId as TEXT) IS NULL OR id_uf = :ufId) " +
        "AND (cast(:ddd as TEXT) IS NULL OR tx_ddd = :ddd) " +
        "AND (cast(:latitude as FLOAT) IS NULL OR nr_lat = :latitude) " +
        "AND (cast(:longitude as FLOAT) IS NULL OR nr_long = :longitude) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<CityUf> findCityUfByFilter(Pageable pageable,
        @Param(CityUfConstantes.ID) Long id,
        @Param(CityUfConstantes.CITYID) Long cityId,
        @Param(CityUfConstantes.UFID) String ufId,
        @Param(CityUfConstantes.DDD) String ddd,
        @Param(CityUfConstantes.LATITUDE) Double latitude,
        @Param(CityUfConstantes.LONGITUDE) Double longitude,
        @Param(CityUfConstantes.STATUS) String status,
        @Param(CityUfConstantes.DATECREATED) String dateCreated,
        @Param(CityUfConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM city_item WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:cityId as BIGINT) IS NULL OR id_city = :cityId) " +
        "AND (cast(:ufId as TEXT) IS NULL OR id_uf = :ufId) " +
        "AND (cast(:ddd as TEXT) IS NULL OR tx_ddd = :ddd) " +
        "AND (cast(:latitude as FLOAT) IS NULL OR nr_lat = :latitude) " +
        "AND (cast(:longitude as FLOAT) IS NULL OR nr_long = :longitude) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<CityUf> findCityUfByFilter(
        @Param(CityUfConstantes.ID) Long id,
        @Param(CityUfConstantes.CITYID) Long cityId,
        @Param(CityUfConstantes.UFID) String ufId,
        @Param(CityUfConstantes.DDD) String ddd,
        @Param(CityUfConstantes.LATITUDE) Double latitude,
        @Param(CityUfConstantes.LONGITUDE) Double longitude,
        @Param(CityUfConstantes.STATUS) String status,
        @Param(CityUfConstantes.DATECREATED) String dateCreated,
        @Param(CityUfConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE id_city = :cityId AND status = :status ", nativeQuery = true)
     Long loadMaxIdByCityIdAndStatus(Long cityId, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE id_uf = :ufId AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUfIdAndStatus(String ufId, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE tx_ddd = :ddd AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDddAndStatus(String ddd, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE nr_lat = :latitude AND status = :status ", nativeQuery = true)
     Long loadMaxIdByLatitudeAndStatus(Double latitude, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE nr_long = :longitude AND status = :status ", nativeQuery = true)
     Long loadMaxIdByLongitudeAndStatus(Double longitude, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE dt_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city_item WHERE dt_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE city_item SET id_city = :cityId, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateCityIdById(@Param("id") Long id, @Param(CityUfConstantes.CITYID) Long cityId);
     @Modifying
     @Query(value = "UPDATE city_item SET id_uf = :ufId, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateUfIdById(@Param("id") Long id, @Param(CityUfConstantes.UFID) String ufId);
     @Modifying
     @Query(value = "UPDATE city_item SET tx_ddd = :ddd, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateDddById(@Param("id") Long id, @Param(CityUfConstantes.DDD) String ddd);
     @Modifying
     @Query(value = "UPDATE city_item SET nr_lat = :latitude, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateLatitudeById(@Param("id") Long id, @Param(CityUfConstantes.LATITUDE) Double latitude);
     @Modifying
     @Query(value = "UPDATE city_item SET nr_long = :longitude, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateLongitudeById(@Param("id") Long id, @Param(CityUfConstantes.LONGITUDE) Double longitude);
     @Modifying
     @Query(value = "UPDATE city_item SET status = :status, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(CityUfConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByCityIdAndStatus(Long cityId, String status);
     long countByUfIdAndStatus(String ufId, String status);
     long countByDddAndStatus(String ddd, String status);
     long countByLatitudeAndStatus(Double latitude, String status);
     long countByLongitudeAndStatus(Double longitude, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE id_city = :cityId AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByCityIdAndStatus(Long cityId, String status);
    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE id_uf = :ufId AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByUfIdAndStatus(String ufId, String status);
    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE tx_ddd = :ddd AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByDddAndStatus(String ddd, String status);
    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE nr_lat = :latitude AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByLatitudeAndStatus(Double latitude, String status);
    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE nr_long = :longitude AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByLongitudeAndStatus(Double longitude, String status);
    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE dt_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM city_item WHERE id = (SELECT MAX(id) AS maxid FROM city_item WHERE dt_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<CityUf> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM city_item WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM city_item WHERE id_city = :cityId AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByCityIdAndStatus(Long cityId, String status);
     @Query(value = "SELECT * FROM city_item WHERE id_uf = :ufId AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByUfIdAndStatus(String ufId, String status);
     @Query(value = "SELECT * FROM city_item WHERE tx_ddd = :ddd AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByDddAndStatus(String ddd, String status);
     @Query(value = "SELECT * FROM city_item WHERE nr_lat = :latitude AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByLatitudeAndStatus(Double latitude, String status);
     @Query(value = "SELECT * FROM city_item WHERE nr_long = :longitude AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByLongitudeAndStatus(Double longitude, String status);
     @Query(value = "SELECT * FROM city_item WHERE dt_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM city_item WHERE dt_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<CityUf> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM city_item WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(CityUfConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE id_city = :cityId", nativeQuery = true)
    void deleteByCityId(@Param(CityUfConstantes.CITYID) Long cityId);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE id_uf = :ufId", nativeQuery = true)
    void deleteByUfId(@Param(CityUfConstantes.UFID) String ufId);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE tx_ddd = :ddd", nativeQuery = true)
    void deleteByDdd(@Param(CityUfConstantes.DDD) String ddd);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE nr_lat = :latitude", nativeQuery = true)
    void deleteByLatitude(@Param(CityUfConstantes.LATITUDE) Double latitude);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE nr_long = :longitude", nativeQuery = true)
    void deleteByLongitude(@Param(CityUfConstantes.LONGITUDE) Double longitude);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(CityUfConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE dt_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(CityUfConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM city_item WHERE dt_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(CityUfConstantes.DATEUPDATED) Date dateUpdated);

}
