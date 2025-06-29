package br.com.jcv.treinadorpro.corelayer.response;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class PersonalTrainerResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("uuidId")
    private UUID uuidId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("cellphone")
    private String cellphone;

    @JsonProperty("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonProperty("gender")
    private GenderEnum gender;

    @JsonProperty("urlPhotoProfile")
    private String urlPhotoProfile;

    @JsonProperty("userProfile")
    private UserProfileEnum userProfile;

    @JsonProperty("masterLanguage")
    private String masterLanguage;

    @JsonProperty("guardianIntegration")
    private UUID guardianIntegration;

    @JsonProperty("lastLogin")
    private LocalDateTime lastLogin;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("personalFeature")
    private PersonalFeatureResponse personalFeature;
}
