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


package br.com.jcv.notifier.builder;

import br.com.jcv.notifier.model.Notifier;

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
    public NotifierModelBuilder applicationUUID(UUID applicationUUID){
        this.notifier.setApplicationUUID(applicationUUID);
        return this;
    }
    public NotifierModelBuilder userUUID(UUID userUUID){
        this.notifier.setUserUUID(userUUID);
        return this;
    }
    public NotifierModelBuilder type(String type){
        this.notifier.setType(type);
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
    public NotifierModelBuilder isReaded(String isReaded){
        this.notifier.setIsReaded(isReaded);
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
