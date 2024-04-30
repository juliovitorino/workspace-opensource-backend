package br.com.jcv.preferences.adapter.v1.business.systempreferences.create;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.jcv.preferences.adapter.v1.business.PreferencesBaseResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSystemPreferencesResponse extends PreferencesBaseResponse implements Serializable
{
}
