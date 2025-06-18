package br.com.jcv.treinadorpro.corelayer.dto;

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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserDTO {

    private Long id;
    private UUID uuidId;
    private String name;
    private String email;
    private String cellphone;
    private LocalDate birthday;
    private GenderEnum gender;
    private String urlPhotoProfile;
    private UserProfileEnum userProfile;
    private MasterLanguageEnum masterLanguage;
    private UUID guardianIntegrationUUID;
    private StatusEnum status;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
