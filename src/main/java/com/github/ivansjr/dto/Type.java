package com.github.ivansjr.dto;

public enum Type {
    SOLO( "solo"),
    DUO("dupla"),
    BAND("banda");

    private final String typeTranslate;

    Type(String typeTranslate) {
        this.typeTranslate = typeTranslate;
    }

    public static Type fromTranslate(String text) {
        for (Type category : Type.values()) {
            if (category.typeTranslate.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
