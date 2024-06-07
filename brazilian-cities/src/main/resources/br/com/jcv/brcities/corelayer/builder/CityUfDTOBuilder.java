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

import br.com.jcv.brcities.corelayer.dto.CityUfDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class CityUfDTOBuilder {

    private CityUfDTO cityufDTO;

    private CityUfDTOBuilder(){}
    public static CityUfDTOBuilder newCityUfDTOTestBuilder() {
        CityUfDTOBuilder builder = new CityUfDTOBuilder();
        builder.cityufDTO = new CityUfDTO();
        return builder;
    }

    public CityUfDTO now() {
        return this.cityufDTO;
    }

    public CityUfDTOBuilder id(Long id){
        this.cityufDTO.setId(id);
        return this;
    }
    public CityUfDTOBuilder cityId(Long cityId){
        this.cityufDTO.setCityId(cityId);
        return this;
    }
    public CityUfDTOBuilder ufId(String ufId){
        this.cityufDTO.setUfId(ufId);
        return this;
    }
    public CityUfDTOBuilder ddd(String ddd){
        this.cityufDTO.setDdd(ddd);
        return this;
    }
    public CityUfDTOBuilder latitude(Double latitude){
        this.cityufDTO.setLatitude(latitude);
        return this;
    }
    public CityUfDTOBuilder longitude(Double longitude){
        this.cityufDTO.setLongitude(longitude);
        return this;
    }
    public CityUfDTOBuilder status(String status){
        this.cityufDTO.setStatus(status);
        return this;
    }
    public CityUfDTOBuilder dateCreated(Date dateCreated){
        this.cityufDTO.setDateCreated(dateCreated);
        return this;
    }
    public CityUfDTOBuilder dateUpdated(Date dateUpdated){
        this.cityufDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
