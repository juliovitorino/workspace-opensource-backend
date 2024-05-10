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


package br.com.jcv.notifier.corelayer.builder;

import br.com.jcv.notifier.corelayer.model.Notifier;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class NotifierModelBuilder {

    private Notifier notifier;

    private NotifierModelBuilder(){}
    public static NotifierModelBuilder newNotifierModelTestBuilder() {
        NotifierModelBuilder builder = new NotifierModelBuilder();
        builder.notifier = new Notifier();
        return builder;
    }

    public Notifier now() {
        return this.notifier;
    }

    public NotifierModelBuilder id(Long id){
        this.notifier.setId(id);
        return this;
    }
    public NotifierModelBuilder uuidExternalApp(UUID uuidExternalApp){
        this.notifier.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public NotifierModelBuilder uuidExternalUser(UUID uuidExternalUser){
        this.notifier.setUuidExternalUser(uuidExternalUser);
        return this;
    }
    public NotifierModelBuilder type(String type){
        this.notifier.setType(type);
        return this;
    }
    public NotifierModelBuilder key(String key){
        this.notifier.setKey(key);
        return this;
    }
    public NotifierModelBuilder title(String title){
        this.notifier.setTitle(title);
        return this;
    }
    public NotifierModelBuilder description(String description){
        this.notifier.setDescription(description);
        return this;
    }
    public NotifierModelBuilder urlImage(String urlImage){
        this.notifier.setUrlImage(urlImage);
        return this;
    }
    public NotifierModelBuilder iconClass(String iconClass){
        this.notifier.setIconClass(iconClass);
        return this;
    }
    public NotifierModelBuilder urlFollow(String urlFollow){
        this.notifier.setUrlFollow(urlFollow);
        return this;
    }
    public NotifierModelBuilder objectFree(String objectFree){
        this.notifier.setObjectFree(objectFree);
        return this;
    }
    public NotifierModelBuilder seenIndicator(String seenIndicator){
        this.notifier.setSeenIndicator(seenIndicator);
        return this;
    }
    public NotifierModelBuilder status(String status){
        this.notifier.setStatus(status);
        return this;
    }
    public NotifierModelBuilder dateCreated(Date dateCreated){
        this.notifier.setDateCreated(dateCreated);
        return this;
    }
    public NotifierModelBuilder dateUpdated(Date dateUpdated){
        this.notifier.setDateUpdated(dateUpdated);
        return this;
    }


}
