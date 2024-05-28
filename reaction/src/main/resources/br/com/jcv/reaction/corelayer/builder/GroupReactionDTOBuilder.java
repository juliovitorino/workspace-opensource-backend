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

import br.com.jcv.reaction.corelayer.dto.GroupReactionDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class GroupReactionDTOBuilder {

    private GroupReactionDTO groupreactionDTO;

    private GroupReactionDTOBuilder(){}
    public static GroupReactionDTOBuilder newGroupReactionDTOTestBuilder() {
        GroupReactionDTOBuilder builder = new GroupReactionDTOBuilder();
        builder.groupreactionDTO = new GroupReactionDTO();
        return builder;
    }

    public GroupReactionDTO now() {
        return this.groupreactionDTO;
    }

    public GroupReactionDTOBuilder id(Long id){
        this.groupreactionDTO.setId(id);
        return this;
    }
    public GroupReactionDTOBuilder groupId(Long groupId){
        this.groupreactionDTO.setGroupId(groupId);
        return this;
    }
    public GroupReactionDTOBuilder reactionId(Long reactionId){
        this.groupreactionDTO.setReactionId(reactionId);
        return this;
    }
    public GroupReactionDTOBuilder status(String status){
        this.groupreactionDTO.setStatus(status);
        return this;
    }
    public GroupReactionDTOBuilder dateCreated(Date dateCreated){
        this.groupreactionDTO.setDateCreated(dateCreated);
        return this;
    }
    public GroupReactionDTOBuilder dateUpdated(Date dateUpdated){
        this.groupreactionDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
