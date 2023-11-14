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

import br.com.jcv.security.guardian.dto.UsersDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class UsersDTOBuilder {

    private UsersDTO usersDTO;

    private UsersDTOBuilder(){}
    public static UsersDTOBuilder newUsersDTOTestBuilder() {
        UsersDTOBuilder builder = new UsersDTOBuilder();
        builder.usersDTO = new UsersDTO();
        return builder;
    }

    public UsersDTO now() {
        return this.usersDTO;
    }

    public UsersDTOBuilder id(Long id){
        this.usersDTO.setId(id);
        return this;
    }
    public UsersDTOBuilder name(String name){
        this.usersDTO.setName(name);
        return this;
    }
    public UsersDTOBuilder encodedPassPhrase(String encodedPassPhrase){
        this.usersDTO.setEncodedPassPhrase(encodedPassPhrase);
        return this;
    }
    public UsersDTOBuilder idUserUUID(UUID idUserUUID){
        this.usersDTO.setIdUserUUID(idUserUUID);
        return this;
    }
    public UsersDTOBuilder birthday(LocalDate birthday){
        this.usersDTO.setBirthday(birthday);
        return this;
    }
    public UsersDTOBuilder status(String status){
        this.usersDTO.setStatus(status);
        return this;
    }
    public UsersDTOBuilder dateCreated(Date dateCreated){
        this.usersDTO.setDateCreated(dateCreated);
        return this;
    }
    public UsersDTOBuilder dateUpdated(Date dateUpdated){
        this.usersDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
