package com.example.harjoitustyo.Graph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.harjoitustyo.IDClass;
import com.example.harjoitustyo.R;
import com.example.harjoitustyo.THL.ThlObjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class GraphData extends Fragment {
    private static GraphData instance = null;
    private String region = "Kaikki";
    private String city = "Kaikki";
    private String age = "Kaikki";
    private String sensor = "Tapausten lukumäärä";
    private String sex = "Kaikki";
    private String dateStartStr = "01-01-2020";
    private String dateEndStr = "01-01-2020";
    private Date dateStart = new Date();
    private Date dateEnd = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private GraphThlObjects GTO = GraphThlObjects.getInstance();
    private ArrayList dayObjectList = new ArrayList<ThlObjects.ThlObject.Children>();
    private ArrayList<String> daySidsList = new ArrayList<>();
    private FragmentTransaction fragmentTransaction = null;
    ArrayList<String[]> daysList = new ArrayList<>();

    private GraphData(){
        setDateStart(dateStartStr);
        setDateEnd(dateEndStr);
    }

    public void processData(FragmentTransaction fragmentTransaction){
        this.fragmentTransaction = fragmentTransaction;
        dayObjectList = GTO.getDayObjectList(dateStart,dateEnd);
        daySidsList = GTO.getDaySIDStrList(dayObjectList);
        String daySids = "";
        for(String daySid : daySidsList){
            daySids = daySids+daySid+".";
        }
        ThlApiGraph.getInstance().fetchData(daySids, String.valueOf(IDClass.getInstance().getNewID()));
    }

    public ArrayList<String[]> getDaysList(){
        return daysList;
    }

    public void HandleData(String result){
        JSONObject jsonValues = null;
        JSONObject jsonDates = null;
        JSONObject JObject = null;
        daysList.clear();

        try {
            JObject = new JSONObject(result).getJSONObject("dataset");
            jsonValues = JObject.getJSONObject("value");
            jsonDates = JObject.getJSONObject("dimension")
                    .getJSONObject("dateweek20200101")
                    .getJSONObject("category")
                    .getJSONObject("label");

            JSONArray arrayValues = jsonValues.names();
            JSONArray arrayDates = jsonDates.names();

            for(int j=0;j<jsonValues.length();j++){
                String values = jsonValues.getString(arrayValues.get(j).toString());
                String days = jsonDates.getString(arrayDates.get(j).toString());
                daysList.add(j,new String[]{values,days});
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        fragmentTransaction.replace(R.id.fragment_container, new FragmentGraph());
        fragmentTransaction.commit();

    }

    public String getDateStart() {
        return dateStartStr;
    }

    public void setDateStart(String dateStartStr) {
        this.dateStartStr = dateStartStr;
        try {
            dateStart = formatter.parse(dateStartStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDateEnd() {
        return dateEndStr;
    }

    public void setDateEnd(String dateEndStr) {
        this.dateEndStr = dateEndStr;
        try {
            dateEnd = formatter.parse(dateEndStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static GraphData getInstance(){
        if(instance==null){
            instance = new GraphData();
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
