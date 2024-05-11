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


package br.com.jcv.reminder.corelayer.builder;

import br.com.jcv.reminder.corelayer.model.ListReminder;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ListReminderModelBuilder {

    private ListReminder listreminder;

    private ListReminderModelBuilder(){}
    public static ListReminderModelBuilder newListReminderModelTestBuilder() {
        ListReminderModelBuilder builder = new ListReminderModelBuilder();
        builder.listreminder = new ListReminder();
        return builder;
    }

    public ListReminder now() {
        return this.listreminder;
    }

    public ListReminderModelBuilder id(Long id){
        this.listreminder.setId(id);
        return this;
    }
    public ListReminderModelBuilder uuidExternalApp(UUID uuidExternalApp){
        this.listreminder.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public ListReminderModelBuilder uuidExternalUser(UUID uuidExternalUser){
        this.listreminder.setUuidExternalUser(uuidExternalUser);
        return this;
    }
    public ListReminderModelBuilder uuidExternalList(UUID uuidExternalList){
        this.listreminder.setUuidExternalList(uuidExternalList);
        return this;
    }
    public ListReminderModelBuilder title(String title){
        this.listreminder.setTitle(title);
        return this;
    }
    public ListReminderModelBuilder status(String status){
        this.listreminder.setStatus(status);
        return this;
    }
    public ListReminderModelBuilder dateCreated(Date dateCreated){
        this.listreminder.setDateCreated(dateCreated);
        return this;
    }
    public ListReminderModelBuilder dateUpdated(Date dateUpdated){
        this.listreminder.setDateUpdated(dateUpdated);
        return this;
    }


}
