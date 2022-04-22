package com.example.harjoitustyo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentChange extends FragmentActivity implements FragmentChangeListener {
        @Override
        public void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.toString());
            fragmentTransaction.addToBackStack(fragment.toString());
            fragmentTransaction.commit();
        }
    }
