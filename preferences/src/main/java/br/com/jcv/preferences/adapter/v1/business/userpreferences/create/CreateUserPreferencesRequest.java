package br.com.jcv.preferences.adapter.v1.business.userpreferences.create;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import br.com.jcv.commons.library.commodities.dto.DTOPadrao;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.preferences.infrastructure.constantes.UserPreferencesConstantes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
* UserPreferencesDTO - Data Transfer Object
*
* @author UserPreferences
* @since Mon Apr 29 16:40:18 BRT 2024
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserPreferencesRequest implements Serializable
{
    private UUID uuidExternalApp;
    private UUID uuidExternalUser;
    private String key;
    private String preference;
}
