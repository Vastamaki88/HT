package com.example.harjoitustyo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.harjoitustyo.THL.ThlFilterItems;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFilters#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFilters extends Fragment {
    public enum filter{age, sex, region, city, sensor}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner regionSpin;
        Spinner citySpin;
        Spinner ageSpin;
        Spinner sexSpin;
        Spinner sensorSpin;
        Button okButton;
        regionSpin = this.getView().findViewById(R.id.spinner_region);
        citySpin = this.getView().findViewById(R.id.spinner_city);
        ageSpin = this.getView().findViewById(R.id.spinner_age);
        sexSpin = this.getView().findViewById(R.id.spinner_sex);
        sensorSpin = this.getView().findViewById(R.id.spinner_sensor);
        okButton = this.getView().findViewById(R.id.button_filter);


        helperClass hc = new helperClass();
        hc.setSpinner(regionSpin, this.getView(), filter.region.ordinal());
        hc.setSpinner(citySpin, this.getView(), filter.city.ordinal());
        hc.setSpinner(ageSpin, this.getView(), filter.age.ordinal());
        hc.setSpinner(sexSpin, this.getView(), filter.sex.ordinal());
        hc.setSpinner(sensorSpin, this.getView(), filter.sensor.ordinal());

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatisticsData SD = StatisticsData.getInstance();
                SD.setAge(ageSpin.getSelectedItem().toString());
                SD.setCity(citySpin.getSelectedItem().toString());
                SD.setRegion(regionSpin.getSelectedItem().toString());
                SD.setSensor(sensorSpin.getSelectedItem().toString());
                SD.setSex(sexSpin.getSelectedItem().toString());

                FragmentFilters.HelperClass2 HC2 =new FragmentFilters.HelperClass2();
                HC2.changeFragment();
            }
        });
    }
    class HelperClass2 extends AppCompatActivity{
        public void changeFragment(){
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new FragmentStatistics());
            fragmentTransaction.commit();
        }
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFilters newInstance(String param1, String param2) {
        FragmentFilters fragment = new FragmentFilters();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters, container, false);
    }
}
class helperClass extends Activity{
    public void setSpinner(Spinner spinner, View view, int ID){
        FragmentFilters.filter filterID = FragmentFilters.filter.values()[ID];
        ArrayList<String> keys = new ArrayList<>();
        int index = 0;
        StatisticsData SD = StatisticsData.getInstance();
        switch (filterID){
            case age:
                keys = ThlFilterItems.getInstance().getAgesList();
                index = keys.indexOf(SD.getAge());
                break;
            case sensor:
                keys = ThlFilterItems.getInstance().getSensorsList();
                index = keys.indexOf(SD.getSensor());
                break;
            case sex:
                keys = ThlFilterItems.getInstance().getSexesList();
                index = keys.indexOf(SD.getSex());
                break;
            case city:
                keys = ThlFilterItems.getInstance().getCitiesList();
                index = keys.indexOf(SD.getCity());
                break;
            case region:
                keys = ThlFilterItems.getInstance().getRegionsList();
                index = keys.indexOf(SD.getRegion());
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,keys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(index);
        System.out.println(spinner.getSelectedItem());
    }
}
