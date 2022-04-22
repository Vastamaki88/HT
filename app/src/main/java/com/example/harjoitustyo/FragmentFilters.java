package com.example.harjoitustyo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
        regionSpin = this.getView().findViewById(R.id.spinner_region);
        citySpin = this.getView().findViewById(R.id.spinner_city);
        ageSpin = this.getView().findViewById(R.id.spinner_age);
        sexSpin = this.getView().findViewById(R.id.spinner_sex);
        sensorSpin = this.getView().findViewById(R.id.spinner_sensor);


        helperClass hc = new helperClass();
        hc.setSpinner(regionSpin, this.getView(), filter.region.ordinal());
        hc.setSpinner(citySpin, this.getView(), filter.city.ordinal());
        hc.setSpinner(ageSpin, this.getView(), filter.age.ordinal());
        hc.setSpinner(sexSpin, this.getView(), filter.sex.ordinal());
        hc.setSpinner(sensorSpin, this.getView(), filter.sensor.ordinal());
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
        switch (filterID){
            case age:
                keys = ThlObjects.getInstance().getAge();
                break;
            case sensor:
                keys = ThlObjects.getInstance().getSensors();
                break;
            case sex:
                keys = ThlObjects.getInstance().getSexes();
                break;
            case city:
                keys = ThlObjects.getInstance().getCities();
                break;
            case region:
                keys = ThlObjects.getInstance().getRegions();
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,keys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
