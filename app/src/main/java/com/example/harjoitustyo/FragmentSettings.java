package com.example.harjoitustyo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;

//This class contains functionality for changing the language
public class FragmentSettings extends Fragment {
    Spinner spinner;


    public static FragmentSettings newInstance(String param1, String param2) {
        FragmentSettings fragment = new FragmentSettings();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Spinner for choosing the language is initialized
        spinner = this.getView().findViewById(R.id.spinner_languages);
        ArrayList<String> kielet = new ArrayList<>();
        Context context = ContextClass.getInstance().getContext();
        kielet.add(getString(R.string.languages_fin));
        kielet.add(getString(R.string.languages_en));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,kielet);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);
        if(SettingsClass.getInstance().getLanguage().equals("Finnish")
                ||SettingsClass.getInstance().getLanguage().equals("Suomi")){
            spinner.setSelection(0);
        }
        //Listener for the spinner to perform the language change
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Resources res = context.getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                String selection = adapterView.getItemAtPosition(i).toString();
                SettingsClass.getInstance().changed=true;

                if(selection.equals("Finnish")||selection.equals("Suomi")) {
                    SettingsClass.getInstance().setLanguage("fin");
                    conf.setLocale(new Locale("fin"));
                    res.updateConfiguration(conf, dm);
                    System.out.println("Finnish selected");
                }else if(selection.equals("English")||selection.equals("Englanti")) {
                    conf.setLocale(new Locale("en"));
                    SettingsClass.getInstance().setLanguage("en");
                    res.updateConfiguration(conf, dm);
                    System.out.println("English selected");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}