package com.example.harjoitustyo;

public class StatisticsData {
    private static StatisticsData instance = null;
    private String region;
    private String city;
    private String age;
    private String sensor;
    private String sex;

    private  StatisticsData(){
        region = "all";
        city = "all";
        age = "all";
        sensor ="all";
        sex = "all";
    }

    public static StatisticsData getInstance(){
        if(instance==null){
            instance = new StatisticsData();
        }return instance;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
