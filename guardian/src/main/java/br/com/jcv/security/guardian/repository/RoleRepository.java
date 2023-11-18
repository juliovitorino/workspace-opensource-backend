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
import br.com.jcv.security.guardian.model.Role;
import br.com.jcv.security.guardian.constantes.RoleConstantes;
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
* RoleRepository - Interface dos métodos de acesso aos dados da tabela tb_role
* Camada de dados Role - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor Role
* @since Sat Nov 18 18:21:11 BRT 2023
*
*/
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    @Query(value = "SELECT * FROM tb_role WHERE  status = :status", nativeQuery = true)
    List<Role> findAllByStatus(@Param(RoleConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_role WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_role = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
Page<Role> findRoleByFilter(Pageable pageable,
        @Param(RoleConstantes.ID) Long id,
        @Param(RoleConstantes.NAME) UUID name,
        @Param(RoleConstantes.STATUS) String status,
        @Param(RoleConstantes.DATECREATED) String dateCreated,
        @Param(RoleConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_role WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_role = :id) " +
        "AND (cast(:name as TEXT) IS NULL OR tx_name = :name) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated, 'YYYY-MM-DD') = :dateUpdated) "

        , nativeQuery = true)
List<Role> findRoleByFilter(
        @Param(RoleConstantes.ID) Long id,
        @Param(RoleConstantes.NAME) UUID name,
        @Param(RoleConstantes.STATUS) String status,
        @Param(RoleConstantes.DATECREATED) String dateCreated,
        @Param(RoleConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_role) AS maxid FROM tb_role WHERE id_role = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_role) AS maxid FROM tb_role WHERE tx_name = :name AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNameAndStatus(UUID name, String status);
     @Query(value = "SELECT MAX(id_role) AS maxid FROM tb_role WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_role) AS maxid FROM tb_role WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_role SET tx_name = :name, dt_updated = current_timestamp  WHERE id_role = :id", nativeQuery = true)
     void updateNameById(@Param("id") Long id, @Param(RoleConstantes.NAME) UUID name);
     @Modifying
     @Query(value = "UPDATE tb_role SET status = :status, dt_updated = current_timestamp  WHERE id_role = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(RoleConstantes.STATUS) String status);

    @Query(value = "SELECT * FROM tb_role WHERE id_role = (SELECT MAX(id_role) AS maxid FROM tb_role WHERE id_role = :id AND  status = :status) ", nativeQuery = true)
    Optional<Role> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_role WHERE id_role = (SELECT MAX(id_role) AS maxid FROM tb_role WHERE tx_name = :name AND  status = :status) ", nativeQuery = true)
    Optional<Role> findByNameAndStatus(UUID name, String status);
    @Query(value = "SELECT * FROM tb_role WHERE id_role = (SELECT MAX(id_role) AS maxid FROM tb_role WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Role> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_role WHERE id_role = (SELECT MAX(id_role) AS maxid FROM tb_role WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Role> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_role WHERE id_role = :id AND  status = :status ", nativeQuery = true)
     List<Role> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_role WHERE tx_name = :name AND  status = :status ", nativeQuery = true)
     List<Role> findAllByNameAndStatus(UUID name, String status);
     @Query(value = "SELECT * FROM tb_role WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Role> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_role WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Role> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_role WHERE id_role = :id", nativeQuery = true)
    void deleteById(@Param(RoleConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_role WHERE tx_name = :name", nativeQuery = true)
    void deleteByName(@Param(RoleConstantes.NAME) UUID name);
    @Modifying
    @Query(value = "DELETE FROM tb_role WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(RoleConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_role WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(RoleConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_role WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(RoleConstantes.DATEUPDATED) Date dateUpdated);

}
