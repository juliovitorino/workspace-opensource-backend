package br.com.jcv.treinadorpro.corelayer.response;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @JsonProperty("id")
    protected Long id;

    @JsonProperty("uuidId")
    protected UUID uuidId;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("email")
    protected String email;

    @JsonProperty("cellphone")
    protected String cellphone;

    @JsonProperty("birthday")
    protected LocalDate birthday;

    @JsonProperty("gender")
    protected GenderEnum gender;

    @JsonProperty("urlPhotoProfile")
    protected String urlPhotoProfile;

    @JsonProperty("userProfile")
    protected UserProfileEnum userProfile;

    @JsonProperty("masterLanguage")
    protected String masterLanguage;

    @JsonProperty("guardianIntegrationUUID")
    protected UUID guardianIntegrationUUID;

    @JsonProperty("status")
    protected StatusEnum status;

    @JsonProperty("lastLogin")
    protected LocalDateTime lastLogin;

    @JsonProperty("createdAt")
    protected LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    protected LocalDateTime updatedAt;

    public UserResponse(
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
        this.id = id;
        this.uuidId = uuidId;
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.birthday = birthday;
        this.gender = gender;
        this.urlPhotoProfile = urlPhotoProfile;
        this.userProfile = userProfile;
        this.masterLanguage = masterLanguage;
        this.status = status;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
