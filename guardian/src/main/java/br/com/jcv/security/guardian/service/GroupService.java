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

import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.model.Group;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* GroupService - Interface for Group
*
* @author Group
* @since Sat Nov 18 18:21:11 BRT 2023
*/

public interface GroupService extends CommoditieBaseService<GroupDTO,Group>
{
    GroupDTO findGroupByIdAndStatus(Long id);
    GroupDTO findGroupByIdAndStatus(Long id, String status);
    GroupDTO findGroupByNameAndStatus(UUID name);
    GroupDTO findGroupByNameAndStatus(UUID name, String status);
    GroupDTO findGroupByDateCreatedAndStatus(Date dateCreated);
    GroupDTO findGroupByDateCreatedAndStatus(Date dateCreated, String status);
    GroupDTO findGroupByDateUpdatedAndStatus(Date dateUpdated);
    GroupDTO findGroupByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<GroupDTO> findAllGroupByIdAndStatus(Long id, String status);
    List<GroupDTO> findAllGroupByNameAndStatus(UUID name, String status);
    List<GroupDTO> findAllGroupByDateCreatedAndStatus(Date dateCreated, String status);
    List<GroupDTO> findAllGroupByDateUpdatedAndStatus(Date dateUpdated, String status);

    GroupDTO updateNameById(Long id, UUID name);
    GroupDTO updateStatusById(Long id, String status);


}
