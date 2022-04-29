package com.example.harjoitustyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import com.example.harjoitustyo.Graph.FragmentGraph;
import com.example.harjoitustyo.Graph.FragmentGraphMain;
import com.example.harjoitustyo.Graph.FragmentGraphRecycler;
import com.example.harjoitustyo.Graph.FragmentGraphSave;
import com.example.harjoitustyo.Graph.SavedGraphs;
import com.example.harjoitustyo.THL.FilterEnum;
import com.example.harjoitustyo.THL.ThlApiCommands;
import com.example.harjoitustyo.THL.ThlObjects;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
//The main class of program
//Main objective of this class is to manage the functioning of navigation drawer
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Context is stored to ContextClass for future usage
        ContextClass.getInstance().setContext(this);
        SavedGraphs savedGraphs = SavedGraphs.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentMain()).commit();
            navigationView.setCheckedItem(R.id.main);
        }
        //THL API Commands are initialized
        ThlApiCommands thlApiCmnds = new ThlApiCommands();
        if(!ThlObjects.getInstance().isInitialized()){
            thlApiCmnds.fetchData();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(SettingsClass.getInstance().changed){
            changeLanguageClicked();
        }
        switch (item.getItemId()){
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentSettings()).commit();
                break;
            case R.id.statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentStatistics()).commit();
                break;
            case R.id.create_graph:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentGraphMain()).commit();
                break;
            case R.id.main:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMain()).commit();
                break;
            case R.id.saedGraphs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentGraphRecycler()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //This method is used to change language
    public void changeLanguageClicked() {
        SettingsClass.getInstance().changed = false;
        Locale myLocale = new Locale(SettingsClass.getInstance().getLanguage());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}