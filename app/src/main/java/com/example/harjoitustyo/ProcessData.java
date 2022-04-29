package com.example.harjoitustyo;

import android.widget.TextView;

import com.example.harjoitustyo.THL.ThlApi;
import com.example.harjoitustyo.THL.ThlObjects;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
//This class is called from Statistics fragment
//Input values to this class are DayObjects, and it returns number as String
public class ProcessData {
    private static ProcessData instance = null;
    private HashMap<String, Result> resultHM;
    private Result res;

    private ProcessData(){
        resultHM = new HashMap<>();
    }

    public static ProcessData getInstance(){
        if(instance == null){
            instance = new ProcessData();
        }return instance;
    }
    //Input parameters are list of THL-day objects, ID number, and textview, which is asking for the result
    public void GetCumulativeCasesCount(ArrayList<ThlObjects.ThlObject.Children> daysLlist, String ID, TextView t) {
        //New result object is created to store values
        res = new Result(t);
        //Asukasmäärä cant be used in this case
        if(StatisticsData.getInstance().getSensor().equals("Asukaslukumäärä")){
            res.setResultToView("-");
            return;
        }
        //If hashmap of results does already contain this id, result object is set to the id
        if (resultHM.containsKey(ID)) {
            resultHM.get(ID).setResultToView();
            return;
            //Otherwise new ID is added
        } else {
            resultHM.put(ID, res);
            //DaysList is looped through in thread, and fetchData method is called for each element
            new Thread(new Runnable() {
                public void run() {
                    for (ThlObjects.ThlObject.Children c : daysLlist) {
                        ThlApi.getInstance().fetchData(c.getSid(), ID);
                    }
                }
            }).start();
        }
    }
    //Total cases gets the sum of all reported cases
    public void getCasesTotal(TextView t, String ID){
        ThlApi.getInstance().fetchData("509030",ID);
        if(!resultHM.containsKey(ID)) {
            resultHM.put(ID, new Result(t));
        }
    }
    //This method is called from API-class, when the HTTP-call results have been received
    //result strings are set to result object
    public void Handle(String ID, String resultStr){
            Result r_day = resultHM.get(ID);
            r_day.addResult(sumValues(resultStr));
            r_day.setResultToView();
    }
    //This class is used to store THL data, and for some dataprocessing
    class Result{
        int result;
        boolean ready;
        TextView t;
        public Result(TextView t){
            this.result = 0;
            this.ready = false;
            this.t = t;
        }
        public void addResult(int i){
            result+= i;
        }
        //Result is set to specific TextView component
        public void setResultToView(){
            this.t.setText(String.valueOf(result));
        }
        //Same method with different parameters
        public void setResultToView(String res){
            this.t.setText(res);
        }
    }
    //This method processes the JSON data and calculates value of specific numbers
    public int sumValues(String data) {
        int palautus = 0;
        String value = null;
        String pvm = null;
        try{
            //"dataset" and "value" objects are searched from data
            JSONObject JObject = new JSONObject(data).getJSONObject("dataset").getJSONObject("value");
            //Array is fromed
            JSONArray JArr = JObject.names();
            //Last element of array contains the needed value
            String valueTemp = JArr.getString(JArr.length()-1);
            value = JObject.getString(valueTemp);
            //Dots are cleaned
            value = value.replace(".","");

        }catch (Exception e){
            System.out.println("Arvoa ei löytynyt: vihe :"+e);
        }
        if(value != null){
            try{
                palautus = Integer.parseInt(value);
            }catch (Exception e){
                System.out.println("Error: "+ e);
                palautus = 0;
            }
        }
           return palautus;
    }
}
