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

import br.com.jcv.security.guardian.dto.GroupRoleDTO;
import br.com.jcv.security.guardian.model.GroupRole;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* GroupRoleService - Interface for GroupRole
*
* @author GroupRole
* @since Sat Nov 18 18:21:12 BRT 2023
*/

public interface GroupRoleService extends CommoditieBaseService<GroupRoleDTO,GroupRole>
{
    GroupRoleDTO findGroupRoleByIdAndStatus(Long id);
    GroupRoleDTO findGroupRoleByIdAndStatus(Long id, String status);
    GroupRoleDTO findGroupRoleByIdRoleAndStatus(Long idRole);
    GroupRoleDTO findGroupRoleByIdRoleAndStatus(Long idRole, String status);
    GroupRoleDTO findGroupRoleByIdGroupAndStatus(Long idGroup);
    GroupRoleDTO findGroupRoleByIdGroupAndStatus(Long idGroup, String status);
    GroupRoleDTO findGroupRoleByIdGroupAndIdRoleAndStatus(Long idGroup, Long idRole, String status);
    GroupRoleDTO findGroupRoleByDateCreatedAndStatus(Date dateCreated);
    GroupRoleDTO findGroupRoleByDateCreatedAndStatus(Date dateCreated, String status);
    GroupRoleDTO findGroupRoleByDateUpdatedAndStatus(Date dateUpdated);
    GroupRoleDTO findGroupRoleByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<GroupRoleDTO> findAllGroupRoleByIdAndStatus(Long id, String status);
    List<GroupRoleDTO> findAllGroupRoleByIdRoleAndStatus(Long idRole, String status);
    List<GroupRoleDTO> findAllGroupRoleByIdGroupAndStatus(Long idGroup, String status);
    List<GroupRoleDTO> findAllGroupRoleByDateCreatedAndStatus(Date dateCreated, String status);
    List<GroupRoleDTO> findAllGroupRoleByDateUpdatedAndStatus(Date dateUpdated, String status);

    GroupRoleDTO updateIdRoleById(Long id, Long idRole);
    GroupRoleDTO updateIdGroupById(Long id, Long idGroup);
    GroupRoleDTO updateStatusById(Long id, String status);


}
