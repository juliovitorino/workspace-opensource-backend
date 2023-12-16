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

import br.com.jcv.security.guardian.model.ApplicationUser;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ApplicationUserModelBuilder {

    private ApplicationUser applicationuser;

    private ApplicationUserModelBuilder(){}
    public static ApplicationUserModelBuilder newApplicationUserModelTestBuilder() {
        ApplicationUserModelBuilder builder = new ApplicationUserModelBuilder();
        builder.applicationuser = new ApplicationUser();
        return builder;
    }

    public ApplicationUser now() {
        return this.applicationuser;
    }

    public ApplicationUserModelBuilder id(Long id){
        this.applicationuser.setId(id);
        return this;
    }
    public ApplicationUserModelBuilder idUser(Long id){
        this.applicationuser.setIdUser(id);
        return this;
    }
    public ApplicationUserModelBuilder idApplication(Long id){
        this.applicationuser.setIdApplication(id);
        return this;
    }
    public ApplicationUserModelBuilder email(String email){
        this.applicationuser.setEmail(email);
        return this;
    }
    public ApplicationUserModelBuilder encodedPassPhrase(String encodedPassPhrase){
        this.applicationuser.setEncodedPassPhrase(encodedPassPhrase);
        return this;
    }
    public ApplicationUserModelBuilder externalAppUserUUID(UUID externalAppUserUUID){
        this.applicationuser.setExternalAppUserUUID(externalAppUserUUID);
        return this;
    }
    public ApplicationUserModelBuilder urlTokenActivation(String urlTokenActivation){
        this.applicationuser.setUrlTokenActivation(urlTokenActivation);
        return this;
    }
    public ApplicationUserModelBuilder activationCode(String activationCode){
        this.applicationuser.setActivationCode(activationCode);
        return this;
    }
    public ApplicationUserModelBuilder dueDateActivation(Date dueDateActivation){
        this.applicationuser.setDueDateActivation(dueDateActivation);
        return this;
    }
    public ApplicationUserModelBuilder status(String status){
        this.applicationuser.setStatus(status);
        return this;
    }
    public ApplicationUserModelBuilder dateCreated(Date dateCreated){
        this.applicationuser.setDateCreated(dateCreated);
        return this;
    }
    public ApplicationUserModelBuilder dateUpdated(Date dateUpdated){
        this.applicationuser.setDateUpdated(dateUpdated);
        return this;
    }


}
