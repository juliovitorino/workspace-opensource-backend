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

import br.com.jcv.bei.corelayer.dto.EconomicIndexDataDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class EconomicIndexDataDTOBuilder {

    private EconomicIndexDataDTO economicindexdataDTO;

    private EconomicIndexDataDTOBuilder(){}
    public static EconomicIndexDataDTOBuilder newEconomicIndexDataDTOTestBuilder() {
        EconomicIndexDataDTOBuilder builder = new EconomicIndexDataDTOBuilder();
        builder.economicindexdataDTO = new EconomicIndexDataDTO();
        return builder;
    }

    public EconomicIndexDataDTO now() {
        return this.economicindexdataDTO;
    }

    public EconomicIndexDataDTOBuilder id(Long id){
        this.economicindexdataDTO.setId(id);
        return this;
    }
    public EconomicIndexDataDTOBuilder economicIndexId(Long economicIndexId){
        this.economicindexdataDTO.setEconomicIndexId(economicIndexId);
        return this;
    }
    public EconomicIndexDataDTOBuilder indexDate(LocalDate indexDate){
        this.economicindexdataDTO.setIndexDate(indexDate);
        return this;
    }
    public EconomicIndexDataDTOBuilder indexValue(Double indexValue){
        this.economicindexdataDTO.setIndexValue(indexValue);
        return this;
    }
    public EconomicIndexDataDTOBuilder status(String status){
        this.economicindexdataDTO.setStatus(status);
        return this;
    }
    public EconomicIndexDataDTOBuilder dateCreated(Date dateCreated){
        this.economicindexdataDTO.setDateCreated(dateCreated);
        return this;
    }
    public EconomicIndexDataDTOBuilder dateUpdated(Date dateUpdated){
        this.economicindexdataDTO.setDateUpdated(dateUpdated);
        return this;
    }


}
