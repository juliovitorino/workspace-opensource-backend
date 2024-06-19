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

import br.com.jcv.brcities.corelayer.model.Uf;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class UfModelBuilder {

    private Uf uf;

    private UfModelBuilder(){}
    public static UfModelBuilder newUfModelTestBuilder() {
        UfModelBuilder builder = new UfModelBuilder();
        builder.uf = new Uf();
        return builder;
    }

    public Uf now() {
        return this.uf;
    }

    public UfModelBuilder id(Long id){
        this.uf.setId(id);
        return this;
    }
    public UfModelBuilder name(String name){
        this.uf.setName(name);
        return this;
    }
    public UfModelBuilder status(String status){
        this.uf.setStatus(status);
        return this;
    }
    public UfModelBuilder dateCreated(Date dateCreated){
        this.uf.setDateCreated(dateCreated);
        return this;
    }
    public UfModelBuilder dateUpdated(Date dateUpdated){
        this.uf.setDateUpdated(dateUpdated);
        return this;
    }


}
