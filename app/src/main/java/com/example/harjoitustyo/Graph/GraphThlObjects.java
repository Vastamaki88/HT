package com.example.harjoitustyo.Graph;

import com.example.harjoitustyo.THL.ThlObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// This class used to retrieve list of THL day-objects, and list for day Strings
//input values are start and end days
public class GraphThlObjects {
    private static GraphThlObjects instance = null;
    SimpleDateFormat simpleDateFormat;

    private GraphThlObjects(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static GraphThlObjects getInstance(){
        if(instance==null){
            instance = new GraphThlObjects();
        }return instance;
    }
    //This method is given as parameters the starting day and end day of a time span
    //Return value is a list of THL objects
    public ArrayList<ThlObjects.ThlObject.Children> getDayObjectList(Date start, Date end){
        //Temporary list to be used in loop
        ArrayList<ThlObjects.ThlObject.Children> daysListTemp = new ArrayList<>();
        //List for return values is initialized
        ArrayList<ThlObjects.ThlObject.Children> daysList = new ArrayList<>();
        //thlWeeks object is retrieved from the THLObjects class
        ArrayList<ThlObjects.ThlObject.Children> thlWeeks = ThlObjects.getInstance().getThlObject(1).getChildren().get(0).getChildren();

        //Weeks are loopet through
        for(ThlObjects.ThlObject.Children week: thlWeeks){
            daysListTemp = week.getChildren();
            //Days are looped through
            for(ThlObjects.ThlObject.Children day : daysListTemp){
                String dayStr = day.getLabel();
                try {
                    //new date is formatted from String value
                    Date dayDate = simpleDateFormat.parse(dayStr);
                    if(dayDate.after(end)){
                        continue;
                    }if(dayDate.before(start)){
                        continue;
                        //if the new day is between the start and end days, it is added to lis
                    }daysList.add(day);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return daysList;
    }
    //This method retrieves the value from previous method and returns list of Strings to be used in API call
    public ArrayList<String> getDaySIDStrList(ArrayList<ThlObjects.ThlObject.Children> dayObjects){
        ArrayList<String>daySids = new ArrayList<>();
        for(ThlObjects.ThlObject.Children dObject : dayObjects){
            daySids.add(dObject.getSid());
        }
        return daySids;
    }
}
