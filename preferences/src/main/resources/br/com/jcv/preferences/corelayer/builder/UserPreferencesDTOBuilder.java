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


package br.com.jcv.preferences.corelayer.builder;

import br.com.jcv.preferences.corelayer.dto.UserPreferencesDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class UserPreferencesDTOBuilder {

    private UserPreferencesDTO userpreferencesDTO;

    private UserPreferencesDTOBuilder(){}
    public static UserPreferencesDTOBuilder newUserPreferencesDTOTestBuilder() {
        UserPreferencesDTOBuilder builder = new UserPreferencesDTOBuilder();
        builder.userpreferencesDTO = new UserPreferencesDTO();
        return builder;
    }

    public UserPreferencesDTO now() {
        return this.userpreferencesDTO;
    }

    public UserPreferencesDTOBuilder id(Long id){
        this.userpreferencesDTO.setId(id);
        return this;
    }
    public UserPreferencesDTOBuilder uuidExternalApp(UUID uuidExternalApp){
        this.userpreferencesDTO.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public UserPreferencesDTOBuilder uuidExternalUser(UUID uuidExternalUser){
        this.userpreferencesDTO.setUuidExternalUser(uuidExternalUser);
        return this;
    }
    public UserPreferencesDTOBuilder key(String key){
        this.userpreferencesDTO.setKey(key);
        return this;
    }
    public UserPreferencesDTOBuilder preference(String preference){
        this.userpreferencesDTO.setPreference(preference);
        return this;
    }
    public UserPreferencesDTOBuilder status(String status){
        this.userpreferencesDTO.setStatus(status);
        return this;
    }
    public UserPreferencesDTOBuilder dateCreated(Date dateCreated){
        this.userpreferencesDTO.setDateCreated(dateCreated);
        return this;
    }
    public UserPreferencesDTOBuilder dateUpdated(Date dateUpdated){
        this.userpreferencesDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
