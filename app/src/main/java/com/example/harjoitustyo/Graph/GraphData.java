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
//This class contains data for drawing the Graph
public class GraphData extends Fragment {
    private static GraphData instance = null;
    //THL filters
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
    //Methods to initialize dates are in constructor
    private GraphData(){
        setDateStart(dateStartStr);
        setDateEnd(dateEndStr);
    }

    public void setDaysList(ArrayList<String[]> daysList){
        this.daysList = daysList;
    }

    //This method creates a string of date sids, which is used in HTTP-call
    //Fragment transaction is sent as parameter becouse it is needed soon to change the fragment
    public void processData(FragmentTransaction fragmentTransaction){
        this.fragmentTransaction = fragmentTransaction;
        //GraphThlObjects(GTO) class is used to receive a list of THL day-objects
        //Each day-object contains SID-number, which is needed for the HTTP-call,
        //and label, which tells the date in readable form
        dayObjectList = GTO.getDayObjectList(dateStart,dateEnd);
        //This call trasforms the object-list to string list
        daySidsList = GTO.getDaySIDStrList(dayObjectList);
        String daySids = "";
        //For loop combines all dates to one string, which is used in HTTP-call
        for(String daySid : daySidsList){
            daySids = daySids+daySid+".";
        }
        //THL API class is used to fetch data
        //SID-numbers are sent as parameter
        //Also ID-number is set as a parameter in case of asynchronous HTTP-calls
        ThlApiGraph.getInstance().fetchData(daySids, String.valueOf(IDClass.getInstance().getNewID()));
    }


    //When THL API class finishes the fetch data method, this method is called
    //Parameter result is a JSON string which includes the needed results
    public void HandleData(String result){
        JSONObject jsonValues = null;
        JSONObject jsonDates = null;
        JSONObject JObject = null;
        daysList.clear();

        try {
            //dataset object is retrieved from the result string
            JObject = new JSONObject(result).getJSONObject("dataset");
            //Then value-object which includes the results
            jsonValues = JObject.getJSONObject("value");
            //And then the label-object, which includes the date-SID pairs
            jsonDates = JObject.getJSONObject("dimension")
                    .getJSONObject("dateweek20200101")
                    .getJSONObject("category")
                    .getJSONObject("label");
            //Both of the JSONObjects are trasferred to JSONArrays
            //names method gives the names, as the JSONObjects do contain name-value pairs
            JSONArray arrayValues = jsonValues.names();
            JSONArray arrayDates = jsonDates.names();
            //Final values from jsonValues and jsonObject are received with this for loop
            //JSONArrays are used to get the values
            //The results are added to daysList ArrayList
            for(int j=0;j<jsonValues.length();j++){
                String values = jsonValues.getString(arrayValues.get(j).toString());
                String days = jsonDates.getString(arrayDates.get(j).toString());
                daysList.add(j,new String[]{values,days});
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //When data processing is ready, the fragment can be changed
        fragmentTransaction.replace(R.id.fragment_container, new FragmentGraph());
        fragmentTransaction.commit();

    }
    //The res is getters and setters
    public String getDateStart() {
        return dateStartStr;
    }
    public void setDateStart(String dateStartStr) {
        this.dateStartStr = dateStartStr;
        try { dateStart = formatter.parse(dateStartStr); } catch (ParseException e) {
            e.printStackTrace(); } }
    public String getDateEnd() {
        return dateEndStr;
    }
    public void setDateEnd(String dateEndStr) {
        this.dateEndStr = dateEndStr;
        try { dateEnd = formatter.parse(dateEndStr); } catch (ParseException e) {
            e.printStackTrace(); } }
    public static GraphData getInstance(){
        if(instance==null){
            instance = new GraphData();
        }return instance; }
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
    public ArrayList<String[]> getDaysList(){
        return daysList;
    }
}
