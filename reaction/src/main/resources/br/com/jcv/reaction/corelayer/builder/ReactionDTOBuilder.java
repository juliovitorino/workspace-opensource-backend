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

import br.com.jcv.reaction.corelayer.dto.ReactionDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ReactionDTOBuilder {

    private ReactionDTO reactionDTO;

    private ReactionDTOBuilder(){}
    public static ReactionDTOBuilder newReactionDTOTestBuilder() {
        ReactionDTOBuilder builder = new ReactionDTOBuilder();
        builder.reactionDTO = new ReactionDTO();
        return builder;
    }

    public ReactionDTO now() {
        return this.reactionDTO;
    }

    public ReactionDTOBuilder id(Long id){
        this.reactionDTO.setId(id);
        return this;
    }
    public ReactionDTOBuilder name(String name){
        this.reactionDTO.setName(name);
        return this;
    }
    public ReactionDTOBuilder icon(String icon){
        this.reactionDTO.setIcon(icon);
        return this;
    }
    public ReactionDTOBuilder tag(String tag){
        this.reactionDTO.setTag(tag);
        return this;
    }
    public ReactionDTOBuilder status(String status){
        this.reactionDTO.setStatus(status);
        return this;
    }
    public ReactionDTOBuilder dateCreated(Date dateCreated){
        this.reactionDTO.setDateCreated(dateCreated);
        return this;
    }
    public ReactionDTOBuilder dateUpdated(Date dateUpdated){
        this.reactionDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
