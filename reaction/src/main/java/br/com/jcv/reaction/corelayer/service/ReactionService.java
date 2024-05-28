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
import br.com.jcv.reaction.corelayer.model.Reaction;
import br.com.jcv.reaction.infrastructure.dto.ReactionDTO;

/**
* ReactionService - Interface for Reaction
*
* @author Reaction
* @since Tue May 28 16:08:23 BRT 2024
*/

public interface ReactionService extends CommoditieBaseService<ReactionDTO,Reaction>
{
    ReactionDTO findReactionByIdAndStatus(Long id);
    ReactionDTO findReactionByIdAndStatus(Long id, String status);
    ReactionDTO findReactionByNameAndStatus(String name);
    ReactionDTO findReactionByNameAndStatus(String name, String status);
    ReactionDTO findReactionByIconAndStatus(String icon);
    ReactionDTO findReactionByIconAndStatus(String icon, String status);
    ReactionDTO findReactionByTagAndStatus(String tag);
    ReactionDTO findReactionByTagAndStatus(String tag, String status);
    ReactionDTO findReactionByDateCreatedAndStatus(Date dateCreated);
    ReactionDTO findReactionByDateCreatedAndStatus(Date dateCreated, String status);
    ReactionDTO findReactionByDateUpdatedAndStatus(Date dateUpdated);
    ReactionDTO findReactionByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<ReactionDTO> findAllReactionByIdAndStatus(Long id, String status);
    List<ReactionDTO> findAllReactionByNameAndStatus(String name, String status);
    List<ReactionDTO> findAllReactionByIconAndStatus(String icon, String status);
    List<ReactionDTO> findAllReactionByTagAndStatus(String tag, String status);
    List<ReactionDTO> findAllReactionByDateCreatedAndStatus(Date dateCreated, String status);
    List<ReactionDTO> findAllReactionByDateUpdatedAndStatus(Date dateUpdated, String status);

    ReactionDTO updateNameById(Long id, String name);
    ReactionDTO updateIconById(Long id, String icon);
    ReactionDTO updateTagById(Long id, String tag);
    ReactionDTO updateStatusById(Long id, String status);


}
