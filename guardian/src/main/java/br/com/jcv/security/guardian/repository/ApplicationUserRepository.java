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
import br.com.jcv.security.guardian.model.ApplicationUser;
import br.com.jcv.security.guardian.constantes.ApplicationUserConstantes;
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
* ApplicationUserRepository - Interface dos métodos de acesso aos dados da tabela tb_application_user
* Camada de dados ApplicationUser - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor ApplicationUser
* @since Thu Nov 16 09:03:29 BRT 2023
*
*/
@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long>
{
    @Query(value = "SELECT * FROM tb_application_user WHERE  status = :status", nativeQuery = true)
    List<ApplicationUser> findAllByStatus(@Param(ApplicationUserConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_application_user WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_application_user = :id) " +
        "AND (cast(:idUser as BIGINT) IS NULL OR id_user = :idUser) " +
        "AND (cast(:email as TEXT) IS NULL OR tx_email = :email) " +
        "AND (cast(:encodedPassPhrase as TEXT) IS NULL OR tx_encoded_pass_phrase = :encodedPassPhrase) " +
        "AND (cast(:externalAppUserUUID as TEXT) IS NULL OR cd_external_uuid = :externalAppUserUUID) " +
        "AND (cast(:urlTokenActivation as TEXT) IS NULL OR cd_url_token_activation = :urlTokenActivation) " +
        "AND (cast(:activationCode as TEXT) IS NULL OR cd_activation = :activationCode) " +
        "AND (cast(:dueDateActivation as DATE) IS NULL OR to_char(dt_due_activation, 'YYYY-MM-DD') = :dueDateActivation) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<ApplicationUser> findApplicationUserByFilter(Pageable pageable,
        @Param(ApplicationUserConstantes.ID) Long id,
        @Param(ApplicationUserConstantes.IDUSER) Long idUser,
        @Param(ApplicationUserConstantes.EMAIL) String email,
        @Param(ApplicationUserConstantes.ENCODEDPASSPHRASE) String encodedPassPhrase,
        @Param(ApplicationUserConstantes.EXTERNALAPPUSERUUID) UUID externalAppUserUUID,
        @Param(ApplicationUserConstantes.URLTOKENACTIVATION) String urlTokenActivation,
        @Param(ApplicationUserConstantes.ACTIVATIONCODE) String activationCode,
        @Param(ApplicationUserConstantes.DUEDATEACTIVATION) String dueDateActivation,
        @Param(ApplicationUserConstantes.STATUS) String status,
        @Param(ApplicationUserConstantes.DATECREATED) String dateCreated,
        @Param(ApplicationUserConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_application_user WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_application_user = :id) " +
        "AND (cast(:idUser as BIGINT) IS NULL OR id_user = :idUser) " +
        "AND (cast(:email as TEXT) IS NULL OR tx_email = :email) " +
        "AND (cast(:encodedPassPhrase as TEXT) IS NULL OR tx_encoded_pass_phrase = :encodedPassPhrase) " +
        "AND (cast(:externalAppUserUUID as TEXT) IS NULL OR cd_external_uuid = :externalAppUserUUID) " +
        "AND (cast(:urlTokenActivation as TEXT) IS NULL OR cd_url_token_activation = :urlTokenActivation) " +
        "AND (cast(:activationCode as TEXT) IS NULL OR cd_activation = :activationCode) " +
        "AND (cast(:dueDateActivation as DATE) IS NULL OR to_char(dt_due_activation, 'YYYY-MM-DD') = :dueDateActivation) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<ApplicationUser> findApplicationUserByFilter(
        @Param(ApplicationUserConstantes.ID) Long id,
        @Param(ApplicationUserConstantes.IDUSER) Long idUser,
        @Param(ApplicationUserConstantes.EMAIL) String email,
        @Param(ApplicationUserConstantes.ENCODEDPASSPHRASE) String encodedPassPhrase,
        @Param(ApplicationUserConstantes.EXTERNALAPPUSERUUID) UUID externalAppUserUUID,
        @Param(ApplicationUserConstantes.URLTOKENACTIVATION) String urlTokenActivation,
        @Param(ApplicationUserConstantes.ACTIVATIONCODE) String activationCode,
        @Param(ApplicationUserConstantes.DUEDATEACTIVATION) String dueDateActivation,
        @Param(ApplicationUserConstantes.STATUS) String status,
        @Param(ApplicationUserConstantes.DATECREATED) String dateCreated,
        @Param(ApplicationUserConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE id_application_user = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE id_user = :idUser AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdUserAndStatus(Long idUser, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE tx_email = :email AND status = :status ", nativeQuery = true)
     Long loadMaxIdByEmailAndStatus(String email, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase AND status = :status ", nativeQuery = true)
     Long loadMaxIdByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE cd_external_uuid = :externalAppUserUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE cd_external_uuid = :externalAppUserUUID AND tx_email = :email AND status = :status ", nativeQuery = true)
     Long loadMaxIdByExternalAppUserUUIDAndEmailAndStatus(UUID externalAppUserUUID, String email, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE cd_url_token_activation = :urlTokenActivation AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUrlTokenActivationAndStatus(String urlTokenActivation, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE cd_activation = :activationCode AND status = :status ", nativeQuery = true)
     Long loadMaxIdByActivationCodeAndStatus(String activationCode, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE dt_due_activation = :dueDateActivation AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDueDateActivationAndStatus(Date dueDateActivation, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_application_user SET tx_email = :email, dt_updated = current_timestamp  WHERE id_application_user = :id", nativeQuery = true)
     void updateEmailById(@Param("id") Long id, @Param(ApplicationUserConstantes.EMAIL) String email);
     @Modifying
     @Query(value = "UPDATE tb_application_user SET tx_encoded_pass_phrase = :encodedPassPhrase, dt_updated = current_timestamp  WHERE id_application_user = :id", nativeQuery = true)
     void updateEncodedPassPhraseById(@Param("id") Long id, @Param(ApplicationUserConstantes.ENCODEDPASSPHRASE) String encodedPassPhrase);
     @Modifying
     @Query(value = "UPDATE tb_application_user SET cd_external_uuid = :externalAppUserUUID, dt_updated = current_timestamp  WHERE id_application_user = :id", nativeQuery = true)
     void updateExternalAppUserUUIDById(@Param("id") Long id, @Param(ApplicationUserConstantes.EXTERNALAPPUSERUUID) UUID externalAppUserUUID);
     @Modifying
     @Query(value = "UPDATE tb_application_user SET cd_url_token_activation = :urlTokenActivation, dt_updated = current_timestamp  WHERE id_application_user = :id", nativeQuery = true)
     void updateUrlTokenActivationById(@Param("id") Long id, @Param(ApplicationUserConstantes.URLTOKENACTIVATION) String urlTokenActivation);
     @Modifying
     @Query(value = "UPDATE tb_application_user SET cd_activation = :activationCode, dt_updated = current_timestamp  WHERE id_application_user = :id", nativeQuery = true)
     void updateActivationCodeById(@Param("id") Long id, @Param(ApplicationUserConstantes.ACTIVATIONCODE) String activationCode);
     @Modifying
     @Query(value = "UPDATE tb_application_user SET dt_due_activation = :dueDateActivation, dt_updated = current_timestamp  WHERE id_application_user = :id", nativeQuery = true)
     void updateDueDateActivationById(@Param("id") Long id, @Param(ApplicationUserConstantes.DUEDATEACTIVATION) Date dueDateActivation);
     @Modifying
     @Query(value = "UPDATE tb_application_user SET status = :status, dt_updated = current_timestamp  WHERE id_application_user = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(ApplicationUserConstantes.STATUS) String status);

    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE id_application_user = :id AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE id_user = :idUser AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByIdUserAndStatus(Long idUser, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE tx_email = :email AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByEmailAndStatus(String email, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE cd_external_uuid = :externalAppUserUUID AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE cd_url_token_activation = :urlTokenActivation AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByUrlTokenActivationAndStatus(String urlTokenActivation, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE cd_activation = :activationCode AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByActivationCodeAndStatus(String activationCode, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE dt_due_activation = :dueDateActivation AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByDueDateActivationAndStatus(Date dueDateActivation, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = (SELECT MAX(id_application_user) AS maxid FROM tb_application_user WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<ApplicationUser> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_application_user WHERE id_application_user = :id AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE id_user = :idUser AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByIdUserAndStatus(Long idUser, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE tx_email = :email AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByEmailAndStatus(String email, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE cd_external_uuid = :externalAppUserUUID AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE cd_url_token_activation = :urlTokenActivation AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByUrlTokenActivationAndStatus(String urlTokenActivation, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE cd_activation = :activationCode AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByActivationCodeAndStatus(String activationCode, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE dt_due_activation = :dueDateActivation AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByDueDateActivationAndStatus(Date dueDateActivation, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_application_user WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<ApplicationUser> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE id_application_user = :id", nativeQuery = true)
    void deleteById(@Param(ApplicationUserConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE id_user = :idUser", nativeQuery = true)
    void deleteByIdUser(@Param(ApplicationUserConstantes.IDUSER) Long idUser);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE tx_email = :email", nativeQuery = true)
    void deleteByEmail(@Param(ApplicationUserConstantes.EMAIL) String email);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase", nativeQuery = true)
    void deleteByEncodedPassPhrase(@Param(ApplicationUserConstantes.ENCODEDPASSPHRASE) String encodedPassPhrase);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE cd_external_uuid = :externalAppUserUUID", nativeQuery = true)
    void deleteByExternalAppUserUUID(@Param(ApplicationUserConstantes.EXTERNALAPPUSERUUID) UUID externalAppUserUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE cd_url_token_activation = :urlTokenActivation", nativeQuery = true)
    void deleteByUrlTokenActivation(@Param(ApplicationUserConstantes.URLTOKENACTIVATION) String urlTokenActivation);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE cd_activation = :activationCode", nativeQuery = true)
    void deleteByActivationCode(@Param(ApplicationUserConstantes.ACTIVATIONCODE) String activationCode);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE dt_due_activation = :dueDateActivation", nativeQuery = true)
    void deleteByDueDateActivation(@Param(ApplicationUserConstantes.DUEDATEACTIVATION) Date dueDateActivation);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(ApplicationUserConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(ApplicationUserConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_application_user WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(ApplicationUserConstantes.DATEUPDATED) Date dateUpdated);

}
