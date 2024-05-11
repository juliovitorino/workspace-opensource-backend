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

import br.com.jcv.reminder.corelayer.dto.ReminderDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ReminderDTOBuilder {

    private ReminderDTO reminderDTO;

    private ReminderDTOBuilder(){}
    public static ReminderDTOBuilder newReminderDTOTestBuilder() {
        ReminderDTOBuilder builder = new ReminderDTOBuilder();
        builder.reminderDTO = new ReminderDTO();
        return builder;
    }

    public ReminderDTO now() {
        return this.reminderDTO;
    }

    public ReminderDTOBuilder id(Long id){
        this.reminderDTO.setId(id);
        return this;
    }
    public ReminderDTOBuilder idList(Long idList){
        this.reminderDTO.setIdList(idList);
        return this;
    }
    public ReminderDTOBuilder title(String title){
        this.reminderDTO.setTitle(title);
        return this;
    }
    public ReminderDTOBuilder note(String note){
        this.reminderDTO.setNote(note);
        return this;
    }
    public ReminderDTOBuilder tags(String tags){
        this.reminderDTO.setTags(tags);
        return this;
    }
    public ReminderDTOBuilder fullUrlImage(String fullUrlImage){
        this.reminderDTO.setFullUrlImage(fullUrlImage);
        return this;
    }
    public ReminderDTOBuilder url(String url){
        this.reminderDTO.setUrl(url);
        return this;
    }
    public ReminderDTOBuilder priority(String priority){
        this.reminderDTO.setPriority(priority);
        return this;
    }
    public ReminderDTOBuilder flag(String flag){
        this.reminderDTO.setFlag(flag);
        return this;
    }
    public ReminderDTOBuilder dueDate(Date dueDate){
        this.reminderDTO.setDueDate(dueDate);
        return this;
    }
    public ReminderDTOBuilder status(String status){
        this.reminderDTO.setStatus(status);
        return this;
    }
    public ReminderDTOBuilder dateCreated(Date dateCreated){
        this.reminderDTO.setDateCreated(dateCreated);
        return this;
    }
    public ReminderDTOBuilder dateUpdated(Date dateUpdated){
        this.reminderDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
