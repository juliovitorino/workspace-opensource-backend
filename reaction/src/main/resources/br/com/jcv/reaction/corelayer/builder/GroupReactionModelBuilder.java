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

import br.com.jcv.reaction.corelayer.model.GroupReaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class GroupReactionModelBuilder {

    private GroupReaction groupreaction;

    private GroupReactionModelBuilder(){}
    public static GroupReactionModelBuilder newGroupReactionModelTestBuilder() {
        GroupReactionModelBuilder builder = new GroupReactionModelBuilder();
        builder.groupreaction = new GroupReaction();
        return builder;
    }

    public GroupReaction now() {
        return this.groupreaction;
    }

    public GroupReactionModelBuilder id(Long id){
        this.groupreaction.setId(id);
        return this;
    }
    public GroupReactionModelBuilder groupId(Long groupId){
        this.groupreaction.setGroupId(groupId);
        return this;
    }
    public GroupReactionModelBuilder reactionId(Long reactionId){
        this.groupreaction.setReactionId(reactionId);
        return this;
    }
    public GroupReactionModelBuilder status(String status){
        this.groupreaction.setStatus(status);
        return this;
    }
    public GroupReactionModelBuilder dateCreated(Date dateCreated){
        this.groupreaction.setDateCreated(dateCreated);
        return this;
    }
    public GroupReactionModelBuilder dateUpdated(Date dateUpdated){
        this.groupreaction.setDateUpdated(dateUpdated);
        return this;
    }


}
