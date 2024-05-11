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

import br.com.jcv.reminder.corelayer.model.Reminder;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ReminderModelBuilder {

    private Reminder reminder;

    private ReminderModelBuilder(){}
    public static ReminderModelBuilder newReminderModelTestBuilder() {
        ReminderModelBuilder builder = new ReminderModelBuilder();
        builder.reminder = new Reminder();
        return builder;
    }

    public Reminder now() {
        return this.reminder;
    }

    public ReminderModelBuilder id(Long id){
        this.reminder.setId(id);
        return this;
    }
    public ReminderModelBuilder idList(Long idList){
        this.reminder.setIdList(idList);
        return this;
    }
    public ReminderModelBuilder title(String title){
        this.reminder.setTitle(title);
        return this;
    }
    public ReminderModelBuilder note(String note){
        this.reminder.setNote(note);
        return this;
    }
    public ReminderModelBuilder tags(String tags){
        this.reminder.setTags(tags);
        return this;
    }
    public ReminderModelBuilder fullUrlImage(String fullUrlImage){
        this.reminder.setFullUrlImage(fullUrlImage);
        return this;
    }
    public ReminderModelBuilder url(String url){
        this.reminder.setUrl(url);
        return this;
    }
    public ReminderModelBuilder priority(String priority){
        this.reminder.setPriority(priority);
        return this;
    }
    public ReminderModelBuilder flag(String flag){
        this.reminder.setFlag(flag);
        return this;
    }
    public ReminderModelBuilder dueDate(Date dueDate){
        this.reminder.setDueDate(dueDate);
        return this;
    }
    public ReminderModelBuilder status(String status){
        this.reminder.setStatus(status);
        return this;
    }
    public ReminderModelBuilder dateCreated(Date dateCreated){
        this.reminder.setDateCreated(dateCreated);
        return this;
    }
    public ReminderModelBuilder dateUpdated(Date dateUpdated){
        this.reminder.setDateUpdated(dateUpdated);
        return this;
    }


}
