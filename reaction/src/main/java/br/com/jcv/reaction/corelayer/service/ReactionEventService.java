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
import br.com.jcv.reaction.corelayer.model.ReactionEvent;
import br.com.jcv.reaction.infrastructure.dto.ReactionEventDTO;

/**
* ReactionEventService - Interface for ReactionEvent
*
* @author ReactionEvent
* @since Tue May 28 16:48:27 BRT 2024
*/

public interface ReactionEventService extends CommoditieBaseService<ReactionEventDTO,ReactionEvent>
{
    ReactionEventDTO findReactionEventByHashMD5AndStatus(String hashMD5, String status);
    ReactionEventDTO findReactionEventByIdAndStatus(Long id);
    ReactionEventDTO findReactionEventByIdAndStatus(Long id, String status);
    ReactionEventDTO findReactionEventByReactionIdAndStatus(Long reactionId);
    ReactionEventDTO findReactionEventByReactionIdAndStatus(Long reactionId, String status);
    ReactionEventDTO findReactionEventByExternalItemUUIDAndStatus(Long externalItemUUID);
    ReactionEventDTO findReactionEventByExternalItemUUIDAndStatus(Long externalItemUUID, String status);
    ReactionEventDTO findReactionEventByExternalAppUUIDAndStatus(Long externalAppUUID);
    ReactionEventDTO findReactionEventByExternalAppUUIDAndStatus(Long externalAppUUID, String status);
    ReactionEventDTO findReactionEventByExternalUserUUIDAndStatus(Long externalUserUUID);
    ReactionEventDTO findReactionEventByExternalUserUUIDAndStatus(Long externalUserUUID, String status);
    ReactionEventDTO findReactionEventByDateCreatedAndStatus(Date dateCreated);
    ReactionEventDTO findReactionEventByDateCreatedAndStatus(Date dateCreated, String status);
    ReactionEventDTO findReactionEventByDateUpdatedAndStatus(Date dateUpdated);
    ReactionEventDTO findReactionEventByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<ReactionEventDTO> findAllReactionEventByIdAndStatus(Long id, String status);
    List<ReactionEventDTO> findAllReactionEventByReactionIdAndStatus(Long reactionId, String status);
    List<ReactionEventDTO> findAllReactionEventByExternalItemUUIDAndStatus(Long externalItemUUID, String status);
    List<ReactionEventDTO> findAllReactionEventByExternalAppUUIDAndStatus(Long externalAppUUID, String status);
    List<ReactionEventDTO> findAllReactionEventByExternalUserUUIDAndStatus(Long externalUserUUID, String status);
    List<ReactionEventDTO> findAllReactionEventByDateCreatedAndStatus(Date dateCreated, String status);
    List<ReactionEventDTO> findAllReactionEventByDateUpdatedAndStatus(Date dateUpdated, String status);

    ReactionEventDTO updateReactionIdById(Long id, Long reactionId);
    ReactionEventDTO updateExternalItemUUIDById(Long id, Long externalItemUUID);
    ReactionEventDTO updateExternalAppUUIDById(Long id, Long externalAppUUID);
    ReactionEventDTO updateExternalUserUUIDById(Long id, Long externalUserUUID);
    ReactionEventDTO updateStatusById(Long id, String status);


}
