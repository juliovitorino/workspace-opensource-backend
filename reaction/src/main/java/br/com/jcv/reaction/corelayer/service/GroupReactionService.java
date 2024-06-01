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


package br.com.jcv.reaction.corelayer.service;

import java.util.Date;
import java.util.List;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.reaction.corelayer.model.GroupReaction;
import br.com.jcv.reaction.infrastructure.dto.GroupReactionDTO;

/**
* GroupReactionService - Interface for GroupReaction
*
* @author GroupReaction
* @since Tue May 28 16:22:55 BRT 2024
*/

public interface GroupReactionService extends CommoditieBaseService<GroupReactionDTO,GroupReaction>
{
    GroupReactionDTO findGroupReactionByIdAndStatus(Long id);
    GroupReactionDTO findGroupReactionByIdAndStatus(Long id, String status);
    GroupReactionDTO findGroupReactionByGroupIdAndStatus(Long groupId);
    GroupReactionDTO findGroupReactionByGroupIdAndStatus(Long groupId, String status);
    GroupReactionDTO findGroupReactionByReactionIdAndStatus(Long reactionId);
    GroupReactionDTO findGroupReactionByReactionIdAndStatus(Long reactionId, String status);
    GroupReactionDTO findGroupReactionByDateCreatedAndStatus(Date dateCreated);
    GroupReactionDTO findGroupReactionByDateCreatedAndStatus(Date dateCreated, String status);
    GroupReactionDTO findGroupReactionByDateUpdatedAndStatus(Date dateUpdated);
    GroupReactionDTO findGroupReactionByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<GroupReactionDTO> findAllGroupReactionByIdAndStatus(Long id, String status);
    List<GroupReactionDTO> findAllGroupReactionByGroupIdAndStatus(Long groupId, String status);
    List<GroupReactionDTO> findAllGroupReactionByReactionIdAndStatus(Long reactionId, String status);
    List<GroupReactionDTO> findAllGroupReactionByDateCreatedAndStatus(Date dateCreated, String status);
    List<GroupReactionDTO> findAllGroupReactionByDateUpdatedAndStatus(Date dateUpdated, String status);

    GroupReactionDTO updateGroupIdById(Long id, Long groupId);
    GroupReactionDTO updateReactionIdById(Long id, Long reactionId);
    GroupReactionDTO updateStatusById(Long id, String status);


}
