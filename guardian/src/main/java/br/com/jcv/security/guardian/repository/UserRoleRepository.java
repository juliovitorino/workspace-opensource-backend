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
import br.com.jcv.security.guardian.model.UserRole;
import br.com.jcv.security.guardian.constantes.UserRoleConstantes;
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
* UserRoleRepository - Interface dos métodos de acesso aos dados da tabela tb_user_role
* Camada de dados UserRole - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor UserRole
* @since Sat Nov 18 18:21:13 BRT 2023
*
*/
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>
{
    @Query(value = "SELECT * FROM tb_user_role WHERE  status = :status", nativeQuery = true)
    List<UserRole> findAllByStatus(@Param(UserRoleConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_user_role WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_user_role = :id) " +
        "AND (cast(:idRole as BIGINT) IS NULL OR id_role = :idRole) " +
        "AND (cast(:idUser as BIGINT) IS NULL OR id_user = :idUser) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<UserRole> findUserRoleByFilter(Pageable pageable,
        @Param(UserRoleConstantes.ID) Long id,
        @Param(UserRoleConstantes.IDROLE) Long idRole,
        @Param(UserRoleConstantes.IDUSER) Long idUser,
        @Param(UserRoleConstantes.STATUS) String status,
        @Param(UserRoleConstantes.DATECREATED) String dateCreated,
        @Param(UserRoleConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_user_role WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_user_role = :id) " +
        "AND (cast(:idRole as BIGINT) IS NULL OR id_role = :idRole) " +
        "AND (cast(:idUser as BIGINT) IS NULL OR id_user = :idUser) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<UserRole> findUserRoleByFilter(
        @Param(UserRoleConstantes.ID) Long id,
        @Param(UserRoleConstantes.IDROLE) Long idRole,
        @Param(UserRoleConstantes.IDUSER) Long idUser,
        @Param(UserRoleConstantes.STATUS) String status,
        @Param(UserRoleConstantes.DATECREATED) String dateCreated,
        @Param(UserRoleConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE id_user_role = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE id_role = :idRole AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdRoleAndStatus(Long idRole, String status);
     @Query(value = "SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE id_user = :idUser AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdUserAndStatus(Long idUser, String status);
     @Query(value = "SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_user_role SET id_role = :idRole, dt_updated = current_timestamp  WHERE id_user_role = :id", nativeQuery = true)
     void updateIdRoleById(@Param("id") Long id, @Param(UserRoleConstantes.IDROLE) Long idRole);
     @Modifying
     @Query(value = "UPDATE tb_user_role SET id_user = :idUser, dt_updated = current_timestamp  WHERE id_user_role = :id", nativeQuery = true)
     void updateIdUserById(@Param("id") Long id, @Param(UserRoleConstantes.IDUSER) Long idUser);
     @Modifying
     @Query(value = "UPDATE tb_user_role SET status = :status, dt_updated = current_timestamp  WHERE id_user_role = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(UserRoleConstantes.STATUS) String status);

    @Query(value = "SELECT * FROM tb_user_role WHERE id_user_role = (SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE id_user_role = :id AND  status = :status) ", nativeQuery = true)
    Optional<UserRole> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_user_role WHERE id_user_role = (SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE id_role = :idRole AND  status = :status) ", nativeQuery = true)
    Optional<UserRole> findByIdRoleAndStatus(Long idRole, String status);
    @Query(value = "SELECT * FROM tb_user_role WHERE id_user_role = (SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE id_user = :idUser AND  status = :status) ", nativeQuery = true)
    Optional<UserRole> findByIdUserAndStatus(Long idUser, String status);
    @Query(value = "SELECT * FROM tb_user_role WHERE id_user_role = (SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<UserRole> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_user_role WHERE id_user_role = (SELECT MAX(id_user_role) AS maxid FROM tb_user_role WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<UserRole> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_user_role WHERE id_user_role = :id AND  status = :status ", nativeQuery = true)
     List<UserRole> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_user_role WHERE id_role = :idRole AND  status = :status ", nativeQuery = true)
     List<UserRole> findAllByIdRoleAndStatus(Long idRole, String status);
     @Query(value = "SELECT * FROM tb_user_role WHERE id_user = :idUser AND id_role = :idRole AND status = :status ", nativeQuery = true)
     List<UserRole> findAllByIdUserAndIdRoleAndStatus(Long idUser, Long idRole, String status);
     @Query(value = "SELECT * FROM tb_user_role WHERE id_user = :idUser AND  status = :status ", nativeQuery = true)
     List<UserRole> findAllByIdUserAndStatus(Long idUser, String status);
     @Query(value = "SELECT * FROM tb_user_role WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<UserRole> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_user_role WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<UserRole> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_user_role WHERE id_user_role = :id", nativeQuery = true)
    void deleteById(@Param(UserRoleConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_user_role WHERE id_role = :idRole", nativeQuery = true)
    void deleteByIdRole(@Param(UserRoleConstantes.IDROLE) Long idRole);
    @Modifying
    @Query(value = "DELETE FROM tb_user_role WHERE id_user = :idUser", nativeQuery = true)
    void deleteByIdUser(@Param(UserRoleConstantes.IDUSER) Long idUser);
    @Modifying
    @Query(value = "DELETE FROM tb_user_role WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(UserRoleConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_user_role WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(UserRoleConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_user_role WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(UserRoleConstantes.DATEUPDATED) Date dateUpdated);

}
