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

import br.com.jcv.preferences.corelayer.model.UserPreferences;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class UserPreferencesModelBuilder {

    private UserPreferences userpreferences;

    private UserPreferencesModelBuilder(){}
    public static UserPreferencesModelBuilder newUserPreferencesModelTestBuilder() {
        UserPreferencesModelBuilder builder = new UserPreferencesModelBuilder();
        builder.userpreferences = new UserPreferences();
        return builder;
    }

    public UserPreferences now() {
        return this.userpreferences;
    }

    public UserPreferencesModelBuilder id(Long id){
        this.userpreferences.setId(id);
        return this;
    }
    public UserPreferencesModelBuilder uuidExternalApp(UUID uuidExternalApp){
        this.userpreferences.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public UserPreferencesModelBuilder uuidExternalUser(UUID uuidExternalUser){
        this.userpreferences.setUuidExternalUser(uuidExternalUser);
        return this;
    }
    public UserPreferencesModelBuilder key(String key){
        this.userpreferences.setKey(key);
        return this;
    }
    public UserPreferencesModelBuilder preference(String preference){
        this.userpreferences.setPreference(preference);
        return this;
    }
    public UserPreferencesModelBuilder status(String status){
        this.userpreferences.setStatus(status);
        return this;
    }
    public UserPreferencesModelBuilder dateCreated(Date dateCreated){
        this.userpreferences.setDateCreated(dateCreated);
        return this;
    }
    public UserPreferencesModelBuilder dateUpdated(Date dateUpdated){
        this.userpreferences.setDateUpdated(dateUpdated);
        return this;
    }


}
