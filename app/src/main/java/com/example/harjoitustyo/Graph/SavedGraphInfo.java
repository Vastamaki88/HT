package com.example.harjoitustyo.Graph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.harjoitustyo.R;


public class SavedGraphInfo extends Fragment {
    Button backButton;
    Button openButton;
    FragmentHelperClass FHG;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = this.getView().findViewById(R.id.SavedGraphInfo_back);
        openButton = this.getView().findViewById(R.id.SavedGraphInfo_open);
        GraphData GD = GraphData.getInstance();

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FHG = new SavedGraphInfo.FragmentHelperClass();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                FHG.ChangeFragmentOpen(fragmentTransaction);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FHG = new SavedGraphInfo.FragmentHelperClass();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                FHG.ChangeFragmentBack(fragmentTransaction);
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
        return inflater.inflate(R.layout.fragment_saved_graph_info, container, false);
    }
    class FragmentHelperClass extends Fragment{

        public void ChangeFragmentBack(FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.fragment_container, new FragmentGraphRecycler());
            fragmentTransaction.commit();
        }
        public void ChangeFragmentOpen(FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.fragment_container, new FragmentGraph());
            fragmentTransaction.commit();
        }
    }
}