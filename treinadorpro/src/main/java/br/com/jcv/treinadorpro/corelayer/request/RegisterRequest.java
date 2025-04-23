package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String cellphone;
    private LocalDate birthday;
    private GenderEnum gender;
    private String masterLanguage;
    private String passwd;
    private String passwdCheck;
    @JsonProperty(value = "plan")
    private PlanRequest planRequest;
}
