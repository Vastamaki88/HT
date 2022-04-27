package com.example.harjoitustyo;

import com.example.harjoitustyo.THL.ThlObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateClass {
    private static DateClass instance = null;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    Date date;
    Calendar calendar;


    private DateClass(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        date = new Date();
    }

    public static DateClass getInstance(){
        if(instance == null){
            instance = new DateClass();
        }return instance;
    }

    public Date getCurrentDate(){
        Date dateNow = new Date();
        dateNow.setTime(dateNow.getTime()+3*60*60*1000);//Current time at GMT+0 to GMT+
        return dateNow;
    }

    public int getNumberOfWeekFrom20200101(Date date){
        Date dateEnd = null;
        Date dateStart = null;
        try {
            dateStart = simpleDateFormat.parse("2020-01-01");
            dateEnd = simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long NumberOfWeeks = (dateEnd.getTime()-dateStart.getTime()+getDayInMS(2))/(1000*60*60*24*7)+2;

        return Integer.parseInt(String.valueOf(NumberOfWeeks));
    }

    //Returns list of full days, starting from yesterday
    public ArrayList<ThlObjects.ThlObject.Children> getDaysFromNow(int number){
        DayClass days = new DayClass();
        Date day = getCurrentDate();
        try {
            day = simpleDateFormat.parse(simpleDateFormat.format(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i = 1; i <= number; i++){
            Date dayTemp = new Date();
            dayTemp.setTime(day.getTime()-getDayInMS(i));

            String weekID = "dateweek20200101" + String.valueOf(getNumberOfWeekFrom20200101(dayTemp));
            days.insertDay(weekID,dayTemp);
        }
        return getThlObjects(days);
    }

    public HashMap<String, String> getDaySids(ArrayList<String> daysList){
        return null;
    }
    //Returns number of milliseconds in one day
    private long getDayInMS(long i){
        return i*24*60*60*1000;
    }

    private ArrayList<ThlObjects.ThlObject.Children> getThlObjects(DayClass days){

        ThlObjects.ThlObject.Children thlWeeksMain = ThlObjects.getInstance().getThlObject(1).getChildren().get(0);
        ArrayList<ThlObjects.ThlObject.Children> thlWeeks = thlWeeksMain.getChildren();

        HashMap<String, List<Date>> weekDayPair = days.getweekDayPair();

        ArrayList<ThlObjects.ThlObject.Children> palautus = new ArrayList<>();

        for(Map.Entry<String, List<Date>> entry : weekDayPair.entrySet()){
            String weekID = entry.getKey();
            List<Date> datesList = new ArrayList<>();
            datesList = entry.getValue();
            if(datesList.size()==7){
                for(ThlObjects.ThlObject.Children w : thlWeeks){
                   if(w.getID().equals(weekID)){
                       palautus.add(w);
                   }
                }
            }else {
                for (ThlObjects.ThlObject.Children w : thlWeeks) {
                    if (w.getID().equals(weekID)) {
                        for (ThlObjects.ThlObject.Children d : w.getChildren()) {
                            try {
                                Date dateTemp = simpleDateFormat.parse(d.getLabel());
                                if (datesList.contains(dateTemp)) {
                                    palautus.add(d);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return palautus;
    }
}
