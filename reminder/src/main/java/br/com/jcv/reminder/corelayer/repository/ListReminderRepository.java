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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jcv.reminder.corelayer.model.ListReminder;
import br.com.jcv.reminder.infrastructure.constantes.ListReminderConstantes;

/**
*
* ListReminderRepository - Interface dos métodos de acesso aos dados da tabela tb_list
* Camada de dados ListReminder - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor ListReminder
* @since Sat May 11 14:30:00 BRT 2024
*
*/
@Repository
public interface ListReminderRepository extends JpaRepository<ListReminder, Long>
{
    @Query(value = "SELECT * FROM tb_list WHERE  status = :status", nativeQuery = true)
    List<ListReminder> findAllByStatus(@Param(ListReminderConstantes.STATUS) String status);

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
Page<ListReminder> findListReminderByFilter(Pageable pageable,
        @Param(ListReminderConstantes.ID) Long id,
        @Param(ListReminderConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(ListReminderConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(ListReminderConstantes.UUIDEXTERNALLIST) UUID uuidExternalList,
        @Param(ListReminderConstantes.TITLE) String title,
        @Param(ListReminderConstantes.STATUS) String status,
        @Param(ListReminderConstantes.DATECREATED) String dateCreated,
        @Param(ListReminderConstantes.DATEUPDATED) String dateUpdated

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
List<ListReminder> findListReminderByFilter(
        @Param(ListReminderConstantes.ID) Long id,
        @Param(ListReminderConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(ListReminderConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(ListReminderConstantes.UUIDEXTERNALLIST) UUID uuidExternalList,
        @Param(ListReminderConstantes.TITLE) String title,
        @Param(ListReminderConstantes.STATUS) String status,
        @Param(ListReminderConstantes.DATECREATED) String dateCreated,
        @Param(ListReminderConstantes.DATEUPDATED) String dateUpdated

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
     void updateUuidExternalAppById(@Param("id") Long id, @Param(ListReminderConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
     @Modifying
     @Query(value = "UPDATE tb_list SET uuid_external_user = :uuidExternalUser, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateUuidExternalUserById(@Param("id") Long id, @Param(ListReminderConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
     @Modifying
     @Query(value = "UPDATE tb_list SET uuid_external_list = :uuidExternalList, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateUuidExternalListById(@Param("id") Long id, @Param(ListReminderConstantes.UUIDEXTERNALLIST) UUID uuidExternalList);
     @Modifying
     @Query(value = "UPDATE tb_list SET tx_title = :title, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateTitleById(@Param("id") Long id, @Param(ListReminderConstantes.TITLE) String title);
     @Modifying
     @Query(value = "UPDATE tb_list SET status = :status, dt_updated = current_timestamp  WHERE id_list = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(ListReminderConstantes.STATUS) String status);

    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE id_list = :id AND  status = :status) ", nativeQuery = true)
    Optional<ListReminder> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_app = :uuidExternalApp AND  status = :status) ", nativeQuery = true)
    Optional<ListReminder> findByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_user = :uuidExternalUser AND  status = :status) ", nativeQuery = true)
    Optional<ListReminder> findByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE uuid_external_list = :uuidExternalList AND  status = :status) ", nativeQuery = true)
    Optional<ListReminder> findByUuidExternalListAndStatus(UUID uuidExternalList, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE tx_title = :title AND  status = :status) ", nativeQuery = true)
    Optional<ListReminder> findByTitleAndStatus(String title, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<ListReminder> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_list WHERE id_list = (SELECT MAX(id_list) AS maxid FROM tb_list WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<ListReminder> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_list WHERE id_list = :id AND  status = :status ", nativeQuery = true)
     List<ListReminder> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_list WHERE uuid_external_app = :uuidExternalApp AND  status = :status ", nativeQuery = true)
     List<ListReminder> findAllByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT * FROM tb_list WHERE uuid_external_user = :uuidExternalUser AND  status = :status ", nativeQuery = true)
     List<ListReminder> findAllByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     @Query(value = "SELECT * FROM tb_list WHERE uuid_external_list = :uuidExternalList AND  status = :status ", nativeQuery = true)
     List<ListReminder> findAllByUuidExternalListAndStatus(UUID uuidExternalList, String status);
     @Query(value = "SELECT * FROM tb_list WHERE tx_title = :title AND  status = :status ", nativeQuery = true)
     List<ListReminder> findAllByTitleAndStatus(String title, String status);
     @Query(value = "SELECT * FROM tb_list WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<ListReminder> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_list WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<ListReminder> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE id_list = :id", nativeQuery = true)
    void deleteById(@Param(ListReminderConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE uuid_external_app = :uuidExternalApp", nativeQuery = true)
    void deleteByUuidExternalApp(@Param(ListReminderConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE uuid_external_user = :uuidExternalUser", nativeQuery = true)
    void deleteByUuidExternalUser(@Param(ListReminderConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE uuid_external_list = :uuidExternalList", nativeQuery = true)
    void deleteByUuidExternalList(@Param(ListReminderConstantes.UUIDEXTERNALLIST) UUID uuidExternalList);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE tx_title = :title", nativeQuery = true)
    void deleteByTitle(@Param(ListReminderConstantes.TITLE) String title);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(ListReminderConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(ListReminderConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_list WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(ListReminderConstantes.DATEUPDATED) Date dateUpdated);

}
