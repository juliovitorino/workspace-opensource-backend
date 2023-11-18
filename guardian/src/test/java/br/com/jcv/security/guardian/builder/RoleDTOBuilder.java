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

import br.com.jcv.security.guardian.dto.RoleDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class RoleDTOBuilder {

    private RoleDTO roleDTO;

    private RoleDTOBuilder(){}
    public static RoleDTOBuilder newRoleDTOTestBuilder() {
        RoleDTOBuilder builder = new RoleDTOBuilder();
        builder.roleDTO = new RoleDTO();
        return builder;
    }

    public RoleDTO now() {
        return this.roleDTO;
    }

    public RoleDTOBuilder id(Long id){
        this.roleDTO.setId(id);
        return this;
    }
    public RoleDTOBuilder name(UUID name){
        this.roleDTO.setName(name);
        return this;
    }
    public RoleDTOBuilder status(String status){
        this.roleDTO.setStatus(status);
        return this;
    }
    public RoleDTOBuilder dateCreated(Date dateCreated){
        this.roleDTO.setDateCreated(dateCreated);
        return this;
    }
    public RoleDTOBuilder dateUpdated(Date dateUpdated){
        this.roleDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
