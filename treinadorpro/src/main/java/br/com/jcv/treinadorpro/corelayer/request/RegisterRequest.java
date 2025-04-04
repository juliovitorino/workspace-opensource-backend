package br.com.jcv.treinadorpro.corelayer.request;

import br.com.jcv.treinadorpro.corelayer.enums.GenderEnum;

import java.io.Serializable;
import java.time.LocalDate;

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

    public RegisterRequest(String firstName, String middleName, String lastName, String email, String cellphone, LocalDate birthday, GenderEnum gender, String masterLanguage, String passwd, String passwdCheck) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.cellphone = cellphone;
        this.birthday = birthday;
        this.gender = gender;
        this.masterLanguage = masterLanguage;
        this.passwd = passwd;
        this.passwdCheck = passwdCheck;
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

    public String getMasterLanguage() {
        return masterLanguage;
    }

    public void setMasterLanguage(String masterLanguage) {
        this.masterLanguage = masterLanguage;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswdCheck() {
        return passwdCheck;
    }

    public void setPasswdCheck(String passwdCheck) {
        this.passwdCheck = passwdCheck;
    }
}
