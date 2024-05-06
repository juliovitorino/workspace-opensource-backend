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


package br.com.jcv.notifier.corelayer.repository;

import java.util.List;
import br.com.jcv.notifier.corelayer.model.Notifier;
import br.com.jcv.notifier.infrastructure.constantes.NotifierConstantes;

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
* @since Mon May 06 08:02:37 BRT 2024
*
*/
@Repository
public interface NotifierRepository extends JpaRepository<Notifier, Long>
{
    @Query(value = "SELECT * FROM tb_notifier WHERE  status = :status", nativeQuery = true)
    List<Notifier> findAllByStatus(@Param(NotifierConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_notifier WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_notifier = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:uuidExternalUser as TEXT) IS NULL OR uuid_external_user = :uuidExternalUser) " +
        "AND (cast(:type as TEXT) IS NULL OR tx_type = :type) " +
        "AND (cast(:key as TEXT) IS NULL OR tx_key = :key) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:description as TEXT) IS NULL OR tx_description = :description) " +
        "AND (cast(:urlImage as TEXT) IS NULL OR tx_url_image = :urlImage) " +
        "AND (cast(:iconClass as TEXT) IS NULL OR tx_icon_class = :iconClass) " +
        "AND (cast(:urlFollow as TEXT) IS NULL OR tx_url_follow = :urlFollow) " +
        "AND (cast(:objectFree as TEXT) IS NULL OR tx_object = :objectFree) " +
        "AND (cast(:seenIndicator as TEXT) IS NULL OR in_seen = :seenIndicator) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<Notifier> findNotifierByFilter(Pageable pageable,
        @Param(NotifierConstantes.ID) Long id,
        @Param(NotifierConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(NotifierConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(NotifierConstantes.TYPE) String type,
        @Param(NotifierConstantes.KEY) String key,
        @Param(NotifierConstantes.TITLE) String title,
        @Param(NotifierConstantes.DESCRIPTION) String description,
        @Param(NotifierConstantes.URLIMAGE) String urlImage,
        @Param(NotifierConstantes.ICONCLASS) String iconClass,
        @Param(NotifierConstantes.URLFOLLOW) String urlFollow,
        @Param(NotifierConstantes.OBJECTFREE) String objectFree,
        @Param(NotifierConstantes.SEENINDICATOR) String seenIndicator,
        @Param(NotifierConstantes.STATUS) String status,
        @Param(NotifierConstantes.DATECREATED) String dateCreated,
        @Param(NotifierConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_notifier WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_notifier = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:uuidExternalUser as TEXT) IS NULL OR uuid_external_user = :uuidExternalUser) " +
        "AND (cast(:type as TEXT) IS NULL OR tx_type = :type) " +
        "AND (cast(:key as TEXT) IS NULL OR tx_key = :key) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:description as TEXT) IS NULL OR tx_description = :description) " +
        "AND (cast(:urlImage as TEXT) IS NULL OR tx_url_image = :urlImage) " +
        "AND (cast(:iconClass as TEXT) IS NULL OR tx_icon_class = :iconClass) " +
        "AND (cast(:urlFollow as TEXT) IS NULL OR tx_url_follow = :urlFollow) " +
        "AND (cast(:objectFree as TEXT) IS NULL OR tx_object = :objectFree) " +
        "AND (cast(:seenIndicator as TEXT) IS NULL OR in_seen = :seenIndicator) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<Notifier> findNotifierByFilter(
        @Param(NotifierConstantes.ID) Long id,
        @Param(NotifierConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(NotifierConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(NotifierConstantes.TYPE) String type,
        @Param(NotifierConstantes.KEY) String key,
        @Param(NotifierConstantes.TITLE) String title,
        @Param(NotifierConstantes.DESCRIPTION) String description,
        @Param(NotifierConstantes.URLIMAGE) String urlImage,
        @Param(NotifierConstantes.ICONCLASS) String iconClass,
        @Param(NotifierConstantes.URLFOLLOW) String urlFollow,
        @Param(NotifierConstantes.OBJECTFREE) String objectFree,
        @Param(NotifierConstantes.SEENINDICATOR) String seenIndicator,
        @Param(NotifierConstantes.STATUS) String status,
        @Param(NotifierConstantes.DATECREATED) String dateCreated,
        @Param(NotifierConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_notifier = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE uuid_external_app = :uuidExternalApp AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE uuid_external_user = :uuidExternalUser AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_type = :type AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTypeAndStatus(String type, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_key = :key AND status = :status ", nativeQuery = true)
     Long loadMaxIdByKeyAndStatus(String key, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_title = :title AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTitleAndStatus(String title, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_description = :description AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDescriptionAndStatus(String description, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_url_image = :urlImage AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUrlImageAndStatus(String urlImage, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_icon_class = :iconClass AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIconClassAndStatus(String iconClass, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_url_follow = :urlFollow AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUrlFollowAndStatus(String urlFollow, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_object = :objectFree AND status = :status ", nativeQuery = true)
     Long loadMaxIdByObjectFreeAndStatus(String objectFree, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE in_seen = :seenIndicator AND status = :status ", nativeQuery = true)
     Long loadMaxIdBySeenIndicatorAndStatus(String seenIndicator, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_notifier SET uuid_external_app = :uuidExternalApp, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateUuidExternalAppById(@Param("id") Long id, @Param(NotifierConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET uuid_external_user = :uuidExternalUser, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateUuidExternalUserById(@Param("id") Long id, @Param(NotifierConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_type = :type, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateTypeById(@Param("id") Long id, @Param(NotifierConstantes.TYPE) String type);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_key = :key, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateKeyById(@Param("id") Long id, @Param(NotifierConstantes.KEY) String key);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_title = :title, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateTitleById(@Param("id") Long id, @Param(NotifierConstantes.TITLE) String title);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_description = :description, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateDescriptionById(@Param("id") Long id, @Param(NotifierConstantes.DESCRIPTION) String description);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_url_image = :urlImage, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateUrlImageById(@Param("id") Long id, @Param(NotifierConstantes.URLIMAGE) String urlImage);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_icon_class = :iconClass, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateIconClassById(@Param("id") Long id, @Param(NotifierConstantes.ICONCLASS) String iconClass);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_url_follow = :urlFollow, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateUrlFollowById(@Param("id") Long id, @Param(NotifierConstantes.URLFOLLOW) String urlFollow);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET tx_object = :objectFree, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateObjectFreeById(@Param("id") Long id, @Param(NotifierConstantes.OBJECTFREE) String objectFree);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET in_seen = :seenIndicator, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateSeenIndicatorById(@Param("id") Long id, @Param(NotifierConstantes.SEENINDICATOR) String seenIndicator);
     @Modifying
     @Query(value = "UPDATE tb_notifier SET status = :status, dt_updated = current_timestamp  WHERE id_notifier = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(NotifierConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     long countByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     long countByTypeAndStatus(String type, String status);
     long countByKeyAndStatus(String key, String status);
     long countByTitleAndStatus(String title, String status);
     long countByDescriptionAndStatus(String description, String status);
     long countByUrlImageAndStatus(String urlImage, String status);
     long countByIconClassAndStatus(String iconClass, String status);
     long countByUrlFollowAndStatus(String urlFollow, String status);
     long countByObjectFreeAndStatus(String objectFree, String status);
     long countBySeenIndicatorAndStatus(String seenIndicator, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE id_notifier = :id AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE uuid_external_app = :uuidExternalApp AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE uuid_external_user = :uuidExternalUser AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_type = :type AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByTypeAndStatus(String type, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_key = :key AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByKeyAndStatus(String key, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_title = :title AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByTitleAndStatus(String title, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_description = :description AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByDescriptionAndStatus(String description, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_url_image = :urlImage AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByUrlImageAndStatus(String urlImage, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_icon_class = :iconClass AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByIconClassAndStatus(String iconClass, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_url_follow = :urlFollow AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByUrlFollowAndStatus(String urlFollow, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE tx_object = :objectFree AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByObjectFreeAndStatus(String objectFree, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE in_seen = :seenIndicator AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findBySeenIndicatorAndStatus(String seenIndicator, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = (SELECT MAX(id_notifier) AS maxid FROM tb_notifier WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Notifier> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_notifier WHERE id_notifier = :id AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE uuid_external_app = :uuidExternalApp AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE uuid_external_user = :uuidExternalUser AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_type = :type AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByTypeAndStatus(String type, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_key = :key AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByKeyAndStatus(String key, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_title = :title AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByTitleAndStatus(String title, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_description = :description AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByDescriptionAndStatus(String description, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_url_image = :urlImage AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByUrlImageAndStatus(String urlImage, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_icon_class = :iconClass AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByIconClassAndStatus(String iconClass, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_url_follow = :urlFollow AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByUrlFollowAndStatus(String urlFollow, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE tx_object = :objectFree AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByObjectFreeAndStatus(String objectFree, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE in_seen = :seenIndicator AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllBySeenIndicatorAndStatus(String seenIndicator, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_notifier WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Notifier> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE id_notifier = :id", nativeQuery = true)
    void deleteById(@Param(NotifierConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE uuid_external_app = :uuidExternalApp", nativeQuery = true)
    void deleteByUuidExternalApp(@Param(NotifierConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE uuid_external_user = :uuidExternalUser", nativeQuery = true)
    void deleteByUuidExternalUser(@Param(NotifierConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_type = :type", nativeQuery = true)
    void deleteByType(@Param(NotifierConstantes.TYPE) String type);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_key = :key", nativeQuery = true)
    void deleteByKey(@Param(NotifierConstantes.KEY) String key);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_title = :title", nativeQuery = true)
    void deleteByTitle(@Param(NotifierConstantes.TITLE) String title);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_description = :description", nativeQuery = true)
    void deleteByDescription(@Param(NotifierConstantes.DESCRIPTION) String description);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_url_image = :urlImage", nativeQuery = true)
    void deleteByUrlImage(@Param(NotifierConstantes.URLIMAGE) String urlImage);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_icon_class = :iconClass", nativeQuery = true)
    void deleteByIconClass(@Param(NotifierConstantes.ICONCLASS) String iconClass);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_url_follow = :urlFollow", nativeQuery = true)
    void deleteByUrlFollow(@Param(NotifierConstantes.URLFOLLOW) String urlFollow);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE tx_object = :objectFree", nativeQuery = true)
    void deleteByObjectFree(@Param(NotifierConstantes.OBJECTFREE) String objectFree);
    @Modifying
    @Query(value = "DELETE FROM tb_notifier WHERE in_seen = :seenIndicator", nativeQuery = true)
    void deleteBySeenIndicator(@Param(NotifierConstantes.SEENINDICATOR) String seenIndicator);
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
