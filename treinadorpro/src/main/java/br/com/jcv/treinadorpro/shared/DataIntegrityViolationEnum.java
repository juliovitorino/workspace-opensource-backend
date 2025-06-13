package br.com.jcv.treinadorpro.shared;

import java.util.Arrays;

public enum DataIntegrityViolationEnum {
    uix_upt_pack_training_id_student_user_id("Its' not possible to create a User Pack Training twice"),
    users_email_key("email already exists"),
    cellphone("Invalid user cellphone")
    ;

    final String friendlyMessage;

    DataIntegrityViolationEnum(String friendlyMessage) {
        this.friendlyMessage = friendlyMessage;
    }

    public static DataIntegrityViolationEnum fromKey(String key) {
        return Arrays.stream(DataIntegrityViolationEnum.values())
                .filter(keyItem -> keyItem.name().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }
}
