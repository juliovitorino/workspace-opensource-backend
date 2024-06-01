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


package br.com.jcv.reaction.corelayer.builder;

import br.com.jcv.reaction.corelayer.dto.ReactionEventDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ReactionEventDTOBuilder {

    private ReactionEventDTO reactioneventDTO;

    private ReactionEventDTOBuilder(){}
    public static ReactionEventDTOBuilder newReactionEventDTOTestBuilder() {
        ReactionEventDTOBuilder builder = new ReactionEventDTOBuilder();
        builder.reactioneventDTO = new ReactionEventDTO();
        return builder;
    }

    public ReactionEventDTO now() {
        return this.reactioneventDTO;
    }

    public ReactionEventDTOBuilder id(Long id){
        this.reactioneventDTO.setId(id);
        return this;
    }
    public ReactionEventDTOBuilder reactionId(Long reactionId){
        this.reactioneventDTO.setReactionId(reactionId);
        return this;
    }
    public ReactionEventDTOBuilder externalItemUUID(Long externalItemUUID){
        this.reactioneventDTO.setExternalItemUUID(externalItemUUID);
        return this;
    }
    public ReactionEventDTOBuilder externalAppUUID(Long externalAppUUID){
        this.reactioneventDTO.setExternalAppUUID(externalAppUUID);
        return this;
    }
    public ReactionEventDTOBuilder externalUserUUID(Long externalUserUUID){
        this.reactioneventDTO.setExternalUserUUID(externalUserUUID);
        return this;
    }
    public ReactionEventDTOBuilder status(String status){
        this.reactioneventDTO.setStatus(status);
        return this;
    }
    public ReactionEventDTOBuilder dateCreated(Date dateCreated){
        this.reactioneventDTO.setDateCreated(dateCreated);
        return this;
    }
    public ReactionEventDTOBuilder dateUpdated(Date dateUpdated){
        this.reactioneventDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
