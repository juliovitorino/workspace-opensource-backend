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


package br.com.jcv.bei.corelayer.builder;

import br.com.jcv.bei.corelayer.dto.EconomicIndexDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class EconomicIndexDTOBuilder {

    private EconomicIndexDTO economicindexDTO;

    private EconomicIndexDTOBuilder(){}
    public static EconomicIndexDTOBuilder newEconomicIndexDTOTestBuilder() {
        EconomicIndexDTOBuilder builder = new EconomicIndexDTOBuilder();
        builder.economicindexDTO = new EconomicIndexDTO();
        return builder;
    }

    public EconomicIndexDTO now() {
        return this.economicindexDTO;
    }

    public EconomicIndexDTOBuilder id(Long id){
        this.economicindexDTO.setId(id);
        return this;
    }
    public EconomicIndexDTOBuilder economicIndex(String economicIndex){
        this.economicindexDTO.setEconomicIndex(economicIndex);
        return this;
    }
    public EconomicIndexDTOBuilder bacenSerieCode(String bacenSerieCode){
        this.economicindexDTO.setBacenSerieCode(bacenSerieCode);
        return this;
    }
    public EconomicIndexDTOBuilder lastDateValue(LocalDate lastDateValue){
        this.economicindexDTO.setLastDateValue(lastDateValue);
        return this;
    }
    public EconomicIndexDTOBuilder status(String status){
        this.economicindexDTO.setStatus(status);
        return this;
    }
    public EconomicIndexDTOBuilder dateCreated(Date dateCreated){
        this.economicindexDTO.setDateCreated(dateCreated);
        return this;
    }
    public EconomicIndexDTOBuilder dateUpdated(Date dateUpdated){
        this.economicindexDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
