package com.example.harjoitustyo.Graph;

import com.example.harjoitustyo.THL.ThlObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphThlObjects {
    private static GraphThlObjects instance = null;
    private ThlObjects.ThlObject.Children dayObject;
    SimpleDateFormat simpleDateFormat;

    private GraphThlObjects(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static GraphThlObjects getInstance(){
        if(instance==null){
            instance = new GraphThlObjects();
        }return instance;
    }

    public ArrayList<ThlObjects.ThlObject.Children> getDayObjectList(Date start, Date end){
        ArrayList<ThlObjects.ThlObject.Children> daysListTemp = new ArrayList<>();
        ArrayList<ThlObjects.ThlObject.Children> daysList = new ArrayList<>();
        ArrayList<ThlObjects.ThlObject.Children> thlWeeks = ThlObjects.getInstance().getThlObject(1).getChildren().get(0).getChildren();

        for(ThlObjects.ThlObject.Children week: thlWeeks){
            daysListTemp = week.getChildren();
            for(ThlObjects.ThlObject.Children day : daysListTemp){
                String dayStr = day.getLabel();
                try {
                    Date dayDate = simpleDateFormat.parse(dayStr);
                    if(dayDate.after(end)){
                        continue;
                    }if(dayDate.before(start)){
                        continue;
                    }daysList.add(day);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return daysList;
    }
    public ArrayList<String> getDaySIDStrList(ArrayList<ThlObjects.ThlObject.Children> dayObjects){
        ArrayList<String>daySids = new ArrayList<>();
        for(ThlObjects.ThlObject.Children dObject : dayObjects){
            daySids.add(dObject.getSid());
        }
        return daySids;
    }
}
