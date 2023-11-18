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


package br.com.jcv.security.guardian.service;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;

import br.com.jcv.security.guardian.dto.UserRoleDTO;
import br.com.jcv.security.guardian.model.UserRole;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* UserRoleService - Interface for UserRole
*
* @author UserRole
* @since Sat Nov 18 18:21:13 BRT 2023
*/

public interface UserRoleService extends CommoditieBaseService<UserRoleDTO,UserRole>
{
    UserRoleDTO findUserRoleByIdAndStatus(Long id);
    UserRoleDTO findUserRoleByIdAndStatus(Long id, String status);
    UserRoleDTO findUserRoleByIdRoleAndStatus(Long idRole);
    UserRoleDTO findUserRoleByIdRoleAndStatus(Long idRole, String status);
    UserRoleDTO findUserRoleByIdUserAndStatus(Long idUser);
    UserRoleDTO findUserRoleByIdUserAndStatus(Long idUser, String status);
    UserRoleDTO findUserRoleByDateCreatedAndStatus(Date dateCreated);
    UserRoleDTO findUserRoleByDateCreatedAndStatus(Date dateCreated, String status);
    UserRoleDTO findUserRoleByDateUpdatedAndStatus(Date dateUpdated);
    UserRoleDTO findUserRoleByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<UserRoleDTO> findAllUserRoleByIdAndStatus(Long id, String status);
    List<UserRoleDTO> findAllUserRoleByIdRoleAndStatus(Long idRole, String status);
    List<UserRoleDTO> findAllUserRoleByIdUserAndStatus(Long idUser, String status);
    List<UserRoleDTO> findAllUserRoleByDateCreatedAndStatus(Date dateCreated, String status);
    List<UserRoleDTO> findAllUserRoleByDateUpdatedAndStatus(Date dateUpdated, String status);

    UserRoleDTO updateIdRoleById(Long id, Long idRole);
    UserRoleDTO updateIdUserById(Long id, Long idUser);
    UserRoleDTO updateStatusById(Long id, String status);


}
