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

import br.com.jcv.security.guardian.model.GApplication;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class GApplicationModelBuilder {

    private GApplication gapplication;

    private GApplicationModelBuilder(){}
    public static GApplicationModelBuilder newGApplicationModelTestBuilder() {
        GApplicationModelBuilder builder = new GApplicationModelBuilder();
        builder.gapplication = new GApplication();
        return builder;
    }

    public GApplication now() {
        return this.gapplication;
    }

    public GApplicationModelBuilder id(Long id){
        this.gapplication.setId(id);
        return this;
    }
    public GApplicationModelBuilder name(String name){
        this.gapplication.setName(name);
        return this;
    }
    public GApplicationModelBuilder externalCodeUUID(UUID externalCodeUUID){
        this.gapplication.setExternalCodeUUID(externalCodeUUID);
        return this;
    }
    public GApplicationModelBuilder status(String status){
        this.gapplication.setStatus(status);
        return this;
    }
    public GApplicationModelBuilder dateCreated(Date dateCreated){
        this.gapplication.setDateCreated(dateCreated);
        return this;
    }
    public GApplicationModelBuilder dateUpdated(Date dateUpdated){
        this.gapplication.setDateUpdated(dateUpdated);
        return this;
    }


}
