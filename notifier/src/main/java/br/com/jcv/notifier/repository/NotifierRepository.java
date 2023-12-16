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


package br.com.jcv.notifier.repository;

import java.util.List;
import br.com.jcv.notifier.model.Notifier;
import br.com.jcv.notifier.constantes.NotifierConstantes;
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
* NotifierRepository - Interface dos métodos de acesso aos dados da tabela tb_notifier
* Camada de dados Notifier - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor Notifier
* @since Sat Dec 16 12:29:21 BRT 2023
*
*/
@Repository
public interface NotifierRepository extends JpaRepository<Notifier, Long>
{
    @Query(value = "SELECT * FROM tb_notifier WHERE  status = :status", nativeQuery = true)
    List<Notifier> findAllByStatus(@Param(NotifierConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_notifier WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_notifier = :id) " +
        "AND (cast(:applicationUUID as TEXT) IS NULL OR id_application_uuid = :applicationUUID) " +
        "AND (cast(:userUUID as TEXT) IS NULL OR id_user_uuid = :userUUID) " +
        "AND (cast(:type as TEXT) IS NULL OR in_type = :type) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:description as TEXT) IS NULL OR tx_description = :description) " +
        "AND (cast(:isReaded as TEXT) IS NULL OR in_readed = :isReaded) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<Notifier> findNotifierByFilter(Pageable pageable,
        @Param(NotifierConstantes.ID) Long id,
        @Param(NotifierConstantes.APPLICATIONUUID) UUID applicationUUID,
        @Param(NotifierConstantes.USERUUID) UUID userUUID,
        @Param(NotifierConstantes.TYPE) String type,
        @Param(NotifierConstantes.TITLE) String title,
        @Param(NotifierConstantes.DESCRIPTION) String description,
        @Param(NotifierConstantes.ISREADED) String isReaded,
        @Param(NotifierConstantes.STATUS) String status,
        @Param(NotifierConstantes.DATECREATED) String dateCreated,
        @Param(NotifierConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_notifier WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_notifier = :id) " +
        "AND (cast(:applicationUUID as TEXT) IS NULL OR id_application_uuid = :applicationUUID) " +
        "AND (cast(:userUUID as TEXT) IS NULL OR id_user_uuid = :userUUID) " +
        "AND (cast(:type as TEXT) IS NULL OR in_type = :type) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:description as TEXT) IS NULL OR tx_description = :description) " +
        "AND (cast(:isReaded as TEXT) IS NULL OR in_readed = :isReaded) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<Notifier> findNotifierByFilter(
        @Param(NotifierConstantes.ID) Long id,
        @Param(NotifierConstantes.APPLICATIONUUID) UUID applicationUUID,
        @Param(NotifierConstantes.USERUUID) UUID userUUID,
        @Param(NotifierConstantes.TYPE) String type,
        @Param(NotifierConstantes.TITLE) String title,
        @Param(NotifierConstantes.DESCRIPTION) String description,
        @Param(NotifierConstantes.ISREADED) String isReaded,
        @Param(NotifierConstantes.STATUS) String status,
        @Param(NotifierConstantes.DATECREATED) String dateCreated,
        @Param(NotifierConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_notifier = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_application_uuid = :applicationUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByApplicationUUIDAndStatus(UUID applicationUUID, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_user_uuid = :userUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUserUUIDAndStatus(UUID userUUID, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE in_type = :type AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTypeAndStatus(String type, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_title = :title AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTitleAndStatus(String title, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_description = :description AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDescriptionAndStatus(String description, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE in_readed = :isReaded AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIsReadedAndStatus(String isReaded, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_notifier SET id_application_uuid = :applicationUUID, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateApplicationUUIDById(@Param("id") Long id, @Param(NotifierConstantes.APPLICATIONUUID) UUID applicationUUID);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET id_user_uuid = :userUUID, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateUserUUIDById(@Param("id") Long id, @Param(NotifierConstantes.USERUUID) UUID userUUID);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET in_type = :type, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateTypeById(@Param("id") Long id, @Param(NotifierConstantes.TYPE) String type);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_title = :title, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateTitleById(@Param("id") Long id, @Param(NotifierConstantes.TITLE) String title);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_description = :description, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateDescriptionById(@Param("id") Long id, @Param(NotifierConstantes.DESCRIPTION) String description);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET in_readed = :isReaded, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateIsReadedById(@Param("id") Long id, @Param(NotifierConstantes.ISREADED) String isReaded);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET status = :status, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(NotifierConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByApplicationUUIDAndStatus(UUID applicationUUID, String status);
     long countByUserUUIDAndStatus(UUID userUUID, String status);
     long countByTypeAndStatus(String type, String status);
     long countByTitleAndStatus(String title, String status);
     long countByDescriptionAndStatus(String description, String status);
     long countByIsReadedAndStatus(String isReaded, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_notifier = :id AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_application_uuid = :applicationUUID AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByApplicationUUIDAndStatus(UUID applicationUUID, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_user_uuid = :userUUID AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByUserUUIDAndStatus(UUID userUUID, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE in_type = :type AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByTypeAndStatus(String type, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_title = :title AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByTitleAndStatus(String title, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_description = :description AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByDescriptionAndStatus(String description, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE in_readed = :isReaded AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByIsReadedAndStatus(String isReaded, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = :id AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE id_application_uuid = :applicationUUID AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByApplicationUUIDAndStatus(UUID applicationUUID, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE id_user_uuid = :userUUID AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByUserUUIDAndStatus(UUID userUUID, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE in_type = :type AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByTypeAndStatus(String type, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_title = :title AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByTitleAndStatus(String title, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_description = :description AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByDescriptionAndStatus(String description, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE in_readed = :isReaded AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByIsReadedAndStatus(String isReaded, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE id_notifier = :id", nativeQuery = true)
    void deleteById(@Param(NotifierConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE id_application_uuid = :applicationUUID", nativeQuery = true)
    void deleteByApplicationUUID(@Param(NotifierConstantes.APPLICATIONUUID) UUID applicationUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE id_user_uuid = :userUUID", nativeQuery = true)
    void deleteByUserUUID(@Param(NotifierConstantes.USERUUID) UUID userUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE in_type = :type", nativeQuery = true)
    void deleteByType(@Param(NotifierConstantes.TYPE) String type);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_title = :title", nativeQuery = true)
    void deleteByTitle(@Param(NotifierConstantes.TITLE) String title);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_description = :description", nativeQuery = true)
    void deleteByDescription(@Param(NotifierConstantes.DESCRIPTION) String description);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE in_readed = :isReaded", nativeQuery = true)
    void deleteByIsReaded(@Param(NotifierConstantes.ISREADED) String isReaded);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(NotifierConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(NotifierConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(NotifierConstantes.DATEUPDATED) Date dateUpdated);

}
