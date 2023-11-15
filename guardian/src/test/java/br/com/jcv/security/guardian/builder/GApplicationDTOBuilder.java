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


package br.com.jcv.security.guardian.builder;

import br.com.jcv.security.guardian.dto.GApplicationDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class GApplicationDTOBuilder {

    private GApplicationDTO gapplicationDTO;

    private GApplicationDTOBuilder(){}
    public static GApplicationDTOBuilder newGApplicationDTOTestBuilder() {
        GApplicationDTOBuilder builder = new GApplicationDTOBuilder();
        builder.gapplicationDTO = new GApplicationDTO();
        return builder;
    }

    public GApplicationDTO now() {
        return this.gapplicationDTO;
    }

    public GApplicationDTOBuilder id(Long id){
        this.gapplicationDTO.setId(id);
        return this;
    }
    public GApplicationDTOBuilder name(String name){
        this.gapplicationDTO.setName(name);
        return this;
    }
    public GApplicationDTOBuilder externalCodeUUID(UUID externalCodeUUID){
        this.gapplicationDTO.setExternalCodeUUID(externalCodeUUID);
        return this;
    }
    public GApplicationDTOBuilder status(String status){
        this.gapplicationDTO.setStatus(status);
        return this;
    }
    public GApplicationDTOBuilder dateCreated(Date dateCreated){
        this.gapplicationDTO.setDateCreated(dateCreated);
        return this;
    }
    public GApplicationDTOBuilder dateUpdated(Date dateUpdated){
        this.gapplicationDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
