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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jcv.brcities.corelayer.model.Uf;
import br.com.jcv.brcities.infrastructure.constantes.UfConstantes;

/**
*
* UfRepository - Interface dos métodos de acesso aos dados da tabela uf
* Camada de dados Uf - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor Uf
* @since Mon Jun 03 16:53:27 BRT 2024
*
*/
@Repository
public interface UfRepository extends JpaRepository<Uf, Long>
{
    @Query(value = "SELECT * FROM uf WHERE  status = :status", nativeQuery = true)
    List<Uf> findAllByStatus(@Param(UfConstantes.STATUS) String status);

@Query(value = "SELECT * FROM uf WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR nm_uf = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<Uf> findUfByFilter(Pageable pageable,
        @Param(UfConstantes.ID) Long id,
        @Param(UfConstantes.NAME) String name,
        @Param(UfConstantes.STATUS) String status,
        @Param(UfConstantes.DATECREATED) String dateCreated,
        @Param(UfConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM uf WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR nm_uf = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<Uf> findUfByFilter(
        @Param(UfConstantes.ID) Long id,
        @Param(UfConstantes.NAME) String name,
        @Param(UfConstantes.STATUS) String status,
        @Param(UfConstantes.DATECREATED) String dateCreated,
        @Param(UfConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM uf WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM uf WHERE nm_uf = :name AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNameAndStatus(String name, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM uf WHERE dt_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM uf WHERE dt_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE uf SET nm_uf = :name, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateNameById(@Param("id") Long id, @Param(UfConstantes.NAME) String name);
     @Modifying
     @Query(value = "UPDATE uf SET status = :status, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(UfConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByNameAndStatus(String name, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM uf WHERE id = (SELECT MAX(id) AS maxid FROM uf WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<Uf> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM uf WHERE id = (SELECT MAX(id) AS maxid FROM uf WHERE nm_uf = :name AND  status = :status) ", nativeQuery = true)
    Optional<Uf> findByNameAndStatus(String name, String status);
    @Query(value = "SELECT * FROM uf WHERE id = (SELECT MAX(id) AS maxid FROM uf WHERE dt_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Uf> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM uf WHERE id = (SELECT MAX(id) AS maxid FROM uf WHERE dt_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Uf> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM uf WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<Uf> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM uf WHERE nm_uf = :name AND  status = :status ", nativeQuery = true)
     List<Uf> findAllByNameAndStatus(String name, String status);
     @Query(value = "SELECT * FROM uf WHERE dt_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Uf> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM uf WHERE dt_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Uf> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM uf WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(UfConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM uf WHERE nm_uf = :name", nativeQuery = true)
    void deleteByName(@Param(UfConstantes.NAME) String name);
    @Modifying
    @Query(value = "DELETE FROM uf WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(UfConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM uf WHERE dt_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(UfConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM uf WHERE dt_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(UfConstantes.DATEUPDATED) Date dateUpdated);

}
