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

import br.com.jcv.security.guardian.model.UserRole;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class UserRoleModelBuilder {

    private UserRole userrole;

    private UserRoleModelBuilder(){}
    public static UserRoleModelBuilder newUserRoleModelTestBuilder() {
        UserRoleModelBuilder builder = new UserRoleModelBuilder();
        builder.userrole = new UserRole();
        return builder;
    }

    public UserRole now() {
        return this.userrole;
    }

    public UserRoleModelBuilder id(Long id){
        this.userrole.setId(id);
        return this;
    }
    public UserRoleModelBuilder idRole(Long idRole){
        this.userrole.setIdRole(idRole);
        return this;
    }
    public UserRoleModelBuilder idUser(Long idUser){
        this.userrole.setIdUser(idUser);
        return this;
    }
    public UserRoleModelBuilder status(String status){
        this.userrole.setStatus(status);
        return this;
    }
    public UserRoleModelBuilder dateCreated(Date dateCreated){
        this.userrole.setDateCreated(dateCreated);
        return this;
    }
    public UserRoleModelBuilder dateUpdated(Date dateUpdated){
        this.userrole.setDateUpdated(dateUpdated);
        return this;
    }


}
