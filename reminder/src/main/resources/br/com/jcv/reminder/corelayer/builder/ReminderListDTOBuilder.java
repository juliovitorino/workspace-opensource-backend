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

import br.com.jcv.reminder.corelayer.dto.ReminderListDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ReminderListDTOBuilder {

    private ReminderListDTO reminderlistDTO;

    private ReminderListDTOBuilder(){}
    public static ReminderListDTOBuilder newReminderListDTOTestBuilder() {
        ReminderListDTOBuilder builder = new ReminderListDTOBuilder();
        builder.reminderlistDTO = new ReminderListDTO();
        return builder;
    }

    public ReminderListDTO now() {
        return this.reminderlistDTO;
    }

    public ReminderListDTOBuilder id(Long id){
        this.reminderlistDTO.setId(id);
        return this;
    }
    public ReminderListDTOBuilder uuidExternalApp(UUID uuidExternalApp){
        this.reminderlistDTO.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public ReminderListDTOBuilder uuidExternalUser(UUID uuidExternalUser){
        this.reminderlistDTO.setUuidExternalUser(uuidExternalUser);
        return this;
    }
    public ReminderListDTOBuilder uuidExternalList(UUID uuidExternalList){
        this.reminderlistDTO.setUuidExternalList(uuidExternalList);
        return this;
    }
    public ReminderListDTOBuilder title(String title){
        this.reminderlistDTO.setTitle(title);
        return this;
    }
    public ReminderListDTOBuilder status(String status){
        this.reminderlistDTO.setStatus(status);
        return this;
    }
    public ReminderListDTOBuilder dateCreated(Date dateCreated){
        this.reminderlistDTO.setDateCreated(dateCreated);
        return this;
    }
    public ReminderListDTOBuilder dateUpdated(Date dateUpdated){
        this.reminderlistDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
