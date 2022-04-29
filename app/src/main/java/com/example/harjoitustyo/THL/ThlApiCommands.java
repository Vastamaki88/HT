package com.example.harjoitustyo.THL;


import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

//This class gets all available commands for THL statistics
//Main objects are Location, Datetime, Age, Sex, and Sensors
//The process of fetching data is executed asynchronously

public class ThlApiCommands {
    BufferedReader reader;
    String URLString;
    MyTask Task;
    ThlObjects thlObjects = ThlObjects.getInstance();

    public ThlApiCommands(){
        reader = null;
        URLString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.dimensions.json";
        Task = new MyTask();
    }
    //Starts the fetch process
    public void fetchData(){
        Task.execute(URLString);
    }
    //Using asyncronous class
    private class MyTask
            extends AsyncTask<String, Void, String> {

        //This method is executed in the background asynchronously
        @Override
        protected String doInBackground(String... params) {
            String urlStr = params[0];
            BufferedReader reader = null;
            try {
                URL url = new URL(urlStr);

                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1) {
                    buffer.append(chars, 0, read);
                }
                return buffer.toString();
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
            handleResult(result);
        }
    }
    //The JSON data is processed and set to ThlObjects
    private void handleResult(String result){
        //Gson library is used to hadle the JSON data
        Gson gson = new Gson();
        JsonStreamParser p = new JsonStreamParser(result);

            try{
                //Looping through Json Elements
                while(p.hasNext()) {
                    JsonElement e = p.next();
                    //If element is Json array, go through each element
                    if(e.isJsonArray()){
                        JsonArray JsonJono = e.getAsJsonArray();
                        for(JsonElement j : JsonJono){
                            if(j.isJsonObject()){
                                //New ThlObject is created here, and put into ThlObject array
                                String objStr = j.toString();
                                ThlObjects.ThlObject thlObject = gson.fromJson(objStr,ThlObjects.ThlObject.class);
                                thlObjects.insertThlObject(thlObject);
                                if(thlObjects.objectsCount()==5){
                                    //System.out.println("INITIALIZED");
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){

            }
    }
}