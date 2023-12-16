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
import br.com.jcv.security.guardian.model.Group;
import br.com.jcv.security.guardian.constantes.GroupConstantes;
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
* GroupRepository - Interface dos métodos de acesso aos dados da tabela tb_group
* Camada de dados Group - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor Group
* @since Sat Nov 18 18:21:11 BRT 2023
*
*/
@Repository
public interface GroupRepository extends JpaRepository<Group, Long>
{
    @Query(value = "SELECT * FROM tb_group WHERE  status = :status", nativeQuery = true)
    List<Group> findAllByStatus(@Param(GroupConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_group WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_group = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<Group> findGroupByFilter(Pageable pageable,
        @Param(GroupConstantes.ID) Long id,
        @Param(GroupConstantes.NAME) String name,
        @Param(GroupConstantes.STATUS) String status,
        @Param(GroupConstantes.DATECREATED) String dateCreated,
        @Param(GroupConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_group WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_group = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<Group> findGroupByFilter(
        @Param(GroupConstantes.ID) Long id,
        @Param(GroupConstantes.NAME) String name,
        @Param(GroupConstantes.STATUS) String status,
        @Param(GroupConstantes.DATECREATED) String dateCreated,
        @Param(GroupConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_group) AS maxid FROM tb_group WHERE id_group = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_group) AS maxid FROM tb_group WHERE tx_name = :name AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNameAndStatus(String name, String status);
     @Query(value = "SELECT MAX(id_group) AS maxid FROM tb_group WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_group) AS maxid FROM tb_group WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_group SET tx_name = :name, dt_updated = current_timestamp  WHERE id_group = :id", nativeQuery = true)
     void updateNameById(@Param("id") Long id, @Param(GroupConstantes.NAME) String name);
     @Modifying
     @Query(value = "UPDATE tb_group SET status = :status, dt_updated = current_timestamp  WHERE id_group = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(GroupConstantes.STATUS) String status);

    @Query(value = "SELECT * FROM tb_group WHERE id_group = (SELECT MAX(id_group) AS maxid FROM tb_group WHERE id_group = :id AND  status = :status) ", nativeQuery = true)
    Optional<Group> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_group WHERE id_group = (SELECT MAX(id_group) AS maxid FROM tb_group WHERE tx_name = :name AND  status = :status) ", nativeQuery = true)
    Optional<Group> findByNameAndStatus(String name, String status);
    @Query(value = "SELECT * FROM tb_group WHERE id_group = (SELECT MAX(id_group) AS maxid FROM tb_group WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Group> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_group WHERE id_group = (SELECT MAX(id_group) AS maxid FROM tb_group WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Group> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_group WHERE id_group = :id AND  status = :status ", nativeQuery = true)
     List<Group> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_group WHERE tx_name = :name AND  status = :status ", nativeQuery = true)
     List<Group> findAllByNameAndStatus(String name, String status);
     @Query(value = "SELECT * FROM tb_group WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Group> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_group WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Group> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_group WHERE id_group = :id", nativeQuery = true)
    void deleteById(@Param(GroupConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_group WHERE tx_name = :name", nativeQuery = true)
    void deleteByName(@Param(GroupConstantes.NAME) String name);
    @Modifying
    @Query(value = "DELETE FROM tb_group WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(GroupConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_group WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(GroupConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_group WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(GroupConstantes.DATEUPDATED) Date dateUpdated);

}
