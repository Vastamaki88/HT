package com.example.harjoitustyo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class ThlResultObjects {
    private static ThlResultObjects instance = null;
    private boolean initialized = false;
    private ArrayList<dataset> thlResultObjects;

    private ThlResultObjects(){
    }

    public static ThlResultObjects getInstance(){
        if(instance==null){
            instance = new ThlResultObjects();
        }return instance;
    }
    public boolean isInitialized(){
        return initialized;
    }

    public void insertObject(dataset o){
    thlResultObjects.add(o);
    }


    class dataset {
        private String version;
        @SerializedName("class")
        private String mclass;
        private String label;
        private Dimension dimension;
        private Value value;

        class Dimension{
            String id;
            String size;
        }
        class Value{
            int[]cases;
        }
    }

}
