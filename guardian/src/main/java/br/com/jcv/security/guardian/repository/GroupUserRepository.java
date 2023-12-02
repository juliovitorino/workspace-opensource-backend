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
import br.com.jcv.security.guardian.model.GroupUser;
import br.com.jcv.security.guardian.constantes.GroupUserConstantes;
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
* GroupUserRepository - Interface dos métodos de acesso aos dados da tabela tb_group_user
* Camada de dados GroupUser - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor GroupUser
* @since Sat Nov 18 18:21:13 BRT 2023
*
*/
@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, Long>
{
    @Query(value = "SELECT * FROM tb_group_user WHERE  status = :status", nativeQuery = true)
    List<GroupUser> findAllByStatus(@Param(GroupUserConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_group_user WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_group_user = :id) " +
        "AND (cast(:idUser as BIGINT) IS NULL OR id_user = :idUser) " +
        "AND (cast(:idGroup as BIGINT) IS NULL OR id_group = :idGroup) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<GroupUser> findGroupUserByFilter(Pageable pageable,
        @Param(GroupUserConstantes.ID) Long id,
        @Param(GroupUserConstantes.IDUSER) Long idUser,
        @Param(GroupUserConstantes.IDGROUP) Long idGroup,
        @Param(GroupUserConstantes.STATUS) String status,
        @Param(GroupUserConstantes.DATECREATED) String dateCreated,
        @Param(GroupUserConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_group_user WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_group_user = :id) " +
        "AND (cast(:idUser as BIGINT) IS NULL OR id_user = :idUser) " +
        "AND (cast(:idGroup as BIGINT) IS NULL OR id_group = :idGroup) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<GroupUser> findGroupUserByFilter(
        @Param(GroupUserConstantes.ID) Long id,
        @Param(GroupUserConstantes.IDUSER) Long idUser,
        @Param(GroupUserConstantes.IDGROUP) Long idGroup,
        @Param(GroupUserConstantes.STATUS) String status,
        @Param(GroupUserConstantes.DATECREATED) String dateCreated,
        @Param(GroupUserConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE id_group_user = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE id_user = :idUser AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdUserAndStatus(Long idUser, String status);
     @Query(value = "SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE id_group = :idGroup AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdGroupAndStatus(Long idGroup, String status);
     @Query(value = "SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE id_group = :idGroup AND id_user = :idUser AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdGroupAndIdUserAndStatus(Long idGroup, Long idUser, String status);
     @Query(value = "SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_group_user SET id_user = :idUser, dt_updated = current_timestamp  WHERE id_group_user = :id", nativeQuery = true)
     void updateIdUserById(@Param("id") Long id, @Param(GroupUserConstantes.IDUSER) Long idUser);
     @Modifying
     @Query(value = "UPDATE tb_group_user SET id_group = :idGroup, dt_updated = current_timestamp  WHERE id_group_user = :id", nativeQuery = true)
     void updateIdGroupById(@Param("id") Long id, @Param(GroupUserConstantes.IDGROUP) Long idGroup);
     @Modifying
     @Query(value = "UPDATE tb_group_user SET status = :status, dt_updated = current_timestamp  WHERE id_group_user = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(GroupUserConstantes.STATUS) String status);

    @Query(value = "SELECT * FROM tb_group_user WHERE id_group_user = (SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE id_group_user = :id AND  status = :status) ", nativeQuery = true)
    Optional<GroupUser> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_group_user WHERE id_group_user = (SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE id_user = :idUser AND  status = :status) ", nativeQuery = true)
    Optional<GroupUser> findByIdUserAndStatus(Long idUser, String status);
    @Query(value = "SELECT * FROM tb_group_user WHERE id_group_user = (SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE id_group = :idGroup AND  status = :status) ", nativeQuery = true)
    Optional<GroupUser> findByIdGroupAndStatus(Long idGroup, String status);
    @Query(value = "SELECT * FROM tb_group_user WHERE id_group_user = (SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<GroupUser> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_group_user WHERE id_group_user = (SELECT MAX(id_group_user) AS maxid FROM tb_group_user WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<GroupUser> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_group_user WHERE id_group_user = :id AND  status = :status ", nativeQuery = true)
     List<GroupUser> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_group_user WHERE id_user = :idUser AND  status = :status ", nativeQuery = true)
     List<GroupUser> findAllByIdUserAndStatus(Long idUser, String status);
     @Query(value = "SELECT * FROM tb_group_user WHERE id_group = :idGroup AND id_user = :idUser AND status = :status ", nativeQuery = true)
     List<GroupUser> findAllByIdGroupAndIdUserAndStatus(Long idGroup, Long idUser, String status);
     @Query(value = "SELECT * FROM tb_group_user WHERE id_group = :idGroup AND  status = :status ", nativeQuery = true)
     List<GroupUser> findAllByIdGroupAndStatus(Long idGroup, String status);
     @Query(value = "SELECT * FROM tb_group_user WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<GroupUser> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_group_user WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<GroupUser> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_group_user WHERE id_group_user = :id", nativeQuery = true)
    void deleteById(@Param(GroupUserConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_group_user WHERE id_user = :idUser", nativeQuery = true)
    void deleteByIdUser(@Param(GroupUserConstantes.IDUSER) Long idUser);
    @Modifying
    @Query(value = "DELETE FROM tb_group_user WHERE id_group = :idGroup", nativeQuery = true)
    void deleteByIdGroup(@Param(GroupUserConstantes.IDGROUP) Long idGroup);
    @Modifying
    @Query(value = "DELETE FROM tb_group_user WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(GroupUserConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_group_user WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(GroupUserConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_group_user WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(GroupUserConstantes.DATEUPDATED) Date dateUpdated);

}
