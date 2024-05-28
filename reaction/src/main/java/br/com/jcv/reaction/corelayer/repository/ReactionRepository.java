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

import br.com.jcv.reaction.corelayer.model.Reaction;
import br.com.jcv.reaction.infrastructure.constantes.ReactionConstantes;

/**
*
* ReactionRepository - Interface dos métodos de acesso aos dados da tabela tb_reaction
* Camada de dados Reaction - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor Reaction
* @since Tue May 28 16:08:23 BRT 2024
*
*/
@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long>
{
    @Query(value = "SELECT * FROM tb_reaction WHERE  status = :status", nativeQuery = true)
    List<Reaction> findAllByStatus(@Param(ReactionConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_reaction WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:icon as TEXT) IS NULL OR tx_icon = :icon) " +
        "AND (cast(:tag as TEXT) IS NULL OR tx_tag = :tag) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<Reaction> findReactionByFilter(Pageable pageable,
        @Param(ReactionConstantes.ID) Long id,
        @Param(ReactionConstantes.NAME) String name,
        @Param(ReactionConstantes.ICON) String icon,
        @Param(ReactionConstantes.TAG) String tag,
        @Param(ReactionConstantes.STATUS) String status,
        @Param(ReactionConstantes.DATECREATED) String dateCreated,
        @Param(ReactionConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_reaction WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:icon as TEXT) IS NULL OR tx_icon = :icon) " +
        "AND (cast(:tag as TEXT) IS NULL OR tx_tag = :tag) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(dt_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(dt_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<Reaction> findReactionByFilter(
        @Param(ReactionConstantes.ID) Long id,
        @Param(ReactionConstantes.NAME) String name,
        @Param(ReactionConstantes.ICON) String icon,
        @Param(ReactionConstantes.TAG) String tag,
        @Param(ReactionConstantes.STATUS) String status,
        @Param(ReactionConstantes.DATECREATED) String dateCreated,
        @Param(ReactionConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction WHERE id = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction WHERE tx_name = :name AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNameAndStatus(String name, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction WHERE tx_icon = :icon AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIconAndStatus(String icon, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction WHERE tx_tag = :tag AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTagAndStatus(String tag, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction WHERE dt_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id) AS maxid FROM tb_reaction WHERE dt_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_reaction SET tx_name = :name, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateNameById(@Param("id") Long id, @Param(ReactionConstantes.NAME) String name);
     @Modifying
     @Query(value = "UPDATE tb_reaction SET tx_icon = :icon, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateIconById(@Param("id") Long id, @Param(ReactionConstantes.ICON) String icon);
     @Modifying
     @Query(value = "UPDATE tb_reaction SET tx_tag = :tag, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateTagById(@Param("id") Long id, @Param(ReactionConstantes.TAG) String tag);
     @Modifying
     @Query(value = "UPDATE tb_reaction SET status = :status, dt_updated = current_timestamp  WHERE id = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(ReactionConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByNameAndStatus(String name, String status);
     long countByIconAndStatus(String icon, String status);
     long countByTagAndStatus(String tag, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction WHERE id = :id AND  status = :status) ", nativeQuery = true)
    Optional<Reaction> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction WHERE tx_name = :name AND  status = :status) ", nativeQuery = true)
    Optional<Reaction> findByNameAndStatus(String name, String status);
    @Query(value = "SELECT * FROM tb_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction WHERE tx_icon = :icon AND  status = :status) ", nativeQuery = true)
    Optional<Reaction> findByIconAndStatus(String icon, String status);
    @Query(value = "SELECT * FROM tb_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction WHERE tx_tag = :tag AND  status = :status) ", nativeQuery = true)
    Optional<Reaction> findByTagAndStatus(String tag, String status);
    @Query(value = "SELECT * FROM tb_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction WHERE dt_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Reaction> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_reaction WHERE id = (SELECT MAX(id) AS maxid FROM tb_reaction WHERE dt_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Reaction> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_reaction WHERE id = :id AND  status = :status ", nativeQuery = true)
     List<Reaction> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_reaction WHERE tx_name = :name AND  status = :status ", nativeQuery = true)
     List<Reaction> findAllByNameAndStatus(String name, String status);
     @Query(value = "SELECT * FROM tb_reaction WHERE tx_icon = :icon AND  status = :status ", nativeQuery = true)
     List<Reaction> findAllByIconAndStatus(String icon, String status);
     @Query(value = "SELECT * FROM tb_reaction WHERE tx_tag = :tag AND  status = :status ", nativeQuery = true)
     List<Reaction> findAllByTagAndStatus(String tag, String status);
     @Query(value = "SELECT * FROM tb_reaction WHERE dt_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Reaction> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_reaction WHERE dt_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Reaction> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_reaction WHERE id = :id", nativeQuery = true)
    void deleteById(@Param(ReactionConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction WHERE tx_name = :name", nativeQuery = true)
    void deleteByName(@Param(ReactionConstantes.NAME) String name);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction WHERE tx_icon = :icon", nativeQuery = true)
    void deleteByIcon(@Param(ReactionConstantes.ICON) String icon);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction WHERE tx_tag = :tag", nativeQuery = true)
    void deleteByTag(@Param(ReactionConstantes.TAG) String tag);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(ReactionConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction WHERE dt_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(ReactionConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_reaction WHERE dt_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(ReactionConstantes.DATEUPDATED) Date dateUpdated);

}
