package br.com.jcv.security.guardian.controller.v1.business.createaccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateNewAccountRequest implements Serializable {
    @NotNull
    private UUID externalApplicationUUID;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotNull
    private LocalDate birthday;
    @NotEmpty
    private String passwd;
    @NotEmpty
    private String passwdCheck;
}
