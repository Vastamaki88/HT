package com.example.harjoitustyo;

public class IDClass {
    private static IDClass instance = null;
    int ID_count;

    private IDClass(){
        ID_count = 0;
    }

    public static IDClass getInstance(){
        if(instance == null){
        instance = new IDClass();
        }return instance;
    }

    public int getNewID(){
        ID_count++;
        return ID_count;
    }
}
