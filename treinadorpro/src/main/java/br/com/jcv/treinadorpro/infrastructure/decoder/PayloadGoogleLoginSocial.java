package br.com.jcv.treinadorpro.infrastructure.decoder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayloadGoogleLoginSocial implements IPayloadLoginSocial{
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;

    @JsonProperty("email_verified")
    private String emailVerified;
    private String name;
    private String picture;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    private Long iat;
    private Long exp;
}
