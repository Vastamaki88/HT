package com.example.harjoitustyo;

import com.example.harjoitustyo.THL.ThlObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//This class stores Week and DayList pairs in hashmap
public class DayClass {
    private HashMap<String, List<Date>> weekDayPair;

    public DayClass(){
        weekDayPair = new HashMap<>();
    }
    //Insert method for inserting week-dayList pairs
    public void insertDay(String weekNo, Date day){
        if(weekDayPair.containsKey(weekNo)){
            weekDayPair.get(weekNo).add(day);
        }else{
            ArrayList<Date> tempList = new ArrayList<Date>();
            weekDayPair.put(weekNo, tempList);
            weekDayPair.get(weekNo).add(day);
        }
    }
    public HashMap<String, List<Date>> getweekDayPair(){
        return weekDayPair;
    }
}
