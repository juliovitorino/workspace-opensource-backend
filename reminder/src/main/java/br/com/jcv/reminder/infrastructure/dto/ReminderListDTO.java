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


package br.com.jcv.reminder.infrastructure.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import br.com.jcv.commons.library.commodities.dto.DTOPadrao;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.reminder.infrastructure.constantes.ReminderListConstantes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
* ReminderListDTO - Data Transfer Object
*
* @author ReminderList
* @since Sat May 11 17:44:44 BRT 2024
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReminderListDTO extends DTOPadrao implements Serializable
{
    
    @SerializedName(ReminderListConstantes.UUIDEXTERNALAPP)
    @JsonProperty(ReminderListConstantes.UUIDEXTERNALAPP)
    private UUID uuidExternalApp;
    
    @SerializedName(ReminderListConstantes.UUIDEXTERNALUSER)
    @JsonProperty(ReminderListConstantes.UUIDEXTERNALUSER)
    private UUID uuidExternalUser;
    
    @SerializedName(ReminderListConstantes.UUIDEXTERNALLIST)
    @JsonProperty(ReminderListConstantes.UUIDEXTERNALLIST)
    private UUID uuidExternalList;
    
    @SerializedName(ReminderListConstantes.TITLE)
    @JsonProperty(ReminderListConstantes.TITLE)
    private String title;


    @SerializedName("mensagemResponse")
    @JsonProperty("mensagemResponse")
    private MensagemResponse mensagemResponse;
}