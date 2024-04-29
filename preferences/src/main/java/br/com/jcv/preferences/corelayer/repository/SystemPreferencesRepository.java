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

import br.com.jcv.preferences.corelayer.model.SystemPreferences;
import br.com.jcv.preferences.infrastructure.constantes.SystemPreferencesConstantes;

/**
*
* SystemPreferencesRepository - Interface dos métodos de acesso aos dados da tabela tb_system_preferences
* Camada de dados SystemPreferences - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor SystemPreferences
* @since Mon Apr 29 15:32:50 BRT 2024
*
*/
@Repository
public interface SystemPreferencesRepository extends JpaRepository<SystemPreferences, Long>
{
    @Query(value = "SELECT * FROM tb_system_preferences WHERE  status = :status", nativeQuery = true)
    List<SystemPreferences> findAllByStatus(@Param(SystemPreferencesConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_system_preferences WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_system_preference = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:key as TEXT) IS NULL OR tx_key = :key) " +
        "AND (cast(:preference as TEXT) IS NULL OR tx_preference = :preference) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<SystemPreferences> findSystemPreferencesByFilter(Pageable pageable,
        @Param(SystemPreferencesConstantes.ID) Long id,
        @Param(SystemPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(SystemPreferencesConstantes.KEY) String key,
        @Param(SystemPreferencesConstantes.PREFERENCE) String preference,
        @Param(SystemPreferencesConstantes.STATUS) String status,
        @Param(SystemPreferencesConstantes.DATECREATED) String dateCreated,
        @Param(SystemPreferencesConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_system_preferences WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_system_preference = :id) " +
        "AND (cast(:uuidExternalApp as TEXT) IS NULL OR uuid_external_app = :uuidExternalApp) " +
        "AND (cast(:key as TEXT) IS NULL OR tx_key = :key) " +
        "AND (cast(:preference as TEXT) IS NULL OR tx_preference = :preference) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<SystemPreferences> findSystemPreferencesByFilter(
        @Param(SystemPreferencesConstantes.ID) Long id,
        @Param(SystemPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp,
        @Param(SystemPreferencesConstantes.KEY) String key,
        @Param(SystemPreferencesConstantes.PREFERENCE) String preference,
        @Param(SystemPreferencesConstantes.STATUS) String status,
        @Param(SystemPreferencesConstantes.DATECREATED) String dateCreated,
        @Param(SystemPreferencesConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE id_system_preference = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE uuid_external_app = :uuidExternalApp AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE tx_key = :key AND status = :status ", nativeQuery = true)
     Long loadMaxIdByKeyAndStatus(String key, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE tx_preference = :preference AND status = :status ", nativeQuery = true)
     Long loadMaxIdByPreferenceAndStatus(String preference, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_system_preferences SET uuid_external_app = :uuidExternalApp, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updateUuidExternalAppById(@Param("id") Long id, @Param(SystemPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
     @Modifying
     @Query(value = "UPDATE tb_system_preferences SET tx_key = :key, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updateKeyById(@Param("id") Long id, @Param(SystemPreferencesConstantes.KEY) String key);
     @Modifying
     @Query(value = "UPDATE tb_system_preferences SET tx_preference = :preference, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updatePreferenceById(@Param("id") Long id, @Param(SystemPreferencesConstantes.PREFERENCE) String preference);
     @Modifying
     @Query(value = "UPDATE tb_system_preferences SET status = :status, dt_updated = current_timestamp  WHERE id_system_preference = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(SystemPreferencesConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     long countByKeyAndStatus(String key, String status);
     long countByPreferenceAndStatus(String preference, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_system_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE id_system_preference = :id AND  status = :status) ", nativeQuery = true)
    Optional<SystemPreferences> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_system_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE uuid_external_app = :uuidExternalApp AND  status = :status) ", nativeQuery = true)
    Optional<SystemPreferences> findByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    @Query(value = "SELECT * FROM tb_system_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE tx_key = :key AND  status = :status) ", nativeQuery = true)
    Optional<SystemPreferences> findByKeyAndStatus(String key, String status);
    @Query(value = "SELECT * FROM tb_system_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE tx_preference = :preference AND  status = :status) ", nativeQuery = true)
    Optional<SystemPreferences> findByPreferenceAndStatus(String preference, String status);
    @Query(value = "SELECT * FROM tb_system_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<SystemPreferences> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_system_preferences WHERE id_system_preference = (SELECT MAX(id_system_preference) AS maxid FROM tb_system_preferences WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<SystemPreferences> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_system_preferences WHERE id_system_preference = :id AND  status = :status ", nativeQuery = true)
     List<SystemPreferences> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_system_preferences WHERE uuid_external_app = :uuidExternalApp AND  status = :status ", nativeQuery = true)
     List<SystemPreferences> findAllByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
     @Query(value = "SELECT * FROM tb_system_preferences WHERE tx_key = :key AND  status = :status ", nativeQuery = true)
     List<SystemPreferences> findAllByKeyAndStatus(String key, String status);
     @Query(value = "SELECT * FROM tb_system_preferences WHERE tx_preference = :preference AND  status = :status ", nativeQuery = true)
     List<SystemPreferences> findAllByPreferenceAndStatus(String preference, String status);
     @Query(value = "SELECT * FROM tb_system_preferences WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<SystemPreferences> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_system_preferences WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<SystemPreferences> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_system_preferences WHERE id_system_preference = :id", nativeQuery = true)
    void deleteById(@Param(SystemPreferencesConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_system_preferences WHERE uuid_external_app = :uuidExternalApp", nativeQuery = true)
    void deleteByUuidExternalApp(@Param(SystemPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp);
    @Modifying
    @Query(value = "DELETE FROM tb_system_preferences WHERE tx_key = :key", nativeQuery = true)
    void deleteByKey(@Param(SystemPreferencesConstantes.KEY) String key);
    @Modifying
    @Query(value = "DELETE FROM tb_system_preferences WHERE tx_preference = :preference", nativeQuery = true)
    void deleteByPreference(@Param(SystemPreferencesConstantes.PREFERENCE) String preference);
    @Modifying
    @Query(value = "DELETE FROM tb_system_preferences WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(SystemPreferencesConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_system_preferences WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(SystemPreferencesConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_system_preferences WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(SystemPreferencesConstantes.DATEUPDATED) Date dateUpdated);

}
