package com.example.harjoitustyo;

import java.util.ArrayList;



public class ThlObjects {
    private static ThlObjects instance = null;
    private ArrayList<ThlObject>thlObjects;

    private ThlObjects(){
        thlObjects = new ArrayList<ThlObject>();
    }
    public static ThlObjects getInstance(){
        if(instance==null){
            instance = new ThlObjects();
        }return instance;
    }

    public void insertThlObject(ThlObject o){
        thlObjects.add(o);
    }

    class ThlObject{
        String id;
        String label;
        ArrayList<Children>children;


        class Children{
            String id;
            String sid;
            String label;
            String stage;
            String code;
            String sort;
            String uri;
            ArrayList<Children>children;
        }
    }

}
