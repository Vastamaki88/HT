package com.example.harjoitustyo.THL;


import android.os.AsyncTask;

import com.example.harjoitustyo.ProcessData;
import com.example.harjoitustyo.StatisticsData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class ThlApi {
    private static ThlApi instance;
    private BufferedReader reader;
    Boolean isRunning = false;
    private MyTask Task;
    private ThlObjects thlObjects = ThlObjects.getInstance();

    private ThlApi(){
        reader = null;
    }

    public static ThlApi getInstance(){
        if(instance == null){
            instance = new ThlApi();
        }return instance;
    }
    public boolean isRunnig(){
        return isRunning;
    }

    //Starts the fetch process
    public void fetchData(String DaySID, String ID){
        ThlFilterItems THLItems = ThlFilterItems.getInstance();
        String sensor = StatisticsData.getInstance().getSensor();
        String age = StatisticsData.getInstance().getAge();
        String region = StatisticsData.getInstance().getRegion();
        String sex = StatisticsData.getInstance().getSex();

        if(!sex.equals("Kaikki")){
            sex = "&filter=sex-" + THLItems.getSexID(sex);
        }else{
            sex = "";
        }
        sensor = "&filter=measure-"+THLItems.getSensorID(sensor);

        if(!age.equals("Kaikki")){
                age = "&filter=ttr10yage-"+THLItems.getAgeID(age);
            }else{
            age="";
        }if(!region.equals("Kaikki")){
            region = "&filter=hcdmunicipality2020-"+THLItems.getRegionID(region);
        }else{region="";}
        isRunning = true;
        String URLString = "";
        URLString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json?row=" +
                "dateweek20200101-"+ DaySID +
                "&column=" +
                region +
                sensor+
                age+
                sex;
        System.out.println("URL TO SEARCH: "+URLString);
        Task = new MyTask();
        Task.execute(URLString,ID);
    }

    //Using asyncronous class
    private class MyTask
            extends AsyncTask<String, Void, String> {

        //This method is executed in the background asynchronously
        @Override
        protected String doInBackground(String... params) {
            String urlStr = params[0];
            String ID = params[1];
            BufferedReader reader = null;
            try {
                URL url = new URL(urlStr);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                buffer.append(ID +"#StoreTaskId#");
                while ((read = reader.read(chars)) != -1) {
                    buffer.append(chars, 0, read);
                }
                String palautus = buffer.toString();
                return palautus;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
        //When asynchronous method is finished receiving the data, this method is called
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            String[] resultArray = result.split("#StoreTaskId#");
            ProcessData.getInstance().Handle(resultArray[0],resultArray[1]);
        }
    }
}