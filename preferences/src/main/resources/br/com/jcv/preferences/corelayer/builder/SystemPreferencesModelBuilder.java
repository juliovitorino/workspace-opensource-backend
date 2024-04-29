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

import br.com.jcv.preferences.corelayer.model.SystemPreferences;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class SystemPreferencesModelBuilder {

    private SystemPreferences systempreferences;

    private SystemPreferencesModelBuilder(){}
    public static SystemPreferencesModelBuilder newSystemPreferencesModelTestBuilder() {
        SystemPreferencesModelBuilder builder = new SystemPreferencesModelBuilder();
        builder.systempreferences = new SystemPreferences();
        return builder;
    }

    public SystemPreferences now() {
        return this.systempreferences;
    }

    public SystemPreferencesModelBuilder id(Long id){
        this.systempreferences.setId(id);
        return this;
    }
    public SystemPreferencesModelBuilder uuidExternalApp(UUID uuidExternalApp){
        this.systempreferences.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public SystemPreferencesModelBuilder key(String key){
        this.systempreferences.setKey(key);
        return this;
    }
    public SystemPreferencesModelBuilder preference(String preference){
        this.systempreferences.setPreference(preference);
        return this;
    }
    public SystemPreferencesModelBuilder status(String status){
        this.systempreferences.setStatus(status);
        return this;
    }
    public SystemPreferencesModelBuilder dateCreated(Date dateCreated){
        this.systempreferences.setDateCreated(dateCreated);
        return this;
    }
    public SystemPreferencesModelBuilder dateUpdated(Date dateUpdated){
        this.systempreferences.setDateUpdated(dateUpdated);
        return this;
    }


}
