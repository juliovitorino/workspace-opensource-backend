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


package br.com.jcv.reminder.corelayer.repository;

import java.util.List;
import br.com.jcv.reminder.corelayer.model.ReminderList;
import br.com.jcv.reminder.infrastructure.constantes.ReminderListConstantes;

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
* ReminderListRepository - Interface dos métodos de acesso aos dados da tabela tb_list
* Camada de dados ReminderList - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor ReminderList
* @since Sat May 11 17:44:44 BRT 2024
*
*/
@Repository
public interface ReminderListRepository extends JpaRepository<ReminderList, Long>
{
    @Query(value = "SELECT * FROM tb_list WHERE  status = :status", nativeQuery = true)
    List<ReminderList> findAllByStatus(@Param(ReminderListConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_list WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_list = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:uuidExternalUser as TEXT) IS NULL OR uuid_external_user = :uuidExternalUser) " +
        "AND (cast(:uuidExternalList as TEXT) IS NULL OR uuid_external_list = :uuidExternalList) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<ReminderList> findReminderListByFilter(Pageable pageable,
        @Param(ReminderListConstantes.ID) Long id,
        @Param(ReminderListConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(ReminderListConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(ReminderListConstantes.UUIDEXTERNALLIST) UUID uuidExternalList,
        @Param(ReminderListConstantes.TITLE) String title,
        @Param(ReminderListConstantes.STATUS) String status,
        @Param(ReminderListConstantes.DATECREATED) String dateCreated,
        @Param(ReminderListConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_list WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_list = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:uuidExternalUser as TEXT) IS NULL OR uuid_external_user = :uuidExternalUser) " +
        "AND (cast(:uuidExternalList as TEXT) IS NULL OR uuid_external_list = :uuidExternalList) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<ReminderList> findReminderListByFilter(
        @Param(ReminderListConstantes.ID) Long id,
        @Param(ReminderListConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(ReminderListConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(ReminderListConstantes.UUIDEXTERNALLIST) UUID uuidExternalList,
        @Param(ReminderListConstantes.TITLE) String title,
        @Param(ReminderListConstantes.STATUS) String status,
        @Param(ReminderListConstantes.DATECREATED) String dateCreated,
        @Param(ReminderListConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_list) AS maxid FROM tb_list WHERE id_list = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_app = :uuidExternalApp AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_user = :uuidExternalUser AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     @Query(value = "SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_list = :uuidExternalList AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalListAndStatus(UUID uuidExternalList, String status);
     @Query(value = "SELECT MAX(id_list) AS maxid FROM tb_list WHERE tx_title = :title AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTitleAndStatus(String title, String status);
     @Query(value = "SELECT MAX(id_list) AS maxid FROM tb_list WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_list) AS maxid FROM tb_list WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_list SET uuid_external_app = :uuidExternalApp, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateUuidExternalAppById(@Param("id") Long id, @Param(ReminderListConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
     @Modifying
     @Query(value = "UPDATE tb_list SET uuid_external_user = :uuidExternalUser, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateUuidExternalUserById(@Param("id") Long id, @Param(ReminderListConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
     @Modifying
     @Query(value = "UPDATE tb_list SET uuid_external_list = :uuidExternalList, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateUuidExternalListById(@Param("id") Long id, @Param(ReminderListConstantes.UUIDEXTERNALLIST) UUID uuidExternalList);
     @Modifying
     @Query(value = "UPDATE tb_list SET tx_title = :title, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateTitleById(@Param("id") Long id, @Param(ReminderListConstantes.TITLE) String title);
     @Modifying
     @Query(value = "UPDATE tb_list SET status = :status, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(ReminderListConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     long countByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     long countByUuidExternalListAndStatus(UUID uuidExternalList, String status);
     long countByTitleAndStatus(String title, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE id_list = :id AND  status = :status) ", nativeQuery = true)
    Optional<ReminderList> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_app = :uuidExternalApp AND  status = :status) ", nativeQuery = true)
    Optional<ReminderList> findByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_user = :uuidExternalUser AND  status = :status) ", nativeQuery = true)
    Optional<ReminderList> findByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_list = :uuidExternalList AND  status = :status) ", nativeQuery = true)
    Optional<ReminderList> findByUuidExternalListAndStatus(UUID uuidExternalList, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE tx_title = :title AND  status = :status) ", nativeQuery = true)
    Optional<ReminderList> findByTitleAndStatus(String title, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<ReminderList> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<ReminderList> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_list WHERE id_list = :id AND  status = :status ", nativeQuery = true)
     List<ReminderList> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_list WHERE uuid_external_app = :uuidExternalApp AND  status = :status ", nativeQuery = true)
     List<ReminderList> findAllByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT * FROM tb_list WHERE uuid_external_user = :uuidExternalUser AND  status = :status ", nativeQuery = true)
     List<ReminderList> findAllByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     @Query(value = "SELECT * FROM tb_list WHERE uuid_external_list = :uuidExternalList AND  status = :status ", nativeQuery = true)
     List<ReminderList> findAllByUuidExternalListAndStatus(UUID uuidExternalList, String status);
     @Query(value = "SELECT * FROM tb_list WHERE tx_title = :title AND  status = :status ", nativeQuery = true)
     List<ReminderList> findAllByTitleAndStatus(String title, String status);
     @Query(value = "SELECT * FROM tb_list WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<ReminderList> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_list WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<ReminderList> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE id_list = :id", nativeQuery = true)
    void deleteById(@Param(ReminderListConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE uuid_external_app = :uuidExternalApp", nativeQuery = true)
    void deleteByUuidExternalApp(@Param(ReminderListConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE uuid_external_user = :uuidExternalUser", nativeQuery = true)
    void deleteByUuidExternalUser(@Param(ReminderListConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE uuid_external_list = :uuidExternalList", nativeQuery = true)
    void deleteByUuidExternalList(@Param(ReminderListConstantes.UUIDEXTERNALLIST) UUID uuidExternalList);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE tx_title = :title", nativeQuery = true)
    void deleteByTitle(@Param(ReminderListConstantes.TITLE) String title);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(ReminderListConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(ReminderListConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(ReminderListConstantes.DATEUPDATED) Date dateUpdated);

}
