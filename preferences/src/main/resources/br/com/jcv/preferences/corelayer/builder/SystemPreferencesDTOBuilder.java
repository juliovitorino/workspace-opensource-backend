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

import br.com.jcv.preferences.corelayer.dto.SystemPreferencesDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class SystemPreferencesDTOBuilder {

    private SystemPreferencesDTO systempreferencesDTO;

    private SystemPreferencesDTOBuilder(){}
    public static SystemPreferencesDTOBuilder newSystemPreferencesDTOTestBuilder() {
        SystemPreferencesDTOBuilder builder = new SystemPreferencesDTOBuilder();
        builder.systempreferencesDTO = new SystemPreferencesDTO();
        return builder;
    }

    public SystemPreferencesDTO now() {
        return this.systempreferencesDTO;
    }

    public SystemPreferencesDTOBuilder id(Long id){
        this.systempreferencesDTO.setId(id);
        return this;
    }
    public SystemPreferencesDTOBuilder uuidExternalApp(UUID uuidExternalApp){
        this.systempreferencesDTO.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public SystemPreferencesDTOBuilder key(String key){
        this.systempreferencesDTO.setKey(key);
        return this;
    }
    public SystemPreferencesDTOBuilder preference(String preference){
        this.systempreferencesDTO.setPreference(preference);
        return this;
    }
    public SystemPreferencesDTOBuilder status(String status){
        this.systempreferencesDTO.setStatus(status);
        return this;
    }
    public SystemPreferencesDTOBuilder dateCreated(Date dateCreated){
        this.systempreferencesDTO.setDateCreated(dateCreated);
        return this;
    }
    public SystemPreferencesDTOBuilder dateUpdated(Date dateUpdated){
        this.systempreferencesDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
