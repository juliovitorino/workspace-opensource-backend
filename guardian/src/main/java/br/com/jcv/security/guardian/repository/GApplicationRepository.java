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


package br.com.jcv.security.guardian.repository;

import java.util.List;
import br.com.jcv.security.guardian.model.GApplication;
import br.com.jcv.security.guardian.constantes.GApplicationConstantes;
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
* GApplicationRepository - Interface dos métodos de acesso aos dados da tabela tb_application
* Camada de dados GApplication - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor GApplication
* @since Wed Nov 15 11:12:12 BRT 2023
*
*/
@Repository
public interface GApplicationRepository extends JpaRepository<GApplication, Long>
{
    @Query(value = "SELECT * FROM tb_application WHERE  status = :status", nativeQuery = true)
    List<GApplication> findAllByStatus(@Param(GApplicationConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_application WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_application = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:externalCodeUUID as TEXT) IS NULL OR cd_external = :externalCodeUUID) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<GApplication> findGApplicationByFilter(Pageable pageable,
        @Param(GApplicationConstantes.ID) Long id,
        @Param(GApplicationConstantes.NAME) String name,
        @Param(GApplicationConstantes.EXTERNALCODEUUID) UUID externalCodeUUID,
        @Param(GApplicationConstantes.STATUS) String status,
        @Param(GApplicationConstantes.DATECREATED) String dateCreated,
        @Param(GApplicationConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_application WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_application = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:externalCodeUUID as TEXT) IS NULL OR cd_external = :externalCodeUUID) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<GApplication> findGApplicationByFilter(
        @Param(GApplicationConstantes.ID) Long id,
        @Param(GApplicationConstantes.NAME) String name,
        @Param(GApplicationConstantes.EXTERNALCODEUUID) UUID externalCodeUUID,
        @Param(GApplicationConstantes.STATUS) String status,
        @Param(GApplicationConstantes.DATECREATED) String dateCreated,
        @Param(GApplicationConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_application) AS maxid FROM tb_application WHERE id_application = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_application) AS maxid FROM tb_application WHERE tx_name = :name AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNameAndStatus(String name, String status);
     @Query(value = "SELECT MAX(id_application) AS maxid FROM tb_application WHERE cd_external = :externalCodeUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status);
     @Query(value = "SELECT MAX(id_application) AS maxid FROM tb_application WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_application) AS maxid FROM tb_application WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_application SET tx_name = :name, dt_updated = current_timestamp  WHERE id_application = :id", nativeQuery = true)
     void updateNameById(@Param("id") Long id, @Param(GApplicationConstantes.NAME) String name);
     @Modifying
     @Query(value = "UPDATE tb_application SET cd_external = :externalCodeUUID, dt_updated = current_timestamp  WHERE id_application = :id", nativeQuery = true)
     void updateExternalCodeUUIDById(@Param("id") Long id, @Param(GApplicationConstantes.EXTERNALCODEUUID) UUID externalCodeUUID);
     @Modifying
     @Query(value = "UPDATE tb_application SET status = :status, dt_updated = current_timestamp  WHERE id_application = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(GApplicationConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByNameAndStatus(String name, String status);
     long countByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_application WHERE id_application = (SELECT MAX(id_application) AS maxid FROM tb_application WHERE id_application = :id AND  status = :status) ", nativeQuery = true)
    Optional<GApplication> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_application WHERE id_application = (SELECT MAX(id_application) AS maxid FROM tb_application WHERE tx_name = :name AND  status = :status) ", nativeQuery = true)
    Optional<GApplication> findByNameAndStatus(String name, String status);
    @Query(value = "SELECT * FROM tb_application WHERE id_application = (SELECT MAX(id_application) AS maxid FROM tb_application WHERE cd_external = :externalCodeUUID AND  status = :status) ", nativeQuery = true)
    Optional<GApplication> findByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status);
    @Query(value = "SELECT * FROM tb_application WHERE id_application = (SELECT MAX(id_application) AS maxid FROM tb_application WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<GApplication> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_application WHERE id_application = (SELECT MAX(id_application) AS maxid FROM tb_application WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<GApplication> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_application WHERE id_application = :id AND  status = :status ", nativeQuery = true)
     List<GApplication> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_application WHERE tx_name = :name AND  status = :status ", nativeQuery = true)
     List<GApplication> findAllByNameAndStatus(String name, String status);
     @Query(value = "SELECT * FROM tb_application WHERE cd_external = :externalCodeUUID AND  status = :status ", nativeQuery = true)
     List<GApplication> findAllByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status);
     @Query(value = "SELECT * FROM tb_application WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<GApplication> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_application WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<GApplication> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_application WHERE id_application = :id", nativeQuery = true)
    void deleteById(@Param(GApplicationConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_application WHERE tx_name = :name", nativeQuery = true)
    void deleteByName(@Param(GApplicationConstantes.NAME) String name);
    @Modifying
    @Query(value = "DELETE FROM tb_application WHERE cd_external = :externalCodeUUID", nativeQuery = true)
    void deleteByExternalCodeUUID(@Param(GApplicationConstantes.EXTERNALCODEUUID) UUID externalCodeUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_application WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(GApplicationConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_application WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(GApplicationConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_application WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(GApplicationConstantes.DATEUPDATED) Date dateUpdated);

}
