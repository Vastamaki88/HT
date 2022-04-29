package com.example.harjoitustyo;
//This class is used to get unique ID number for API calls
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
    //Counter for
    public int getNewID(){
        ID_count++;
        return ID_count;
    }
}
