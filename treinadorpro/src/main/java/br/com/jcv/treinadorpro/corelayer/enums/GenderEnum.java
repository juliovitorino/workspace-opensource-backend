package br.com.jcv.treinadorpro.corelayer.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    M("MALE_TAG_TRANSLATOR"),
    F("FEMALE_TAG_TRANSLATOR");

    private final String tagTranslator;

    GenderEnum(String tagTranslator) {
        this.tagTranslator= tagTranslator;
    }
}
