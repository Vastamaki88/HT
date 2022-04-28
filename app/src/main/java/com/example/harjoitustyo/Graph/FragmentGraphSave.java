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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//This fragment is shown when user wants to save graph data
public class FragmentGraphSave extends Fragment {
    TextView FilenameText;
    Button backButton;
    Button saveButton;
    FragmentHelperClass helper;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FilenameText = this.getView().findViewById(R.id.graph_filename_text);
        backButton = this.getView().findViewById(R.id.savegraphBtn_back);
        saveButton = this.getView().findViewById(R.id.savegraphBtn_save);

        //Back button listener, which changes the fragment
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            helper = new FragmentHelperClass();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
            helper.ChangeFragment(fragmentTransaction);

            }
        });
        //Save button listener, which saves graph data for the recycler view
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String[]> values = GraphData.getInstance().getDaysList();
                //Current date is set to data. It will be in description field of recycler view cards
                //Time is left out from the date
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dateStr = formatter.format(date);
                //New graph object is created
                Graph graph = new Graph(values,dateStr,FilenameText.getText().toString());
                //Graph data is written in JSON format to the device
                SavedGraphs.getInstance().writeFile(graph);
                //Graph data is set to list, which contains recycler view items
                SavedGraphs.getInstance().addItem(graph);
            }
        });
    }
    public FragmentGraphSave() {
        // Required empty public constructor
    }

    public static FragmentGraphSave newInstance(String param1, String param2) {
        FragmentGraphSave fragment = new FragmentGraphSave();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.save_graph_view, container, false);
    }
    //Helper class to change fragment
    class FragmentHelperClass extends Fragment{
        public void ChangeFragment(FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.fragment_container, new FragmentGraph());
            fragmentTransaction.commit();
        }
    }
}
