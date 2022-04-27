package com.example.harjoitustyo.Graph;

import java.util.ArrayList;

public class SavedGraphs {
    private static SavedGraphs instance = null;
    ArrayList<String[]> dataset = new ArrayList<>();

    private SavedGraphs(){};

    public static SavedGraphs getInstance(){
        if(instance==null){
            instance = new SavedGraphs();
        }return instance;
    }

}
