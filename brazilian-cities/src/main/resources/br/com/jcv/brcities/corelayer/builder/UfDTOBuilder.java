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


package br.com.jcv.brcities.corelayer.builder;

import br.com.jcv.brcities.corelayer.dto.UfDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class UfDTOBuilder {

    private UfDTO ufDTO;

    private UfDTOBuilder(){}
    public static UfDTOBuilder newUfDTOTestBuilder() {
        UfDTOBuilder builder = new UfDTOBuilder();
        builder.ufDTO = new UfDTO();
        return builder;
    }

    public UfDTO now() {
        return this.ufDTO;
    }

    public UfDTOBuilder id(Long id){
        this.ufDTO.setId(id);
        return this;
    }
    public UfDTOBuilder name(String name){
        this.ufDTO.setName(name);
        return this;
    }
    public UfDTOBuilder status(String status){
        this.ufDTO.setStatus(status);
        return this;
    }
    public UfDTOBuilder dateCreated(Date dateCreated){
        this.ufDTO.setDateCreated(dateCreated);
        return this;
    }
    public UfDTOBuilder dateUpdated(Date dateUpdated){
        this.ufDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
