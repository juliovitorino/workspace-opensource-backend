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

import br.com.jcv.security.guardian.model.SessionState;
import br.com.jcv.security.guardian.constantes.SessionStateConstantes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
*
* SessionStateRepository - Interface dos métodos de acesso aos dados da tabela tb_session_state
* Camada de dados SessionState - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor SessionState
* @since Sun Oct 29 15:32:37 BRT 2023
*
*/
@Repository
public interface SessionStateRepository extends JpaRepository<SessionState, Long>
{
    @Query(value = "SELECT * FROM tb_session_state WHERE  status = :status", nativeQuery = true)
    List<SessionState> findAllByStatus(@Param(SessionStateConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_session_state WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_session_state = :id) " +
        "AND (cast(:idToken as TEXT) IS NULL OR id_token = :idToken) " +
        "AND (cast(:idUserUUID as TEXT) IS NULL OR id_user_uuid = :idUserUUID) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<SessionState> findSessionStateByFilter(Pageable pageable,
        @Param(SessionStateConstantes.ID) Long id,
        @Param(SessionStateConstantes.IDTOKEN) UUID idToken,
        @Param(SessionStateConstantes.IDUSERUUID) UUID idUserUUID,
        @Param(SessionStateConstantes.STATUS) String status,
        @Param(SessionStateConstantes.DATECREATED) String dateCreated,
        @Param(SessionStateConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_session_state WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_session_state = :id) " +
        "AND (cast(:idToken as TEXT) IS NULL OR id_token = :idToken) " +
        "AND (cast(:idUserUUID as TEXT) IS NULL OR id_user_uuid = :idUserUUID) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<SessionState> findSessionStateByFilter(
        @Param(SessionStateConstantes.ID) Long id,
        @Param(SessionStateConstantes.IDTOKEN) UUID idToken,
        @Param(SessionStateConstantes.IDUSERUUID) UUID idUserUUID,
        @Param(SessionStateConstantes.STATUS) String status,
        @Param(SessionStateConstantes.DATECREATED) String dateCreated,
        @Param(SessionStateConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE id_session_state = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE id_token = :idToken AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdTokenAndStatus(UUID idToken, String status);
     @Query(value = "SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE id_user_uuid = :idUserUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdUserUUIDAndStatus(UUID idUserUUID, String status);
     @Query(value = "SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_session_state SET id_token = :idToken, dt_updated = current_timestamp  WHERE id_session_state = :id", nativeQuery = true)
     void updateIdTokenById(@Param("id") Long id, @Param(SessionStateConstantes.IDTOKEN) UUID idToken);
     @Modifying
     @Query(value = "UPDATE tb_session_state SET id_user_uuid = :idUserUUID, dt_updated = current_timestamp  WHERE id_session_state = :id", nativeQuery = true)
     void updateIdUserUUIDById(@Param("id") Long id, @Param(SessionStateConstantes.IDUSERUUID) UUID idUserUUID);
     @Modifying
     @Query(value = "UPDATE tb_session_state SET status = :status, dt_updated = current_timestamp  WHERE id_session_state = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(SessionStateConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByIdTokenAndStatus(UUID idToken, String status);
     long countByIdUserUUIDAndStatus(UUID idUserUUID, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_session_state WHERE id_session_state = (SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE id_session_state = :id AND  status = :status) ", nativeQuery = true)
    Optional<SessionState> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_session_state WHERE id_session_state = (SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE id_token = :idToken AND  status = :status) ", nativeQuery = true)
    Optional<SessionState> findByIdTokenAndStatus(UUID idToken, String status);
    @Query(value = "SELECT * FROM tb_session_state WHERE id_session_state = (SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE id_user_uuid = :idUserUUID AND  status = :status) ", nativeQuery = true)
    Optional<SessionState> findByIdUserUUIDAndStatus(UUID idUserUUID, String status);
    @Query(value = "SELECT * FROM tb_session_state WHERE id_session_state = (SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<SessionState> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_session_state WHERE id_session_state = (SELECT MAX(id_session_state) AS maxid FROM tb_session_state WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<SessionState> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_session_state WHERE id_session_state = :id AND  status = :status ", nativeQuery = true)
     List<SessionState> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_session_state WHERE id_token = :idToken AND  status = :status ", nativeQuery = true)
     List<SessionState> findAllByIdTokenAndStatus(UUID idToken, String status);
     @Query(value = "SELECT * FROM tb_session_state WHERE id_user_uuid = :idUserUUID AND  status = :status ", nativeQuery = true)
     List<SessionState> findAllByIdUserUUIDAndStatus(UUID idUserUUID, String status);
     @Query(value = "SELECT * FROM tb_session_state WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<SessionState> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_session_state WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<SessionState> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);

     @Query(value = "select cast(tss.id_user_uuid as VARCHAR) \n" +
             " from tb_session_state tss \n" +
             " where tss.status = :status \n" +
             " group by tss.id_user_uuid  \n" +
             " having count(tss.id_user_uuid) > 1", nativeQuery = true)
     List<String> findAllOldestSessionByStatus(String status);

    @Modifying
    @Query(value = "DELETE FROM tb_session_state WHERE id_session_state = :id", nativeQuery = true)
    void deleteById(@Param(SessionStateConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_session_state WHERE id_token = :idToken", nativeQuery = true)
    void deleteByIdToken(@Param(SessionStateConstantes.IDTOKEN) UUID idToken);
    @Modifying
    @Query(value = "DELETE FROM tb_session_state WHERE id_user_uuid = :idUserUUID", nativeQuery = true)
    void deleteByIdUserUUID(@Param(SessionStateConstantes.IDUSERUUID) UUID idUserUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_session_state WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(SessionStateConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_session_state WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(SessionStateConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_session_state WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(SessionStateConstantes.DATEUPDATED) Date dateUpdated);
    @Modifying
    @Query(value = "DELETE FROM tb_session_state WHERE id_user_uuid = :idUserUUID AND id_session_state < :maxSessionId", nativeQuery = true)
    @Async
    @Transactional(transactionManager="transactionManager",
            propagation = Propagation.REQUIRED,
            rollbackFor = Throwable.class
    )
    void deleteByIdUserUUIDAndPreviousSessionId(UUID idUserUUID, Long maxSessionId);

}
