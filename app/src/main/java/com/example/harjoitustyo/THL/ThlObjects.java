package com.example.harjoitustyo.THL;

import java.util.ArrayList;


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

    public class ThlObject{
        String id;
        String label;
        ArrayList<Children>children;

        public ArrayList<Children> getChildren(){
            return children;
        }

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
