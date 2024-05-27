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

import br.com.jcv.bei.corelayer.model.EconomicIndex;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class EconomicIndexModelBuilder {

    private EconomicIndex economicindex;

    private EconomicIndexModelBuilder(){}
    public static EconomicIndexModelBuilder newEconomicIndexModelTestBuilder() {
        EconomicIndexModelBuilder builder = new EconomicIndexModelBuilder();
        builder.economicindex = new EconomicIndex();
        return builder;
    }

    public EconomicIndex now() {
        return this.economicindex;
    }

    public EconomicIndexModelBuilder id(Long id){
        this.economicindex.setId(id);
        return this;
    }
    public EconomicIndexModelBuilder economicIndex(String economicIndex){
        this.economicindex.setEconomicIndex(economicIndex);
        return this;
    }
    public EconomicIndexModelBuilder bacenSerieCode(String bacenSerieCode){
        this.economicindex.setBacenSerieCode(bacenSerieCode);
        return this;
    }
    public EconomicIndexModelBuilder lastDateValue(LocalDate lastDateValue){
        this.economicindex.setLastDateValue(lastDateValue);
        return this;
    }
    public EconomicIndexModelBuilder status(String status){
        this.economicindex.setStatus(status);
        return this;
    }
    public EconomicIndexModelBuilder dateCreated(Date dateCreated){
        this.economicindex.setDateCreated(dateCreated);
        return this;
    }
    public EconomicIndexModelBuilder dateUpdated(Date dateUpdated){
        this.economicindex.setDateUpdated(dateUpdated);
        return this;
    }


}
