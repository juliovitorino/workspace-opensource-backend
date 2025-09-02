package br.com.jcv.treinadorpro.corelayer.enums;

import lombok.Getter;
import org.codehaus.plexus.interpolation.ValueSource;

import java.util.Arrays;

@Getter
public enum MasterLanguageEnum {
    PT_BR("pt-BR"),
    EN_US("en-US"),
    ES_ES("es-ES")
        ;

    private final String language;

    MasterLanguageEnum(String language) {
        this.language = language;
    }

    public static MasterLanguageEnum findByString(String language) {
        return Arrays.stream(MasterLanguageEnum.values()).filter(item -> item.language.equals(language)).findFirst().orElse(null);
    }
}