package com.example.harjoitustyo.THL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
//This class contains all the filters which can be used
//to limit the data. This class is initialized at the start up of th program
public class ThlFilterItems {
    private static ThlFilterItems instance;
    private ArrayList<String>regionsList = new ArrayList<>();
    private ArrayList<String>agesList = new ArrayList<>();
    private ArrayList<String>citiesList = new ArrayList<>();
    private ArrayList<String>sexesList = new ArrayList<>();
    private ArrayList<String>sensorsList = new ArrayList<>();
    private HashMap<String, String>regionsHM = new HashMap<>();
    private HashMap<String, String>agesHM = new HashMap<>();
    private HashMap<String, String>sexesHM = new HashMap<>();
    private HashMap<String, String>sensorsHM = new HashMap<>();
    private HashMap<String, String>citiesHM = new HashMap<>();

    private ThlFilterItems(){
        regionsHM = getRegions();
        agesHM = getAge();
        sensorsHM = getSensors();
        citiesHM = getCities();
        sexesHM = getSexes();
        regionsList = getList(regionsHM);
        agesList = getList(agesHM);
        sensorsList = getList2(sensorsHM);
        citiesList = getList(citiesHM);
        sexesList = getList(sexesHM);

    }
    //Getters
    public ArrayList<String> getRegionsList() {
        return regionsList;
    }
    public ArrayList<String> getAgesList() {
        return agesList;
    }
    public ArrayList<String> getCitiesList() {
        return citiesList;
    }
    public ArrayList<String> getSexesList() {
        return sexesList;
    }
    public ArrayList<String> getSensorsList() {
        return sensorsList;
    }
    public HashMap<String, String> getRegionsHM() {
        return regionsHM;
    }
    public HashMap<String, String> getAgesHM() {
        return agesHM;
    }
    public HashMap<String, String> getSexesHM() {
        return sexesHM;
    }
    public HashMap<String, String> getSensorsHM() {
        return sensorsHM;
    }
    public HashMap<String, String> getCitiesHM() {
        return citiesHM;
    }
    public String getRegionID(String region){
        return regionsHM.get(region);
    }
    public String getCityID(String city){
        return citiesHM.get(city);
    }
    public String getSensorID(String sensor){
        return sensorsHM.get(sensor);
    }
    public String getSexID(String sex){
        return sexesHM.get(sex);
    }
    public String getAgeID(String age){
        return agesHM.get(age);
    }

    public static ThlFilterItems getInstance(){
        if(instance == null){
            instance = new ThlFilterItems();
        }return instance;
    }
    //Returns HashMap of regions including region name and SID
    private HashMap<String, String> getRegions(){
        HashMap<String, String> regions = new HashMap();
        try{
            ArrayList<ThlObjects.ThlObject.Children> regionObjects = ThlObjects.getInstance().getThlObject(0).getChildren().get(0).getChildren();
            for(ThlObjects.ThlObject.Children city : regionObjects){
                regions.put(city.getLabel(),city.getSid());
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        return regions;
    }
    //Returns HashMap of cities including city name and SID
    private HashMap<String, String> getCities(){
        HashMap<String, String> cities = new HashMap();
        try{
            ArrayList<ThlObjects.ThlObject.Children> cityObjects = ThlObjects.getInstance().getThlObject(0).getChildren().get(0).getChildren();
            for(ThlObjects.ThlObject.Children region : cityObjects){
                for(ThlObjects.ThlObject.Children city :region.getChildren()) {
                    cities.put(city.getLabel(), city.getSid());
                }
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = cities.keySet();
        ArrayList<String>citiesList = new ArrayList<>(keySet);

        return cities;
    }
    //Returns HashMap of sensors including sensor name and SID
    private HashMap<String, String> getSensors(){
        HashMap<String, String> sensors = new HashMap();
        try{
            ArrayList<ThlObjects.ThlObject.Children> sensorObjects = ThlObjects.getInstance().getThlObject(4).getChildren().get(0).getChildren();
            for(ThlObjects.ThlObject.Children city : sensorObjects){
                sensors.put(city.getLabel(),city.getSid());
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = sensors.keySet();
        ArrayList<String>sensorsList = new ArrayList<>(keySet);

        return sensors;
    }
    //Returns HashMap of ages including age label and SID
    private HashMap<String, String> getAge(){
        HashMap<String, String> ages = new HashMap();
        try{
            ArrayList<ThlObjects.ThlObject.Children> ageObjects = ThlObjects.getInstance().getThlObject(2).getChildren().get(0).getChildren();
            for(ThlObjects.ThlObject.Children age : ageObjects){
                ages.put(age.getLabel(),age.getSid());
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        Set<String> keySet = ages.keySet();
        ArrayList<String>regionsList = new ArrayList<>(keySet);

        return ages;
    }
    //Returns HashMap of sexes including sex label and SID
    private HashMap<String, String> getSexes(){
        HashMap<String, String> sexes = new HashMap();
        try{
            ArrayList<ThlObjects.ThlObject.Children> sexObjects = ThlObjects.getInstance().getThlObject(3).getChildren().get(0).getChildren();
            for(ThlObjects.ThlObject.Children city : sexObjects){
                sexes.put(city.getLabel(),city.getSid());
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        return sexes;
    }
    //Method for moving HashMap values to ArrayList
    private ArrayList<String> getList(HashMap<String, String> HM){
        Set<String> keySet = HM.keySet();
        ArrayList<String>newList = new ArrayList<>(keySet);
        Collections.sort(newList);
        newList.add(0,"Kaikki");
        return newList;
    }
    //Another method for moving HashMap values to ArrayList
    private ArrayList<String> getList2(HashMap<String, String> HM){
        Set<String> keySet = HM.keySet();
        ArrayList<String>newList = new ArrayList<>(keySet);
        Collections.sort(newList);
        return newList;
    }

}
