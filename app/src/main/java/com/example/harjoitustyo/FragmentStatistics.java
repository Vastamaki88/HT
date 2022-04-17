package com.example.harjoitustyo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class FragmentStatistics extends Fragment {
    TextView textViewCases7d;
    TextView textViewCases14d;
    TextView textViewCases30d;
    TextView textViewCases100d;
    TextView textViewCasesTotal;
    TextView textViewSex;
    TextView textViewRegion;
    TextView textViewCity;
    TextView textViewAge;
    TextView textViewSensor;
    StatisticsData SD;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewCases7d = this.getView().findViewById(R.id.cases_7days);
        textViewCases14d = this.getView().findViewById(R.id.cases_14days);
        textViewCases30d = this.getView().findViewById(R.id.cases_30days);
        textViewCases100d = this.getView().findViewById(R.id.cases_100days);
        textViewCasesTotal = this.getView().findViewById(R.id.cases_total);
        //Data info
        textViewSex = this.getView().findViewById(R.id.statistics_sex2);
        textViewRegion = this.getView().findViewById(R.id.statistics_region2);
        textViewCity = this.getView().findViewById(R.id.statistics_city2);
        textViewAge = this.getView().findViewById(R.id.statistics_age2);
        textViewSensor = this.getView().findViewById(R.id.statistics_sensor2);

        SD = StatisticsData.getInstance();
        textViewSex.setText(SD.getSex());
        textViewAge.setText(SD.getAge());
        textViewRegion.setText(SD.getRegion());
        textViewCity.setText(SD.getCity());
        textViewSensor.setText(SD.getSensor());

        casesInPrevDays(7,textViewCases7d);
        casesInPrevDays(14,textViewCases14d);
        casesInPrevDays(30,textViewCases30d);
        casesInPrevDays(100,textViewCases100d);
        casesTotal(textViewCasesTotal);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }
    public void casesInPrevDays(int i, TextView textView) {
        ArrayList<ThlObjects.ThlObject.Children> ThlDayObjectList = DateClass.getInstance().getDaysFromNow(i);
        ProcessData.getInstance().GetCumulativeCasesCount(ThlDayObjectList, String.valueOf(IDClass.getInstance().getNewID()),textView);
    }
    public void casesTotal(TextView t){
        ProcessData.getInstance().getCasesTotal(t, "totalCases");
    }
    }
