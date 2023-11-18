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

import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.model.GroupUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* GroupUserService - Interface for GroupUser
*
* @author GroupUser
* @since Sat Nov 18 18:21:13 BRT 2023
*/

public interface GroupUserService extends CommoditieBaseService<GroupUserDTO,GroupUser>
{
    GroupUserDTO findGroupUserByIdAndStatus(Long id);
    GroupUserDTO findGroupUserByIdAndStatus(Long id, String status);
    GroupUserDTO findGroupUserByIdUserAndStatus(Long idUser);
    GroupUserDTO findGroupUserByIdUserAndStatus(Long idUser, String status);
    GroupUserDTO findGroupUserByIdGroupAndStatus(Long idGroup);
    GroupUserDTO findGroupUserByIdGroupAndStatus(Long idGroup, String status);
    GroupUserDTO findGroupUserByDateCreatedAndStatus(Date dateCreated);
    GroupUserDTO findGroupUserByDateCreatedAndStatus(Date dateCreated, String status);
    GroupUserDTO findGroupUserByDateUpdatedAndStatus(Date dateUpdated);
    GroupUserDTO findGroupUserByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<GroupUserDTO> findAllGroupUserByIdAndStatus(Long id, String status);
    List<GroupUserDTO> findAllGroupUserByIdUserAndStatus(Long idUser, String status);
    List<GroupUserDTO> findAllGroupUserByIdGroupAndStatus(Long idGroup, String status);
    List<GroupUserDTO> findAllGroupUserByDateCreatedAndStatus(Date dateCreated, String status);
    List<GroupUserDTO> findAllGroupUserByDateUpdatedAndStatus(Date dateUpdated, String status);

    GroupUserDTO updateIdUserById(Long id, Long idUser);
    GroupUserDTO updateIdGroupById(Long id, Long idGroup);
    GroupUserDTO updateStatusById(Long id, String status);


}
