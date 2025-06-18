package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {
    @NotNull
    private String name;
    private String email;
    private String cellphone;

    @NotNull(message = "Birthday date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birthday must be a date in the past")
    private LocalDate birthday;

    private GenderEnum gender;
    private String masterLanguage;
    private String passwd;
    private String passwdCheck;
    @JsonProperty(value = "plan")
    private PlanRequest planRequest;
}
