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

import br.com.jcv.reaction.corelayer.dto.GroupDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class GroupDTOBuilder {

    private GroupDTO groupDTO;

    private GroupDTOBuilder(){}
    public static GroupDTOBuilder newGroupDTOTestBuilder() {
        GroupDTOBuilder builder = new GroupDTOBuilder();
        builder.groupDTO = new GroupDTO();
        return builder;
    }

    public GroupDTO now() {
        return this.groupDTO;
    }

    public GroupDTOBuilder id(Long id){
        this.groupDTO.setId(id);
        return this;
    }
    public GroupDTOBuilder name(String name){
        this.groupDTO.setName(name);
        return this;
    }
    public GroupDTOBuilder status(String status){
        this.groupDTO.setStatus(status);
        return this;
    }
    public GroupDTOBuilder dateCreated(Date dateCreated){
        this.groupDTO.setDateCreated(dateCreated);
        return this;
    }
    public GroupDTOBuilder dateUpdated(Date dateUpdated){
        this.groupDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
