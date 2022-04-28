package com.example.harjoitustyo.Graph;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.harjoitustyo.ContextClass;
import com.example.harjoitustyo.R;

//This class is called when looking for the saved data
//stored to recycler view component
public class FragmentGraphRecycler extends Fragment implements SelectListener {
    Context context;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Initializtin recycler view components
        context = ContextClass.getInstance().getContext();
        recyclerView = (RecyclerView) this.getView().findViewById(R.id.recyclerview1);
        adapter = new RecyclerViewAdapter(context,SavedGraphs.getInstance().dataSetList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recycler_view_main, container, false);
    }

    @Override
    //Item click listener for the recycler view items
    //Receives Graph object as a parameter, which is stored in each card in the recycler view
    //Changes the fragment
    public void onItemClicked(Graph graph) {
        GraphData.getInstance().setDaysList(graph.getDataSet());
        FragmentGraphRecycler.FragmentHelperClass.ChangeFragmentOpen(getActivity()
                .getSupportFragmentManager().beginTransaction()
        );
    }
    //Helper class for changing the fragment
    static class FragmentHelperClass extends Fragment{

        private static void ChangeFragmentOpen(FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.fragment_container, new SavedGraphInfo());
            fragmentTransaction.commit();
        }
    }
}
