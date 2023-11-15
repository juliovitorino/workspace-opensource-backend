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

import java.time.LocalDate;
import java.util.List;
import br.com.jcv.security.guardian.model.Users;
import br.com.jcv.security.guardian.constantes.UsersConstantes;
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
* UsersRepository - Interface dos métodos de acesso aos dados da tabela tb_user
* Camada de dados Users - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor Users
* @since Tue Nov 14 17:22:08 BRT 2023
*
*/
@Repository
public interface UsersRepository extends JpaRepository<Users, Long>
{
    @Query(value = "SELECT * FROM tb_user WHERE  status = :status", nativeQuery = true)
    List<Users> findAllByStatus(@Param(UsersConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_user WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_user = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:encodedPassPhrase as TEXT) IS NULL OR tx_encoded_pass_phrase = :encodedPassPhrase) " +
        "AND (cast(:idUserUUID as TEXT) IS NULL OR id_user_uuid = :idUserUUID) " +
        "AND (cast(:birthday as DATE) IS NULL OR to_char(dt_birthday, 'YYYY-MM-DD') = :birthday) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<Users> findUsersByFilter(Pageable pageable,
        @Param(UsersConstantes.ID) Long id,
        @Param(UsersConstantes.NAME) String name,
        @Param(UsersConstantes.ENCODED_PWD) String encodedPassPhrase,
        @Param(UsersConstantes.IDUSERUUID) UUID idUserUUID,
        @Param(UsersConstantes.BIRTHDAY) String birthday,
        @Param(UsersConstantes.STATUS) String status,
        @Param(UsersConstantes.DATECREATED) String dateCreated,
        @Param(UsersConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_user WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_user = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:encodedPassPhrase as TEXT) IS NULL OR tx_encoded_pass_phrase = :encodedPassPhrase) " +
        "AND (cast(:idUserUUID as TEXT) IS NULL OR id_user_uuid = :idUserUUID) " +
        "AND (cast(:birthday as DATE) IS NULL OR to_char(dt_birthday, 'YYYY-MM-DD') = :birthday) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<Users> findUsersByFilter(
        @Param(UsersConstantes.ID) Long id,
        @Param(UsersConstantes.NAME) String name,
        @Param(UsersConstantes.ENCODED_PWD) String encodedPassPhrase,
        @Param(UsersConstantes.IDUSERUUID) UUID idUserUUID,
        @Param(UsersConstantes.BIRTHDAY) String birthday,
        @Param(UsersConstantes.STATUS) String status,
        @Param(UsersConstantes.DATECREATED) String dateCreated,
        @Param(UsersConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_user) AS maxid FROM tb_user WHERE id_user = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_user) AS maxid FROM tb_user WHERE tx_name = :name AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNameAndStatus(String name, String status);
     @Query(value = "SELECT MAX(id_user) AS maxid FROM tb_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase AND status = :status ", nativeQuery = true)
     Long loadMaxIdByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
     @Query(value = "SELECT MAX(id_user) AS maxid FROM tb_user WHERE id_user_uuid = :idUserUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdUserUUIDAndStatus(UUID idUserUUID, String status);
     @Query(value = "SELECT MAX(id_user) AS maxid FROM tb_user WHERE dt_birthday = :birthday AND status = :status ", nativeQuery = true)
     Long loadMaxIdByBirthdayAndStatus(LocalDate birthday, String status);
     @Query(value = "SELECT MAX(id_user) AS maxid FROM tb_user WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_user) AS maxid FROM tb_user WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_user SET tx_name = :name, dt_updated = current_timestamp  WHERE id_user = :id", nativeQuery = true)
     void updateNameById(@Param("id") Long id, @Param(UsersConstantes.NAME) String name);
     @Modifying
     @Query(value = "UPDATE tb_user SET tx_encoded_pass_phrase = :encodedPassPhrase, dt_updated = current_timestamp  WHERE id_user = :id", nativeQuery = true)
     void updateEncodedPassPhraseById(@Param("id") Long id, @Param(UsersConstantes.ENCODED_PWD) String encodedPassPhrase);
     @Modifying
     @Query(value = "UPDATE tb_user SET id_user_uuid = :idUserUUID, dt_updated = current_timestamp  WHERE id_user = :id", nativeQuery = true)
     void updateIdUserUUIDById(@Param("id") Long id, @Param(UsersConstantes.IDUSERUUID) UUID idUserUUID);
     @Modifying
     @Query(value = "UPDATE tb_user SET dt_birthday = :birthday, dt_updated = current_timestamp  WHERE id_user = :id", nativeQuery = true)
     void updateBirthdayById(@Param("id") Long id, @Param(UsersConstantes.BIRTHDAY) LocalDate birthday);
     @Modifying
     @Query(value = "UPDATE tb_user SET status = :status, dt_updated = current_timestamp  WHERE id_user = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(UsersConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByNameAndStatus(String name, String status);
     long countByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
     long countByIdUserUUIDAndStatus(UUID idUserUUID, String status);
     long countByBirthdayAndStatus(LocalDate birthday, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_user WHERE id_user = (SELECT MAX(id_user) AS maxid FROM tb_user WHERE id_user = :id AND  status = :status) ", nativeQuery = true)
    Optional<Users> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_user WHERE id_user = (SELECT MAX(id_user) AS maxid FROM tb_user WHERE tx_name = :name AND  status = :status) ", nativeQuery = true)
    Optional<Users> findByNameAndStatus(String name, String status);
    @Query(value = "SELECT * FROM tb_user WHERE id_user = (SELECT MAX(id_user) AS maxid FROM tb_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase AND  status = :status) ", nativeQuery = true)
    Optional<Users> findByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
    @Query(value = "SELECT * FROM tb_user WHERE id_user = (SELECT MAX(id_user) AS maxid FROM tb_user WHERE id_user_uuid = :idUserUUID AND  status = :status) ", nativeQuery = true)
    Optional<Users> findByIdUserUUIDAndStatus(UUID idUserUUID, String status);
    @Query(value = "SELECT * FROM tb_user WHERE id_user = (SELECT MAX(id_user) AS maxid FROM tb_user WHERE dt_birthday = :birthday AND  status = :status) ", nativeQuery = true)
    Optional<Users> findByBirthdayAndStatus(LocalDate birthday, String status);
    @Query(value = "SELECT * FROM tb_user WHERE id_user = (SELECT MAX(id_user) AS maxid FROM tb_user WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Users> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_user WHERE id_user = (SELECT MAX(id_user) AS maxid FROM tb_user WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Users> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_user WHERE id_user = :id AND  status = :status ", nativeQuery = true)
     List<Users> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_user WHERE tx_name = :name AND  status = :status ", nativeQuery = true)
     List<Users> findAllByNameAndStatus(String name, String status);
     @Query(value = "SELECT * FROM tb_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase AND  status = :status ", nativeQuery = true)
     List<Users> findAllByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
     @Query(value = "SELECT * FROM tb_user WHERE id_user_uuid = :idUserUUID AND  status = :status ", nativeQuery = true)
     List<Users> findAllByIdUserUUIDAndStatus(UUID idUserUUID, String status);
     @Query(value = "SELECT * FROM tb_user WHERE dt_birthday = :birthday AND  status = :status ", nativeQuery = true)
     List<Users> findAllByBirthdayAndStatus(LocalDate birthday, String status);
     @Query(value = "SELECT * FROM tb_user WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Users> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_user WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Users> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE id_user = :id", nativeQuery = true)
    void deleteById(@Param(UsersConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE tx_name = :name", nativeQuery = true)
    void deleteByName(@Param(UsersConstantes.NAME) String name);
    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE tx_encoded_pass_phrase = :encodedPassPhrase", nativeQuery = true)
    void deleteByEncodedPassPhrase(@Param(UsersConstantes.ENCODED_PWD) String encodedPassPhrase);
    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE id_user_uuid = :idUserUUID", nativeQuery = true)
    void deleteByIdUserUUID(@Param(UsersConstantes.IDUSERUUID) UUID idUserUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE dt_birthday = :birthday", nativeQuery = true)
    void deleteByBirthday(@Param(UsersConstantes.BIRTHDAY) LocalDate birthday);
    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(UsersConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(UsersConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_user WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(UsersConstantes.DATEUPDATED) Date dateUpdated);

}
