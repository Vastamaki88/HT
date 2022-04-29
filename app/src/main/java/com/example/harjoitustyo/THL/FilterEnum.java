package com.example.harjoitustyo.THL;
//URL-components are stored in this class
public enum FilterEnum {
    ALL("Kaikki"),
    SEX("&filter=sex-"),
    AGE("&filter=ttr10yage-"),
    REGION("&filter=hcdmunicipality2020-"),
    CITY("&filter=hcdmunicipality2020-");

    private String value;

    private FilterEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
