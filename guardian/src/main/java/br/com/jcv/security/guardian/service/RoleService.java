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

import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.model.Role;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* RoleService - Interface for Role
*
* @author Role
* @since Sat Nov 18 18:21:11 BRT 2023
*/

public interface RoleService extends CommoditieBaseService<RoleDTO,Role>
{
    RoleDTO findRoleByIdAndStatus(Long id);
    RoleDTO findRoleByIdAndStatus(Long id, String status);
    RoleDTO findRoleByNameAndStatus(String name);
    RoleDTO findRoleByNameAndStatus(String name, String status);
    RoleDTO findRoleByDateCreatedAndStatus(Date dateCreated);
    RoleDTO findRoleByDateCreatedAndStatus(Date dateCreated, String status);
    RoleDTO findRoleByDateUpdatedAndStatus(Date dateUpdated);
    RoleDTO findRoleByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<RoleDTO> findAllRoleByIdAndStatus(Long id, String status);
    List<RoleDTO> findAllRoleByNameAndStatus(String name, String status);
    List<RoleDTO> findAllRoleByDateCreatedAndStatus(Date dateCreated, String status);
    List<RoleDTO> findAllRoleByDateUpdatedAndStatus(Date dateUpdated, String status);

    RoleDTO updateNameById(Long id, String name);
    RoleDTO updateStatusById(Long id, String status);


}
