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


package br.com.jcv.security.guardian.dto;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.dto.DTOPadrao;
import br.com.jcv.commons.library.commodities.annotation.RegexValidation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.*;
import br.com.jcv.security.guardian.constantes.ApplicationUserConstantes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
* ApplicationUserDTO - Data Transfer Object
*
* @author ApplicationUser
* @since Thu Nov 16 09:03:29 BRT 2023
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationUserDTO extends DTOPadrao implements Serializable
{
    
    @SerializedName(ApplicationUserConstantes.IDAPPLICATION)
    @JsonProperty(ApplicationUserConstantes.IDAPPLICATION)
    private Long idApplication;

    @SerializedName(ApplicationUserConstantes.IDUSER)
    @JsonProperty(ApplicationUserConstantes.IDUSER)
    private Long idUser;

    @SerializedName(ApplicationUserConstantes.EMAIL)
    @JsonProperty(ApplicationUserConstantes.EMAIL)
    private String email;
    
    @SerializedName(ApplicationUserConstantes.ENCODEDPASSPHRASE)
    @JsonProperty(ApplicationUserConstantes.ENCODEDPASSPHRASE)
    private String encodedPassPhrase;
    
    @SerializedName(ApplicationUserConstantes.EXTERNALAPPUSERUUID)
    @JsonProperty(ApplicationUserConstantes.EXTERNALAPPUSERUUID)
    private UUID externalAppUserUUID;

    @SerializedName(ApplicationUserConstantes.EXTERNALUSERUUID)
    @JsonProperty(ApplicationUserConstantes.EXTERNALUSERUUID)
    private UUID externalUserUUID;
    
    @SerializedName(ApplicationUserConstantes.URLTOKENACTIVATION)
    @JsonProperty(ApplicationUserConstantes.URLTOKENACTIVATION)
    private String urlTokenActivation;
    
    @SerializedName(ApplicationUserConstantes.ACTIVATIONCODE)
    @JsonProperty(ApplicationUserConstantes.ACTIVATIONCODE)
    private String activationCode;
    
    @SerializedName(ApplicationUserConstantes.DUEDATEACTIVATION)
    @JsonProperty(ApplicationUserConstantes.DUEDATEACTIVATION)
    private Date dueDateActivation;


    @SerializedName("mensagemResponse")
    @JsonProperty("mensagemResponse")
    private MensagemResponse mensagemResponse;
}
