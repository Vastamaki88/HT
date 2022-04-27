package com.example.harjoitustyo;

import android.content.Context;

public class ContextClass {
    Context context;
    public static ContextClass instance = null;

    private ContextClass(){

    }

    public static ContextClass getInstance(){
        if(instance==null){
            instance = new ContextClass();
        }return instance;
    }
    public void setContext(Context c){
        this.context = c;
    }
    public Context getContext(){
        return context;
    }
}
