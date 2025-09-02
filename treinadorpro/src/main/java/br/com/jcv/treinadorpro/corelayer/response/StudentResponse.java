package br.com.jcv.treinadorpro.corelayer.response;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentResponse extends UserResponse implements Serializable {

    public StudentResponse() {}

    public StudentResponse(
            Long id,
            UUID uuidId,
            String name,
            String email,
            String cellphone,
            LocalDate birthday,
            GenderEnum gender,
            String urlPhotoProfile,
            UserProfileEnum userProfile,
            String masterLanguage,
            StatusEnum status,
            LocalDateTime lastLogin,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        super(id,uuidId,name,email,cellphone,birthday,gender,urlPhotoProfile,userProfile,masterLanguage,status,lastLogin,createdAt,updatedAt);
    }


}
