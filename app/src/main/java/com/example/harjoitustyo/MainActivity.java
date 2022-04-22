package com.example.harjoitustyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        ThlApiCommands thlApiCmnds = new ThlApiCommands();
        if(!ThlObjects.getInstance().isInitialized()){
            thlApiCmnds.fetchData();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentSettings()).commit();
                break;
            case R.id.statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentStatistics()).commit();
                ArrayList<String>testilista = new ArrayList<>();
                testilista = ThlObjects.getInstance().getCities();
                break;
            case R.id.create_graph:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentSettings()).commit();
                break;
            case R.id.main:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMain()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}