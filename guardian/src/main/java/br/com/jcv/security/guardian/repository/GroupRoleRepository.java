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
import br.com.jcv.security.guardian.model.GroupRole;
import br.com.jcv.security.guardian.constantes.GroupRoleConstantes;
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
* GroupRoleRepository - Interface dos métodos de acesso aos dados da tabela tb_group_role
* Camada de dados GroupRole - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor GroupRole
* @since Sat Nov 18 18:21:12 BRT 2023
*
*/
@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRole, Long>
{
    @Query(value = "SELECT * FROM tb_group_role WHERE  status = :status", nativeQuery = true)
    List<GroupRole> findAllByStatus(@Param(GroupRoleConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_group_role WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_group_role = :id) " +
        "AND (cast(:idRole as BIGINT) IS NULL OR id_role = :idRole) " +
        "AND (cast(:idGroup as BIGINT) IS NULL OR id_group = :idGroup) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<GroupRole> findGroupRoleByFilter(Pageable pageable,
        @Param(GroupRoleConstantes.ID) Long id,
        @Param(GroupRoleConstantes.IDROLE) Long idRole,
        @Param(GroupRoleConstantes.IDGROUP) Long idGroup,
        @Param(GroupRoleConstantes.STATUS) String status,
        @Param(GroupRoleConstantes.DATECREATED) String dateCreated,
        @Param(GroupRoleConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_group_role WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_group_role = :id) " +
        "AND (cast(:idRole as BIGINT) IS NULL OR id_role = :idRole) " +
        "AND (cast(:idGroup as BIGINT) IS NULL OR id_group = :idGroup) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<GroupRole> findGroupRoleByFilter(
        @Param(GroupRoleConstantes.ID) Long id,
        @Param(GroupRoleConstantes.IDROLE) Long idRole,
        @Param(GroupRoleConstantes.IDGROUP) Long idGroup,
        @Param(GroupRoleConstantes.STATUS) String status,
        @Param(GroupRoleConstantes.DATECREATED) String dateCreated,
        @Param(GroupRoleConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE id_group_role = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE id_role = :idRole AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdRoleAndStatus(Long idRole, String status);
     @Query(value = "SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE id_group = :idGroup AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdGroupAndStatus(Long idGroup, String status);
     @Query(value = "SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_group_role SET id_role = :idRole, dt_updated = current_timestamp  WHERE id_group_role = :id", nativeQuery = true)
     void updateIdRoleById(@Param("id") Long id, @Param(GroupRoleConstantes.IDROLE) Long idRole);
     @Modifying
     @Query(value = "UPDATE tb_group_role SET id_group = :idGroup, dt_updated = current_timestamp  WHERE id_group_role = :id", nativeQuery = true)
     void updateIdGroupById(@Param("id") Long id, @Param(GroupRoleConstantes.IDGROUP) Long idGroup);
     @Modifying
     @Query(value = "UPDATE tb_group_role SET status = :status, dt_updated = current_timestamp  WHERE id_group_role = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(GroupRoleConstantes.STATUS) String status);

    @Query(value = "SELECT * FROM tb_group_role WHERE id_group_role = (SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE id_group_role = :id AND  status = :status) ", nativeQuery = true)
    Optional<GroupRole> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_group_role WHERE id_group_role = (SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE id_role = :idRole AND  status = :status) ", nativeQuery = true)
    Optional<GroupRole> findByIdRoleAndStatus(Long idRole, String status);
    @Query(value = "SELECT * FROM tb_group_role WHERE id_group_role = (SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE id_group = :idGroup AND  status = :status) ", nativeQuery = true)
    Optional<GroupRole> findByIdGroupAndStatus(Long idGroup, String status);
    @Query(value = "SELECT * FROM tb_group_role WHERE id_group_role = (SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<GroupRole> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_group_role WHERE id_group_role = (SELECT MAX(id_group_role) AS maxid FROM tb_group_role WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<GroupRole> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_group_role WHERE id_group_role = :id AND  status = :status ", nativeQuery = true)
     List<GroupRole> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_group_role WHERE id_role = :idRole AND  status = :status ", nativeQuery = true)
     List<GroupRole> findAllByIdRoleAndStatus(Long idRole, String status);
     @Query(value = "SELECT * FROM tb_group_role WHERE id_group = :idGroup AND  status = :status ", nativeQuery = true)
     List<GroupRole> findAllByIdGroupAndStatus(Long idGroup, String status);
     @Query(value = "SELECT * FROM tb_group_role WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<GroupRole> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_group_role WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<GroupRole> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_group_role WHERE id_group_role = :id", nativeQuery = true)
    void deleteById(@Param(GroupRoleConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_group_role WHERE id_role = :idRole", nativeQuery = true)
    void deleteByIdRole(@Param(GroupRoleConstantes.IDROLE) Long idRole);
    @Modifying
    @Query(value = "DELETE FROM tb_group_role WHERE id_group = :idGroup", nativeQuery = true)
    void deleteByIdGroup(@Param(GroupRoleConstantes.IDGROUP) Long idGroup);
    @Modifying
    @Query(value = "DELETE FROM tb_group_role WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(GroupRoleConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_group_role WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(GroupRoleConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_group_role WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(GroupRoleConstantes.DATEUPDATED) Date dateUpdated);

}
