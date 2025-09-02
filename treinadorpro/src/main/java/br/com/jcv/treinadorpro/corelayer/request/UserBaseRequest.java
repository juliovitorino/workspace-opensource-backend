package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import br.com.jcv.treinadorpro.corelayer.enums.MasterLanguageEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public abstract class UserBaseRequest {

    private UUID uuidId;
    private String name;
    private String email;
    private String cellphone;
    private LocalDate birthday;
    private GenderEnum gender;
    private String urlPhotoProfile;
    private String masterLanguage;
}
