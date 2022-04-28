package com.example.harjoitustyo.Graph;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.harjoitustyo.FragmentFilters;
import com.example.harjoitustyo.FragmentStatistics;
import com.example.harjoitustyo.R;
import com.example.harjoitustyo.StatisticsData;
import com.example.harjoitustyo.THL.ThlFilterItems;

import java.util.ArrayList;
import java.util.Calendar;


public class FragmentGraphMain extends Fragment {
    Button setStartDate;
    Button setEndDate;
    Button viewGraph;
    Button openGraphs;
    Spinner regionSpin;
    Spinner citySpin;
    Spinner ageSpin;
    Spinner sexSpin;
    Spinner sensorSpin;
    TextView textDateStart;
    TextView textDateEnd;

    public FragmentGraphMain() {
        // Required empty public constructor
    }
    public TextView getTextDateStart(){
        return textDateStart;
    }
    public TextView getTextDateEnd(){
        return textDateEnd;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GraphData GD = GraphData.getInstance();
        setStartDate = this.getView().findViewById(R.id.graph_set_start);
        setEndDate = this.getView().findViewById(R.id.graph_set_end);
        viewGraph = this.getView().findViewById(R.id.button_view_graph);
        openGraphs = this.getView().findViewById(R.id.createGraph_open);
        HelperClass HC = new FragmentGraphMain.HelperClass(this.getView());


        regionSpin = this.getView().findViewById(R.id.graph_spinner_region);
        citySpin = this.getView().findViewById(R.id.graph_spinner_city);
        ageSpin = this.getView().findViewById(R.id.graph_spinner_age);
        sexSpin = this.getView().findViewById(R.id.graph_spinner_sex);
        sensorSpin = this.getView().findViewById(R.id.graph_spinner_sensor);
        textDateStart = this.getView().findViewById(R.id.graph_date_start_text);
        textDateEnd = this.getView().findViewById(R.id.graph_date_end_text);

        textDateEnd.setText(GD.getDateEnd());
        textDateStart.setText(GD.getDateStart());

        FragmentGraphMain.helperClass1 hc = new helperClass1();
        hc.setSpinner(regionSpin, this.getView(), FragmentFilters.filter.region.ordinal());
        hc.setSpinner(citySpin, this.getView(), FragmentFilters.filter.city.ordinal());
        hc.setSpinner(ageSpin, this.getView(), FragmentFilters.filter.age.ordinal());
        hc.setSpinner(sexSpin, this.getView(), FragmentFilters.filter.sex.ordinal());
        hc.setSpinner(sensorSpin, this.getView(), FragmentFilters.filter.sensor.ordinal());

        openGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HC.changeFragmentOpen(getActivity().getSupportFragmentManager().beginTransaction());
            }
        });

        regionSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                citySpin.setSelection(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                regionSpin.setSelection(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        viewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GD.setAge(ageSpin.getSelectedItem().toString());
                GD.setCity(citySpin.getSelectedItem().toString());
                GD.setRegion(regionSpin.getSelectedItem().toString());
                GD.setSensor(sensorSpin.getSelectedItem().toString());
                GD.setSex(sexSpin.getSelectedItem().toString());

                HC.changeFragment();
            }
        });

        View.OnClickListener dateBtnListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HC.showDatePickerDialog(1);

            }
        };
        View.OnClickListener dateBtnListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HC.showDatePickerDialog(2);
            }
        };
        setStartDate.setOnClickListener(dateBtnListener1);
        setEndDate.setOnClickListener(dateBtnListener2);
    }
    class HelperClass extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
        View v;
        int BtnID = 0;
        public HelperClass(View v){
            this.v = v;
        }
        public void changeFragment(){
            GraphData.getInstance().processData(getActivity()
                    .getSupportFragmentManager().beginTransaction());
        }
        public void changeFragmentOpen(FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.fragment_container, new FragmentGraph());
            fragmentTransaction.commit();
        }

        public void showDatePickerDialog(int id){
            BtnID = id;
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    v.getContext(),
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
            String monthStr = String.valueOf(month+1);
            String dayStr = String.valueOf(dayOfMonth);
            if(month<10){
                monthStr = 0+monthStr;
            }if(dayOfMonth<10){
                dayStr = +0+dayStr;
            }
            String date = dayStr +"-" + monthStr +"-" + year;
            if(BtnID==1){
                TextView t = getTextDateStart();
                t.setText(date);
                GraphData.getInstance().setDateStart(date);
            }else{
                TextView t = getTextDateEnd();
                t.setText(date);
                GraphData.getInstance().setDateEnd(date);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_main, container, false);
    }
    class helperClass1 extends Activity {
        public void setSpinner(Spinner spinner, View view, int ID){
            FragmentFilters.filter filterID = FragmentFilters.filter.values()[ID];
            ArrayList<String> keys = new ArrayList<>();
            int index = 0;
            StatisticsData SD = StatisticsData.getInstance();
            switch (filterID){
                case age:
                    keys = ThlFilterItems.getInstance().getAgesList();
                    index = keys.indexOf(SD.getAge());
                    break;
                case sensor:
                    keys = ThlFilterItems.getInstance().getSensorsList();
                    index = keys.indexOf(SD.getSensor());
                    break;
                case sex:
                    keys = ThlFilterItems.getInstance().getSexesList();
                    index = keys.indexOf(SD.getSex());
                    break;
                case city:
                    keys = ThlFilterItems.getInstance().getCitiesList();
                    index = keys.indexOf(SD.getCity());
                    break;
                case region:
                    keys = ThlFilterItems.getInstance().getRegionsList();
                    index = keys.indexOf(SD.getRegion());
                    break;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,keys);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(index);
            System.out.println(spinner.getSelectedItem());
        }
    }
}

