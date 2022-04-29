package com.example.harjoitustyo;
//Stores the language selection from settings fragment
public class SettingsClass {
    private static SettingsClass instance = null;
    private String language = "English";
    boolean changed = false;

    private SettingsClass(){
    }

    public static SettingsClass getInstance(){
        if(instance == null){
            instance = new SettingsClass();
        }return instance;
    }

    public void setLanguage(String s){
        language = s;
    }
    public String getLanguage(){
        return language;
    }
}
