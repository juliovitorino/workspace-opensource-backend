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

import br.com.jcv.reaction.corelayer.model.ReactionEvent;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ReactionEventModelBuilder {

    private ReactionEvent reactionevent;

    private ReactionEventModelBuilder(){}
    public static ReactionEventModelBuilder newReactionEventModelTestBuilder() {
        ReactionEventModelBuilder builder = new ReactionEventModelBuilder();
        builder.reactionevent = new ReactionEvent();
        return builder;
    }

    public ReactionEvent now() {
        return this.reactionevent;
    }

    public ReactionEventModelBuilder id(Long id){
        this.reactionevent.setId(id);
        return this;
    }
    public ReactionEventModelBuilder reactionId(Long reactionId){
        this.reactionevent.setReactionId(reactionId);
        return this;
    }
    public ReactionEventModelBuilder externalItemUUID(Long externalItemUUID){
        this.reactionevent.setExternalItemUUID(externalItemUUID);
        return this;
    }
    public ReactionEventModelBuilder externalAppUUID(Long externalAppUUID){
        this.reactionevent.setExternalAppUUID(externalAppUUID);
        return this;
    }
    public ReactionEventModelBuilder externalUserUUID(Long externalUserUUID){
        this.reactionevent.setExternalUserUUID(externalUserUUID);
        return this;
    }
    public ReactionEventModelBuilder status(String status){
        this.reactionevent.setStatus(status);
        return this;
    }
    public ReactionEventModelBuilder dateCreated(Date dateCreated){
        this.reactionevent.setDateCreated(dateCreated);
        return this;
    }
    public ReactionEventModelBuilder dateUpdated(Date dateUpdated){
        this.reactionevent.setDateUpdated(dateUpdated);
        return this;
    }


}
