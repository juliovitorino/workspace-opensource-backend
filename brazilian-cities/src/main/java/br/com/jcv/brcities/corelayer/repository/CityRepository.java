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

import java.util.List;
import br.com.jcv.brcities.corelayer.model.City;
import br.com.jcv.brcities.infrastructure.constantes.CityConstantes;

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
* CityRepository - Interface dos métodos de acesso aos dados da tabela city
* Camada de dados City - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor City
* @since Mon Jun 03 17:37:14 BRT 2024
*
*/
@Repository
public interface CityRepository extends JpaRepository<City, Long>
{
    @Query(value = "SELECT * FROM city WHERE  status = :status", nativeQuery = true)
    List<City> findAllByStatus(@Param(CityConstantes.STATUS) String status);

@Query(value = "SELECT * FROM city WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR nm_city = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<City> findCityByFilter(Pageable pageable,
        @Param(CityConstantes.ID) Long id,
        @Param(CityConstantes.NAME) String name,
        @Param(CityConstantes.STATUS) String status,
        @Param(CityConstantes.DATECREATED) String dateCreated,
        @Param(CityConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM city WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR nm_city = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<City> findCityByFilter(
        @Param(CityConstantes.ID) Long id,
        @Param(CityConstantes.NAME) String name,
        @Param(CityConstantes.STATUS) String status,
        @Param(CityConstantes.DATECREATED) String dateCreated,
        @Param(CityConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM city WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city WHERE nm_city = :name AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNameAndStatus(String name, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city WHERE dt_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM city WHERE dt_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE city SET nm_city = :name, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateNameById(@Param("id") Long id, @Param(CityConstantes.NAME) String name);
     @Modifying
     @Query(value = "UPDATE city SET status = :status, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(CityConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByNameAndStatus(String name, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM city WHERE id = (SELECT MAX(id) AS maxid FROM city WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<City> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM city WHERE id = (SELECT MAX(id) AS maxid FROM city WHERE nm_city = :name AND  status = :status) ", nativeQuery = true)
    Optional<City> findByNameAndStatus(String name, String status);
    @Query(value = "SELECT * FROM city WHERE id = (SELECT MAX(id) AS maxid FROM city WHERE dt_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<City> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM city WHERE id = (SELECT MAX(id) AS maxid FROM city WHERE dt_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<City> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM city WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<City> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM city WHERE nm_city = :name AND  status = :status ", nativeQuery = true)
     List<City> findAllByNameAndStatus(String name, String status);
     @Query(value = "SELECT * FROM city WHERE dt_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<City> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM city WHERE dt_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<City> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM city WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(CityConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM city WHERE nm_city = :name", nativeQuery = true)
    void deleteByName(@Param(CityConstantes.NAME) String name);
    @Modifying
    @Query(value = "DELETE FROM city WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(CityConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM city WHERE dt_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(CityConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM city WHERE dt_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(CityConstantes.DATEUPDATED) Date dateUpdated);

}
