package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ThlApi thlApi = new ThlApi();
        thlApi.fetchData();
        System.out.println("testing");
        //ThlObjects3 thl = gson.fromJson(testi,ThlObjects3.class);

    }


}