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

import br.com.jcv.reaction.corelayer.model.Reaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ReactionModelBuilder {

    private Reaction reaction;

    private ReactionModelBuilder(){}
    public static ReactionModelBuilder newReactionModelTestBuilder() {
        ReactionModelBuilder builder = new ReactionModelBuilder();
        builder.reaction = new Reaction();
        return builder;
    }

    public Reaction now() {
        return this.reaction;
    }

    public ReactionModelBuilder id(Long id){
        this.reaction.setId(id);
        return this;
    }
    public ReactionModelBuilder name(String name){
        this.reaction.setName(name);
        return this;
    }
    public ReactionModelBuilder icon(String icon){
        this.reaction.setIcon(icon);
        return this;
    }
    public ReactionModelBuilder tag(String tag){
        this.reaction.setTag(tag);
        return this;
    }
    public ReactionModelBuilder status(String status){
        this.reaction.setStatus(status);
        return this;
    }
    public ReactionModelBuilder dateCreated(Date dateCreated){
        this.reaction.setDateCreated(dateCreated);
        return this;
    }
    public ReactionModelBuilder dateUpdated(Date dateUpdated){
        this.reaction.setDateUpdated(dateUpdated);
        return this;
    }


}
