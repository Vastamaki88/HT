package com.example.harjoitustyo;


import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

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
        isRunning = true;
        String URLString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json?row=" +
                "hcdmunicipality2020-445222" +
                "&column=" +
                "dateweek20200101-" +DaySID+
                "&filter=" +
                "measure-444833";
        //System.out.println("URL TO SEARCH: "+URLString);
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
    //The JSON data is processed and set to ThlObjects
  /*  private void handleResult(String ID, String result){
        //Gson library is used to hadle the JSON data
        Gson gson = new Gson();
        JsonStreamParser p = new JsonStreamParser(result);

     //   JsonObject jobj = (JsonObject) new Gson().fromJson(result, JsonObject.class)
      //          .getAsJsonObject().get("dataset")
      //          .getAsJsonObject().get("value");


     //   String test2 = jobj.get("dataset").toString();

            try{
                //Looping through Json Elements

                while(p.hasNext()) {
                    JsonElement e = p.next();
                    System.out.println("asd");
                            if(e.isJsonObject()){
                                //New ThlObject is created here, and put into ThlObject array
                                String objStr = e.toString();
                                ThlResultObjects.dataset thlResultObject = gson.fromJson(objStr, ThlResultObjects.dataset.class);
                                ThlResultObjects.getInstance().insertObject(thlResultObject);

                            }
                        }
            }catch (Exception e){

            }
    }
    public String getWeekSid(String weekNo){

        return null;
    }*/
}