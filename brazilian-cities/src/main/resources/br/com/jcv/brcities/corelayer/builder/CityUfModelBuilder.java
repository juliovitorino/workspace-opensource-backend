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

import br.com.jcv.brcities.corelayer.model.CityUf;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class CityUfModelBuilder {

    private CityUf cityuf;

    private CityUfModelBuilder(){}
    public static CityUfModelBuilder newCityUfModelTestBuilder() {
        CityUfModelBuilder builder = new CityUfModelBuilder();
        builder.cityuf = new CityUf();
        return builder;
    }

    public CityUf now() {
        return this.cityuf;
    }

    public CityUfModelBuilder id(Long id){
        this.cityuf.setId(id);
        return this;
    }
    public CityUfModelBuilder cityId(Long cityId){
        this.cityuf.setCityId(cityId);
        return this;
    }
    public CityUfModelBuilder ufId(String ufId){
        this.cityuf.setUfId(ufId);
        return this;
    }
    public CityUfModelBuilder ddd(String ddd){
        this.cityuf.setDdd(ddd);
        return this;
    }
    public CityUfModelBuilder latitude(Double latitude){
        this.cityuf.setLatitude(latitude);
        return this;
    }
    public CityUfModelBuilder longitude(Double longitude){
        this.cityuf.setLongitude(longitude);
        return this;
    }
    public CityUfModelBuilder status(String status){
        this.cityuf.setStatus(status);
        return this;
    }
    public CityUfModelBuilder dateCreated(Date dateCreated){
        this.cityuf.setDateCreated(dateCreated);
        return this;
    }
    public CityUfModelBuilder dateUpdated(Date dateUpdated){
        this.cityuf.setDateUpdated(dateUpdated);
        return this;
    }


}
