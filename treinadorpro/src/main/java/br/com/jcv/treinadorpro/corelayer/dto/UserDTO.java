package br.com.jcv.treinadorpro.corelayer.dto;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;
import br.com.jcv.treinadorpro.corelayer.enums.UserProfileEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String cellphone;
    private LocalDate birthday;
    private GenderEnum gender;
    private String urlPhotoProfile;
    private UserProfileEnum userProfile;
    private String masterLanguage;
    private String status;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO() {}

    public UserDTO(Long id, String firstName, String middleName, String lastName, String email, String cellphone, LocalDate birthday, GenderEnum gender, String urlPhotoProfile, UserProfileEnum userProfile, String masterLanguage, String status, LocalDateTime lastLogin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getUrlPhotoProfile() {
        return urlPhotoProfile;
    }

    public void setUrlPhotoProfile(String urlPhotoProfile) {
        this.urlPhotoProfile = urlPhotoProfile;
    }

    public UserProfileEnum getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileEnum userProfile) {
        this.userProfile = userProfile;
    }

    public String getMasterLanguage() {
        return masterLanguage;
    }

    public void setMasterLanguage(String masterLanguage) {
        this.masterLanguage = masterLanguage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", urlPhotoProfile='" + urlPhotoProfile + '\'' +
                ", userProfile=" + userProfile +
                ", masterLanguage='" + masterLanguage + '\'' +
                ", status='" + status + '\'' +
                ", lastLogin=" + lastLogin +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
