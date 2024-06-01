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

import java.util.List;
import br.com.jcv.reaction.corelayer.model.GroupReaction;
import br.com.jcv.reaction.infrastructure.constantes.GroupReactionConstantes;

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
* GroupReactionRepository - Interface dos métodos de acesso aos dados da tabela tb_group_reaction
* Camada de dados GroupReaction - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor GroupReaction
* @since Tue May 28 16:22:55 BRT 2024
*
*/
@Repository
public interface GroupReactionRepository extends JpaRepository<GroupReaction, Long>
{
    @Query(value = "SELECT * FROM tb_group_reaction WHERE  status = :status", nativeQuery = true)
    List<GroupReaction> findAllByStatus(@Param(GroupReactionConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_group_reaction WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:groupId as BIGINT) IS NULL OR id_group = :groupId) " +
        "AND (cast(:reactionId as BIGINT) IS NULL OR id_reaction = :reactionId) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<GroupReaction> findGroupReactionByFilter(Pageable pageable,
        @Param(GroupReactionConstantes.ID) Long id,
        @Param(GroupReactionConstantes.GROUPID) Long groupId,
        @Param(GroupReactionConstantes.REACTIONID) Long reactionId,
        @Param(GroupReactionConstantes.STATUS) String status,
        @Param(GroupReactionConstantes.DATECREATED) String dateCreated,
        @Param(GroupReactionConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_group_reaction WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:groupId as BIGINT) IS NULL OR id_group = :groupId) " +
        "AND (cast(:reactionId as BIGINT) IS NULL OR id_reaction = :reactionId) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<GroupReaction> findGroupReactionByFilter(
        @Param(GroupReactionConstantes.ID) Long id,
        @Param(GroupReactionConstantes.GROUPID) Long groupId,
        @Param(GroupReactionConstantes.REACTIONID) Long reactionId,
        @Param(GroupReactionConstantes.STATUS) String status,
        @Param(GroupReactionConstantes.DATECREATED) String dateCreated,
        @Param(GroupReactionConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE id_group = :groupId AND status = :status ", nativeQuery = true)
     Long loadMaxIdByGroupIdAndStatus(Long groupId, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE id_reaction = :reactionId AND status = :status ", nativeQuery = true)
     Long loadMaxIdByReactionIdAndStatus(Long reactionId, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE dt_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE dt_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_group_reaction SET id_group = :groupId, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateGroupIdById(@Param("id") Long id, @Param(GroupReactionConstantes.GROUPID) Long groupId);
     @Modifying
     @Query(value = "UPDATE tb_group_reaction SET id_reaction = :reactionId, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateReactionIdById(@Param("id") Long id, @Param(GroupReactionConstantes.REACTIONID) Long reactionId);
     @Modifying
     @Query(value = "UPDATE tb_group_reaction SET status = :status, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(GroupReactionConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByGroupIdAndStatus(Long groupId, String status);
     long countByReactionIdAndStatus(Long reactionId, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_group_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<GroupReaction> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_group_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE id_group = :groupId AND  status = :status) ", nativeQuery = true)
    Optional<GroupReaction> findByGroupIdAndStatus(Long groupId, String status);
    @Query(value = "SELECT * FROM tb_group_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE id_reaction = :reactionId AND  status = :status) ", nativeQuery = true)
    Optional<GroupReaction> findByReactionIdAndStatus(Long reactionId, String status);
    @Query(value = "SELECT * FROM tb_group_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE dt_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<GroupReaction> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_group_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_group_reaction WHERE dt_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<GroupReaction> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_group_reaction WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<GroupReaction> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_group_reaction WHERE id_group = :groupId AND  status = :status ", nativeQuery = true)
     List<GroupReaction> findAllByGroupIdAndStatus(Long groupId, String status);
     @Query(value = "SELECT * FROM tb_group_reaction WHERE id_reaction = :reactionId AND  status = :status ", nativeQuery = true)
     List<GroupReaction> findAllByReactionIdAndStatus(Long reactionId, String status);
     @Query(value = "SELECT * FROM tb_group_reaction WHERE dt_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<GroupReaction> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_group_reaction WHERE dt_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<GroupReaction> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_group_reaction WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(GroupReactionConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_group_reaction WHERE id_group = :groupId", nativeQuery = true)
    void deleteByGroupId(@Param(GroupReactionConstantes.GROUPID) Long groupId);
    @Modifying
    @Query(value = "DELETE FROM tb_group_reaction WHERE id_reaction = :reactionId", nativeQuery = true)
    void deleteByReactionId(@Param(GroupReactionConstantes.REACTIONID) Long reactionId);
    @Modifying
    @Query(value = "DELETE FROM tb_group_reaction WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(GroupReactionConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_group_reaction WHERE dt_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(GroupReactionConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_group_reaction WHERE dt_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(GroupReactionConstantes.DATEUPDATED) Date dateUpdated);

}
