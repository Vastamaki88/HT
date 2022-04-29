package com.example.harjoitustyo.THL;

import java.util.ArrayList;

//Class for creating Objects from THL data
//This class is used for both THL filters and actual data
public class ThlObjects {
    private static ThlObjects instance = null;
    private ArrayList<ThlObject>thlObjects;
    private boolean initialized = false;

    private ThlObjects(){
        thlObjects = new ArrayList<ThlObject>();
    }
    public static ThlObjects getInstance(){
        if(instance==null){
            instance = new ThlObjects();
        }return instance;
    }
    public boolean isInitialized(){
        return initialized;
    }
    //ThlObjects class contains a list of ThlObject
    public void insertThlObject(ThlObject o){
        thlObjects.add(o);
        initialized = true;
    }
    public int objectsCount(){
        return thlObjects.size();
    }
    public ThlObject getThlObject(int i){
        return thlObjects.get(i);
    }

    //This is actual class for THL data
    public class ThlObject{
        String id;
        String label;
        ArrayList<Children>children;

        public ArrayList<Children> getChildren(){
            return children;
        }

        //Children class contains ArrayList<Children>
        //which means that it can call itself
        //There can be many layers in the THL data, and this class
        //will call itself as many time as needed
        public class Children{
            String id;
            String sid;
            String label;
            String stage;
            String code;
            String sort;
            String uri;
            ArrayList<Children>children;
            public String getSid(){
                return sid;
            }
            public ArrayList<Children> getChildren(){
                return children;
            }
            public String getLabel(){
                return label;
            }
            public String getID(){return id;}
        }
    }


}
