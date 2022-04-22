package com.example.harjoitustyo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button filterButton;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewCases7d = this.getView().findViewById(R.id.cases_7days);
        textViewCases14d = this.getView().findViewById(R.id.cases_14days);
        textViewCases30d = this.getView().findViewById(R.id.cases_30days);
        textViewCases100d = this.getView().findViewById(R.id.cases_100days);
        textViewCasesTotal = this.getView().findViewById(R.id.cases_total);
        filterButton = this.getView().findViewById(R.id.buttonFilter);
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

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new FragmentFilters());
                fragmentTransaction.commit();
                //FragmentChange FG = new FragmentChange();
                //Fragment newFrag = new FragmentFilters();
                //FG.replaceFragment(newFrag);
            }
        });

    }
    class HelperClass extends AppCompatActivity{
        public void onBtnClick(View view){
            Fragment newFrag = new FragmentFilters();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,newFrag)
                    .addToBackStack(newFrag.toString())
                    .setTransition(FragmentTransaction
                            .TRANSIT_FRAGMENT_OPEN)
                    .commit();

        }
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
