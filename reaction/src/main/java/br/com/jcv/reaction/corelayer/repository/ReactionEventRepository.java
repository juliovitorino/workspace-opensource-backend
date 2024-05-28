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


package br.com.jcv.reaction.corelayer.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jcv.reaction.corelayer.model.ReactionEvent;
import br.com.jcv.reaction.infrastructure.constantes.ReactionEventConstantes;

/**
*
* ReactionEventRepository - Interface dos métodos de acesso aos dados da tabela tb_reaction_event
* Camada de dados ReactionEvent - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor ReactionEvent
* @since Tue May 28 16:48:27 BRT 2024
*
*/
@Repository
public interface ReactionEventRepository extends JpaRepository<ReactionEvent, Long>
{
    @Query(value = "SELECT * FROM tb_reaction_event WHERE  status = :status", nativeQuery = true)
    List<ReactionEvent> findAllByStatus(@Param(ReactionEventConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_reaction_event WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:reactionId as BIGINT) IS NULL OR id_reaction = :reactionId) " +
        "AND (cast(:externalItemUUID as BIGINT) IS NULL OR uuid_external_item = :externalItemUUID) " +
        "AND (cast(:externalAppUUID as BIGINT) IS NULL OR uuid_external_app = :externalAppUUID) " +
        "AND (cast(:externalUserUUID as BIGINT) IS NULL OR uuid_external_user = :externalUserUUID) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<ReactionEvent> findReactionEventByFilter(Pageable pageable,
        @Param(ReactionEventConstantes.ID) Long id,
        @Param(ReactionEventConstantes.REACTIONID) Long reactionId,
        @Param(ReactionEventConstantes.EXTERNALITEMUUID) Long externalItemUUID,
        @Param(ReactionEventConstantes.EXTERNALAPPUUID) Long externalAppUUID,
        @Param(ReactionEventConstantes.EXTERNALUSERUUID) Long externalUserUUID,
        @Param(ReactionEventConstantes.STATUS) String status,
        @Param(ReactionEventConstantes.DATECREATED) String dateCreated,
        @Param(ReactionEventConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_reaction_event WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:reactionId as BIGINT) IS NULL OR id_reaction = :reactionId) " +
        "AND (cast(:externalItemUUID as BIGINT) IS NULL OR uuid_external_item = :externalItemUUID) " +
        "AND (cast(:externalAppUUID as BIGINT) IS NULL OR uuid_external_app = :externalAppUUID) " +
        "AND (cast(:externalUserUUID as BIGINT) IS NULL OR uuid_external_user = :externalUserUUID) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<ReactionEvent> findReactionEventByFilter(
        @Param(ReactionEventConstantes.ID) Long id,
        @Param(ReactionEventConstantes.REACTIONID) Long reactionId,
        @Param(ReactionEventConstantes.EXTERNALITEMUUID) Long externalItemUUID,
        @Param(ReactionEventConstantes.EXTERNALAPPUUID) Long externalAppUUID,
        @Param(ReactionEventConstantes.EXTERNALUSERUUID) Long externalUserUUID,
        @Param(ReactionEventConstantes.STATUS) String status,
        @Param(ReactionEventConstantes.DATECREATED) String dateCreated,
        @Param(ReactionEventConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE id_reaction = :reactionId AND status = :status ", nativeQuery = true)
     Long loadMaxIdByReactionIdAndStatus(Long reactionId, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE uuid_external_item = :externalItemUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByExternalItemUUIDAndStatus(Long externalItemUUID, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE uuid_external_app = :externalAppUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByExternalAppUUIDAndStatus(Long externalAppUUID, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE uuid_external_user = :externalUserUUID AND status = :status ", nativeQuery = true)
     Long loadMaxIdByExternalUserUUIDAndStatus(Long externalUserUUID, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE dt_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE dt_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_reaction_event SET id_reaction = :reactionId, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateReactionIdById(@Param("id") Long id, @Param(ReactionEventConstantes.REACTIONID) Long reactionId);
     @Modifying
     @Query(value = "UPDATE tb_reaction_event SET uuid_external_item = :externalItemUUID, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateExternalItemUUIDById(@Param("id") Long id, @Param(ReactionEventConstantes.EXTERNALITEMUUID) Long externalItemUUID);
     @Modifying
     @Query(value = "UPDATE tb_reaction_event SET uuid_external_app = :externalAppUUID, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateExternalAppUUIDById(@Param("id") Long id, @Param(ReactionEventConstantes.EXTERNALAPPUUID) Long externalAppUUID);
     @Modifying
     @Query(value = "UPDATE tb_reaction_event SET uuid_external_user = :externalUserUUID, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateExternalUserUUIDById(@Param("id") Long id, @Param(ReactionEventConstantes.EXTERNALUSERUUID) Long externalUserUUID);
     @Modifying
     @Query(value = "UPDATE tb_reaction_event SET status = :status, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(ReactionEventConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByReactionIdAndStatus(Long reactionId, String status);
     long countByExternalItemUUIDAndStatus(Long externalItemUUID, String status);
     long countByExternalAppUUIDAndStatus(Long externalAppUUID, String status);
     long countByExternalUserUUIDAndStatus(Long externalUserUUID, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_reaction_event WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<ReactionEvent> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_reaction_event WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE id_reaction = :reactionId AND  status = :status) ", nativeQuery = true)
    Optional<ReactionEvent> findByReactionIdAndStatus(Long reactionId, String status);
    @Query(value = "SELECT * FROM tb_reaction_event WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE uuid_external_item = :externalItemUUID AND  status = :status) ", nativeQuery = true)
    Optional<ReactionEvent> findByExternalItemUUIDAndStatus(Long externalItemUUID, String status);
    @Query(value = "SELECT * FROM tb_reaction_event WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE uuid_external_app = :externalAppUUID AND  status = :status) ", nativeQuery = true)
    Optional<ReactionEvent> findByExternalAppUUIDAndStatus(Long externalAppUUID, String status);
    @Query(value = "SELECT * FROM tb_reaction_event WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE uuid_external_user = :externalUserUUID AND  status = :status) ", nativeQuery = true)
    Optional<ReactionEvent> findByExternalUserUUIDAndStatus(Long externalUserUUID, String status);
    @Query(value = "SELECT * FROM tb_reaction_event WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE dt_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<ReactionEvent> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_reaction_event WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction_event WHERE dt_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<ReactionEvent> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_reaction_event WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<ReactionEvent> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_reaction_event WHERE id_reaction = :reactionId AND  status = :status ", nativeQuery = true)
     List<ReactionEvent> findAllByReactionIdAndStatus(Long reactionId, String status);
     @Query(value = "SELECT * FROM tb_reaction_event WHERE uuid_external_item = :externalItemUUID AND  status = :status ", nativeQuery = true)
     List<ReactionEvent> findAllByExternalItemUUIDAndStatus(Long externalItemUUID, String status);
     @Query(value = "SELECT * FROM tb_reaction_event WHERE uuid_external_app = :externalAppUUID AND  status = :status ", nativeQuery = true)
     List<ReactionEvent> findAllByExternalAppUUIDAndStatus(Long externalAppUUID, String status);
     @Query(value = "SELECT * FROM tb_reaction_event WHERE uuid_external_user = :externalUserUUID AND  status = :status ", nativeQuery = true)
     List<ReactionEvent> findAllByExternalUserUUIDAndStatus(Long externalUserUUID, String status);
     @Query(value = "SELECT * FROM tb_reaction_event WHERE dt_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<ReactionEvent> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_reaction_event WHERE dt_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<ReactionEvent> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(ReactionEventConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE id_reaction = :reactionId", nativeQuery = true)
    void deleteByReactionId(@Param(ReactionEventConstantes.REACTIONID) Long reactionId);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE uuid_external_item = :externalItemUUID", nativeQuery = true)
    void deleteByExternalItemUUID(@Param(ReactionEventConstantes.EXTERNALITEMUUID) Long externalItemUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE uuid_external_app = :externalAppUUID", nativeQuery = true)
    void deleteByExternalAppUUID(@Param(ReactionEventConstantes.EXTERNALAPPUUID) Long externalAppUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE uuid_external_user = :externalUserUUID", nativeQuery = true)
    void deleteByExternalUserUUID(@Param(ReactionEventConstantes.EXTERNALUSERUUID) Long externalUserUUID);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(ReactionEventConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE dt_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(ReactionEventConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction_event WHERE dt_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(ReactionEventConstantes.DATEUPDATED) Date dateUpdated);

}
