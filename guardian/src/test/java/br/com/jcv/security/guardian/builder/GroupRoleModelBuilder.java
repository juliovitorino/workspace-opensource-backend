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

import br.com.jcv.security.guardian.model.GroupRole;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class GroupRoleModelBuilder {

    private GroupRole grouprole;

    private GroupRoleModelBuilder(){}
    public static GroupRoleModelBuilder newGroupRoleModelTestBuilder() {
        GroupRoleModelBuilder builder = new GroupRoleModelBuilder();
        builder.grouprole = new GroupRole();
        return builder;
    }

    public GroupRole now() {
        return this.grouprole;
    }

    public GroupRoleModelBuilder id(Long id){
        this.grouprole.setId(id);
        return this;
    }
    public GroupRoleModelBuilder idRole(Long idRole){
        this.grouprole.setIdRole(idRole);
        return this;
    }
    public GroupRoleModelBuilder idGroup(Long idGroup){
        this.grouprole.setIdGroup(idGroup);
        return this;
    }
    public GroupRoleModelBuilder status(String status){
        this.grouprole.setStatus(status);
        return this;
    }
    public GroupRoleModelBuilder dateCreated(Date dateCreated){
        this.grouprole.setDateCreated(dateCreated);
        return this;
    }
    public GroupRoleModelBuilder dateUpdated(Date dateUpdated){
        this.grouprole.setDateUpdated(dateUpdated);
        return this;
    }


}
