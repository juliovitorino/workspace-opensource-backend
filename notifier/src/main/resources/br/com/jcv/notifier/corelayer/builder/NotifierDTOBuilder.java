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

import br.com.jcv.notifier.corelayer.dto.NotifierDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class NotifierDTOBuilder {

    private NotifierDTO notifierDTO;

    private NotifierDTOBuilder(){}
    public static NotifierDTOBuilder newNotifierDTOTestBuilder() {
        NotifierDTOBuilder builder = new NotifierDTOBuilder();
        builder.notifierDTO = new NotifierDTO();
        return builder;
    }

    public NotifierDTO now() {
        return this.notifierDTO;
    }

    public NotifierDTOBuilder id(Long id){
        this.notifierDTO.setId(id);
        return this;
    }
    public NotifierDTOBuilder uuidExternalApp(UUID uuidExternalApp){
        this.notifierDTO.setUuidExternalApp(uuidExternalApp);
        return this;
    }
    public NotifierDTOBuilder uuidExternalUser(UUID uuidExternalUser){
        this.notifierDTO.setUuidExternalUser(uuidExternalUser);
        return this;
    }
    public NotifierDTOBuilder type(String type){
        this.notifierDTO.setType(type);
        return this;
    }
    public NotifierDTOBuilder key(String key){
        this.notifierDTO.setKey(key);
        return this;
    }
    public NotifierDTOBuilder title(String title){
        this.notifierDTO.setTitle(title);
        return this;
    }
    public NotifierDTOBuilder description(String description){
        this.notifierDTO.setDescription(description);
        return this;
    }
    public NotifierDTOBuilder urlImage(String urlImage){
        this.notifierDTO.setUrlImage(urlImage);
        return this;
    }
    public NotifierDTOBuilder iconClass(String iconClass){
        this.notifierDTO.setIconClass(iconClass);
        return this;
    }
    public NotifierDTOBuilder urlFollow(String urlFollow){
        this.notifierDTO.setUrlFollow(urlFollow);
        return this;
    }
    public NotifierDTOBuilder objectFree(String objectFree){
        this.notifierDTO.setObjectFree(objectFree);
        return this;
    }
    public NotifierDTOBuilder seenIndicator(String seenIndicator){
        this.notifierDTO.setSeenIndicator(seenIndicator);
        return this;
    }
    public NotifierDTOBuilder status(String status){
        this.notifierDTO.setStatus(status);
        return this;
    }
    public NotifierDTOBuilder dateCreated(Date dateCreated){
        this.notifierDTO.setDateCreated(dateCreated);
        return this;
    }
    public NotifierDTOBuilder dateUpdated(Date dateUpdated){
        this.notifierDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
