package com.example.harjoitustyo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


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

    class ThlObject{
        String id;
        String label;
        ArrayList<Children>children;

        public ArrayList<Children> getChildren(){
            return children;
        }

        class Children{
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
    public ArrayList<String> getRegions(){
        HashMap<String, String> regions = new HashMap();
        try{
            ArrayList<ThlObject.Children> regionObjects = ThlObjects.getInstance().getThlObject(0).getChildren().get(0).getChildren();
            for(ThlObject.Children city : regionObjects){
                regions.put(city.getLabel(),city.getSid());
                System.out.println("test");
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = regions.keySet();
        ArrayList<String>regionsList = new ArrayList<>(keySet);
        return regionsList;
    }
    public ArrayList<String> getCities(){
        HashMap<String, String> cities = new HashMap();
        try{
            ArrayList<ThlObject.Children> cityObjects = ThlObjects.getInstance().getThlObject(0).getChildren().get(0).getChildren();
            for(ThlObject.Children region : cityObjects){
                for(ThlObject.Children city :region.getChildren()) {
                    cities.put(city.getLabel(), city.getSid());
                }
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = cities.keySet();
        ArrayList<String>citiesList = new ArrayList<>(keySet);

        return citiesList;
    }
    public ArrayList<String> getSensors(){
        HashMap<String, String> sensors = new HashMap();
        try{
            ArrayList<ThlObject.Children> sensorObjects = ThlObjects.getInstance().getThlObject(4).getChildren().get(0).getChildren();
            for(ThlObject.Children city : sensorObjects){
                sensors.put(city.getLabel(),city.getSid());
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = sensors.keySet();
        ArrayList<String>sensorsList = new ArrayList<>(keySet);

        return sensorsList;
    }
    public ArrayList<String> getAge(){
        HashMap<String, String> ages = new HashMap();
        try{
            ArrayList<ThlObject.Children> ageObjects = ThlObjects.getInstance().getThlObject(2).getChildren().get(0).getChildren();
            for(ThlObject.Children age : ageObjects){
                ages.put(age.getLabel(),age.getSid());
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = ages.keySet();
        ArrayList<String>regionsList = new ArrayList<>(keySet);

        return regionsList;
    }
    public ArrayList<String> getSexes(){
        HashMap<String, String> sexes = new HashMap();
        try{
            ArrayList<ThlObject.Children> sexObjects = ThlObjects.getInstance().getThlObject(3).getChildren().get(0).getChildren();
            for(ThlObject.Children city : sexObjects){
                sexes.put(city.getLabel(),city.getSid());
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = sexes.keySet();
        ArrayList<String>regionsList = new ArrayList<>(keySet);

        return regionsList;
    }

}
