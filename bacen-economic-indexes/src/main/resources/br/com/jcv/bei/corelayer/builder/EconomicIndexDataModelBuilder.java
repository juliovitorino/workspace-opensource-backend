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

import br.com.jcv.bei.corelayer.model.EconomicIndexData;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class EconomicIndexDataModelBuilder {

    private EconomicIndexData economicindexdata;

    private EconomicIndexDataModelBuilder(){}
    public static EconomicIndexDataModelBuilder newEconomicIndexDataModelTestBuilder() {
        EconomicIndexDataModelBuilder builder = new EconomicIndexDataModelBuilder();
        builder.economicindexdata = new EconomicIndexData();
        return builder;
    }

    public EconomicIndexData now() {
        return this.economicindexdata;
    }

    public EconomicIndexDataModelBuilder id(Long id){
        this.economicindexdata.setId(id);
        return this;
    }
    public EconomicIndexDataModelBuilder economicIndexId(Long economicIndexId){
        this.economicindexdata.setEconomicIndexId(economicIndexId);
        return this;
    }
    public EconomicIndexDataModelBuilder indexDate(LocalDate indexDate){
        this.economicindexdata.setIndexDate(indexDate);
        return this;
    }
    public EconomicIndexDataModelBuilder indexValue(Double indexValue){
        this.economicindexdata.setIndexValue(indexValue);
        return this;
    }
    public EconomicIndexDataModelBuilder status(String status){
        this.economicindexdata.setStatus(status);
        return this;
    }
    public EconomicIndexDataModelBuilder dateCreated(Date dateCreated){
        this.economicindexdata.setDateCreated(dateCreated);
        return this;
    }
    public EconomicIndexDataModelBuilder dateUpdated(Date dateUpdated){
        this.economicindexdata.setDateUpdated(dateUpdated);
        return this;
    }


}
