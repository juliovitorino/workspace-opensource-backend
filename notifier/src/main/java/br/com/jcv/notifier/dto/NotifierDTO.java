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


package br.com.jcv.notifier.dto;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.dto.DTOPadrao;
import br.com.jcv.commons.library.commodities.annotation.RegexValidation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.*;
import br.com.jcv.notifier.constantes.NotifierConstantes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
* NotifierDTO - Data Transfer Object
*
* @author Notifier
* @since Sat Dec 16 12:29:20 BRT 2023
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotifierDTO extends DTOPadrao implements Serializable
{
    
    @SerializedName(NotifierConstantes.APPLICATIONUUID)
    @JsonProperty(NotifierConstantes.APPLICATIONUUID)
    private UUID applicationUUID;
    
    @SerializedName(NotifierConstantes.USERUUID)
    @JsonProperty(NotifierConstantes.USERUUID)
    private UUID userUUID;
    
    @SerializedName(NotifierConstantes.TYPE)
    @JsonProperty(NotifierConstantes.TYPE)
    private String type;
    
    @SerializedName(NotifierConstantes.TITLE)
    @JsonProperty(NotifierConstantes.TITLE)
    private String title;
    
    @SerializedName(NotifierConstantes.DESCRIPTION)
    @JsonProperty(NotifierConstantes.DESCRIPTION)
    private String description;
    
    @SerializedName(NotifierConstantes.ISREADED)
    @JsonProperty(NotifierConstantes.ISREADED)
    private String isReaded;


    @SerializedName("mensagemResponse")
    @JsonProperty("mensagemResponse")
    private MensagemResponse mensagemResponse;
}
