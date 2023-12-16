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


package br.com.jcv.security.guardian.builder;

import br.com.jcv.security.guardian.dto.GroupUserDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class GroupUserDTOBuilder {

    private GroupUserDTO groupuserDTO;

    private GroupUserDTOBuilder(){}
    public static GroupUserDTOBuilder newGroupUserDTOTestBuilder() {
        GroupUserDTOBuilder builder = new GroupUserDTOBuilder();
        builder.groupuserDTO = new GroupUserDTO();
        return builder;
    }

    public GroupUserDTO now() {
        return this.groupuserDTO;
    }

    public GroupUserDTOBuilder id(Long id){
        this.groupuserDTO.setId(id);
        return this;
    }
    public GroupUserDTOBuilder idUser(Long idUser){
        this.groupuserDTO.setIdUser(idUser);
        return this;
    }
    public GroupUserDTOBuilder idGroup(Long idGroup){
        this.groupuserDTO.setIdGroup(idGroup);
        return this;
    }
    public GroupUserDTOBuilder status(String status){
        this.groupuserDTO.setStatus(status);
        return this;
    }
    public GroupUserDTOBuilder dateCreated(Date dateCreated){
        this.groupuserDTO.setDateCreated(dateCreated);
        return this;
    }
    public GroupUserDTOBuilder dateUpdated(Date dateUpdated){
        this.groupuserDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
