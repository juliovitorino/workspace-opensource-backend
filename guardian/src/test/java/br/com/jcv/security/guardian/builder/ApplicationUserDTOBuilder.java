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

import br.com.jcv.security.guardian.dto.ApplicationUserDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ApplicationUserDTOBuilder {

    private ApplicationUserDTO applicationuserDTO;

    private ApplicationUserDTOBuilder(){}
    public static ApplicationUserDTOBuilder newApplicationUserDTOTestBuilder() {
        ApplicationUserDTOBuilder builder = new ApplicationUserDTOBuilder();
        builder.applicationuserDTO = new ApplicationUserDTO();
        return builder;
    }

    public ApplicationUserDTO now() {
        return this.applicationuserDTO;
    }

    public ApplicationUserDTOBuilder id(Long id){
        this.applicationuserDTO.setId(id);
        return this;
    }
    public ApplicationUserDTOBuilder idUser(Long id){
        this.applicationuserDTO.setIdUser(id);
        return this;
    }
    public ApplicationUserDTOBuilder idApplication(Long id){
        this.applicationuserDTO.setIdApplication(id);
        return this;
    }
    public ApplicationUserDTOBuilder email(String email){
        this.applicationuserDTO.setEmail(email);
        return this;
    }
    public ApplicationUserDTOBuilder encodedPassPhrase(String encodedPassPhrase){
        this.applicationuserDTO.setEncodedPassPhrase(encodedPassPhrase);
        return this;
    }
    public ApplicationUserDTOBuilder externalAppUserUUID(UUID externalAppUserUUID){
        this.applicationuserDTO.setExternalAppUserUUID(externalAppUserUUID);
        return this;
    }
    public ApplicationUserDTOBuilder urlTokenActivation(String urlTokenActivation){
        this.applicationuserDTO.setUrlTokenActivation(urlTokenActivation);
        return this;
    }
    public ApplicationUserDTOBuilder activationCode(String activationCode){
        this.applicationuserDTO.setActivationCode(activationCode);
        return this;
    }
    public ApplicationUserDTOBuilder dueDateActivation(Date dueDateActivation){
        this.applicationuserDTO.setDueDateActivation(dueDateActivation);
        return this;
    }
    public ApplicationUserDTOBuilder status(String status){
        this.applicationuserDTO.setStatus(status);
        return this;
    }
    public ApplicationUserDTOBuilder dateCreated(Date dateCreated){
        this.applicationuserDTO.setDateCreated(dateCreated);
        return this;
    }
    public ApplicationUserDTOBuilder dateUpdated(Date dateUpdated){
        this.applicationuserDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
