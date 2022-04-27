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

public class ProcessData {
    private static ProcessData instance = null;
    private HashMap<String, result> resultHM;
    private result r;

    private ProcessData(){
        resultHM = new HashMap<>();
    }

    public static ProcessData getInstance(){
        if(instance == null){
            instance = new ProcessData();
        }return instance;
    }
    public void GetCumulativeCasesCount(ArrayList<ThlObjects.ThlObject.Children> list, String ID, TextView t) {
        r = new result(t);
        if(StatisticsData.getInstance().getSensor().equals("Asukaslukumäärä")){
            r.setResultToView("-");
            return;
        }
        if (resultHM.containsKey(ID)) {
            resultHM.get(ID).setResultToView();
            return;
        } else {
            resultHM.put(ID, r);
            new Thread(new Runnable() {
                public void run() {
                    for (ThlObjects.ThlObject.Children c : list) {
                        ThlApi.getInstance().fetchData(c.getSid(), ID);
                    }
                }
            }).start();
        }
    }
    public void getCasesTotal(TextView t, String ID){
        ThlApi.getInstance().fetchData("509030",ID);
        if(!resultHM.containsKey(ID)) {
            resultHM.put(ID, new result(t));
        }
    }

    public void Handle(String ID, String resultStr){
            result r_day = resultHM.get(ID);
            r_day.addResult(sumValues(resultStr));
            r_day.setResultToView();
    }

    class result{
        int result;
        boolean ready;
        TextView t;
        public result(TextView t){
            this.result = 0;
            this.ready = false;
            this.t = t;
        }
        public void addResult(int i){
            result+= i;
        }
        public boolean isReady(){
            return ready;
        }
        public int getResult(){
            return result;
        }
        public void setResultToView(){
            this.t.setText(String.valueOf(result));
        }
        public void setToReady(){
            this.ready = true;
        }
        public void setResultToView(String res){
            this.t.setText(res);
        }
    }

    public int sumValues(String data) {
        int palautus = 0;
        JsonObject valueJson = null;
        String value = null;
        String pvm = null;
        try{
            JSONObject JObject = new JSONObject(data).getJSONObject("dataset").getJSONObject("value");
            JSONArray JArr = JObject.names();
            String value2 = JArr.getString(JArr.length()-1);
            value = JObject.getString(value2);

        }catch (Exception e){
            System.out.println("Arvoa ei löytynyt: vihe :"+e);
        }
        try {
            pvm = new Gson().fromJson(data, JsonObject.class)
                    .getAsJsonObject().get("dataset")
                    .getAsJsonObject().get("dimension")
                    .getAsJsonObject().get("dateweek20200101")
                    .getAsJsonObject().get("category")
                    .getAsJsonObject().get("label").toString().replace("\"", "");

        }catch (Exception e){
            System.out.println("Arvoa ei löytynyt: vihe :"+e);
        }

        if(value != null){
            palautus = Integer.parseInt(value);
        }
        if(palautus == 0){
            System.out.println("palautus 0 ------" + pvm );
        }
           return palautus;
    }
}
