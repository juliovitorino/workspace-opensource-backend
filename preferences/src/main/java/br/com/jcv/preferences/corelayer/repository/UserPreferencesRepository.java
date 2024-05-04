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


package br.com.jcv.preferences.corelayer.repository;

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

import br.com.jcv.preferences.corelayer.model.UserPreferences;
import br.com.jcv.preferences.infrastructure.constantes.UserPreferencesConstantes;

/**
*
* UserPreferencesRepository - Interface dos métodos de acesso aos dados da tabela tb_user_preferences
* Camada de dados UserPreferences - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor UserPreferences
* @since Mon Apr 29 16:40:18 BRT 2024
*
*/
@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long>
{
    @Query(value = "SELECT * FROM tb_user_preferences WHERE  status = :status", nativeQuery = true)
    List<UserPreferences> findAllByStatus(@Param(UserPreferencesConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_user_preferences WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_system_preference = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:uuidExternalUser as TEXT) IS NULL OR uuid_external_user = :uuidExternalUser) " +
        "AND (cast(:key as TEXT) IS NULL OR tx_key = :key) " +
        "AND (cast(:preference as TEXT) IS NULL OR tx_preference = :preference) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<UserPreferences> findUserPreferencesByFilter(Pageable pageable,
        @Param(UserPreferencesConstantes.ID) Long id,
        @Param(UserPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(UserPreferencesConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(UserPreferencesConstantes.KEY) String key,
        @Param(UserPreferencesConstantes.PREFERENCE) String preference,
        @Param(UserPreferencesConstantes.STATUS) String status,
        @Param(UserPreferencesConstantes.DATECREATED) String dateCreated,
        @Param(UserPreferencesConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_user_preferences WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_system_preference = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:uuidExternalUser as TEXT) IS NULL OR uuid_external_user = :uuidExternalUser) " +
        "AND (cast(:key as TEXT) IS NULL OR tx_key = :key) " +
        "AND (cast(:preference as TEXT) IS NULL OR tx_preference = :preference) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<UserPreferences> findUserPreferencesByFilter(
        @Param(UserPreferencesConstantes.ID) Long id,
        @Param(UserPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(UserPreferencesConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser,
        @Param(UserPreferencesConstantes.KEY) String key,
        @Param(UserPreferencesConstantes.PREFERENCE) String preference,
        @Param(UserPreferencesConstantes.STATUS) String status,
        @Param(UserPreferencesConstantes.DATECREATED) String dateCreated,
        @Param(UserPreferencesConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE id_system_preference = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE uuid_external_app = :uuidExternalApp AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE uuid_external_user = :uuidExternalUser AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE tx_key = :key AND status = :status ", nativeQuery = true)
     Long loadMaxIdByKeyAndStatus(String key, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE tx_preference = :preference AND status = :status ", nativeQuery = true)
     Long loadMaxIdByPreferenceAndStatus(String preference, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_user_preferences SET uuid_external_app = :uuidExternalApp, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updateUuidExternalAppById(@Param("id") Long id, @Param(UserPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
     @Modifying
     @Query(value = "UPDATE tb_user_preferences SET uuid_external_user = :uuidExternalUser, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updateUuidExternalUserById(@Param("id") Long id, @Param(UserPreferencesConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
     @Modifying
     @Query(value = "UPDATE tb_user_preferences SET tx_key = :key, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updateKeyById(@Param("id") Long id, @Param(UserPreferencesConstantes.KEY) String key);
     @Modifying
     @Query(value = "UPDATE tb_user_preferences SET tx_preference = :preference, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updatePreferenceById(@Param("id") Long id, @Param(UserPreferencesConstantes.PREFERENCE) String preference);
     @Modifying
     @Query(value = "UPDATE tb_user_preferences SET status = :status, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(UserPreferencesConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     long countByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     long countByKeyAndStatus(String key, String status);
     long countByPreferenceAndStatus(String preference, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE id_system_preference = :id AND  status = :status) ", nativeQuery = true)
    Optional<UserPreferences> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE uuid_external_app = :uuidExternalApp AND  status = :status) ", nativeQuery = true)
    Optional<UserPreferences> findByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE uuid_external_user = :uuidExternalUser AND  status = :status) ", nativeQuery = true)
    Optional<UserPreferences> findByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE tx_key = :key AND  status = :status) ", nativeQuery = true)
    Optional<UserPreferences> findByKeyAndStatus(String key, String status);
    @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE tx_preference = :preference AND  status = :status) ", nativeQuery = true)
    Optional<UserPreferences> findByPreferenceAndStatus(String preference, String status);
    @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<UserPreferences> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_user_preferences WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<UserPreferences> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_user_preferences WHERE id_system_preference = :id AND  status = :status ", nativeQuery = true)
     List<UserPreferences> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_user_preferences WHERE uuid_external_app = :uuidExternalApp AND  status = :status ", nativeQuery = true)
     List<UserPreferences> findAllByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT * FROM tb_user_preferences WHERE uuid_external_user = :uuidExternalUser AND  status = :status ", nativeQuery = true)
     List<UserPreferences> findAllByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
     @Query(value = "SELECT * FROM tb_user_preferences WHERE tx_key = :key AND  status = :status ", nativeQuery = true)
     List<UserPreferences> findAllByKeyAndStatus(String key, String status);
     @Query(value = "SELECT * FROM tb_user_preferences WHERE tx_preference = :preference AND  status = :status ", nativeQuery = true)
     List<UserPreferences> findAllByPreferenceAndStatus(String preference, String status);
     @Query(value = "SELECT * FROM tb_user_preferences WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<UserPreferences> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_user_preferences WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<UserPreferences> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE id_system_preference = :id", nativeQuery = true)
    void deleteById(@Param(UserPreferencesConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE uuid_external_app = :uuidExternalApp", nativeQuery = true)
    void deleteByUuidExternalApp(@Param(UserPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE uuid_external_user = :uuidExternalUser", nativeQuery = true)
    void deleteByUuidExternalUser(@Param(UserPreferencesConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser);
    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE tx_key = :key", nativeQuery = true)
    void deleteByKey(@Param(UserPreferencesConstantes.KEY) String key);
    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE tx_preference = :preference", nativeQuery = true)
    void deleteByPreference(@Param(UserPreferencesConstantes.PREFERENCE) String preference);
    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(UserPreferencesConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(UserPreferencesConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_user_preferences WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(UserPreferencesConstantes.DATEUPDATED) Date dateUpdated);

}
