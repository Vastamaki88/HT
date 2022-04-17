package com.example.harjoitustyo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFilters#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFilters extends Fragment {
    Spinner regionSpin;
    Spinner citySpin;
    Spinner ageSpin;
    Spinner sexSpin;
    Spinner sensorSpin;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        regionSpin = this.getView().findViewById(R.id.spinner_region);
        citySpin = this.getView().findViewById(R.id.spinner_city);
        ageSpin = this.getView().findViewById(R.id.spinner_age);
        sexSpin = this.getView().findViewById(R.id.spinner_sex);
        sensorSpin = this.getView().findViewById(R.id.spinner_sensor);
    }

    public void setSpinner(){
        //ArrayList<String> regions =
        //return spinner;
    }

    public FragmentFilters() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFilters newInstance(String param1, String param2) {
        FragmentFilters fragment = new FragmentFilters();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filters, container, false);
    }
}