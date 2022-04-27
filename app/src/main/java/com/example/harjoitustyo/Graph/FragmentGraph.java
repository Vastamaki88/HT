package com.example.harjoitustyo.Graph;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.harjoitustyo.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FragmentGraph extends Fragment {
    GraphData GD;
    Button backButton;
    Button saveButton;
    FragmentHelperClass FHG;
    public FragmentGraph() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = this.getView().findViewById(R.id.graph_back);
        saveButton = this.getView().findViewById(R.id.graph_save);
        GD = GraphData.getInstance();
        HelperClass HC = new HelperClass(this.getView());
        LineChart lineChart = HC.getLineChart();
        System.out.println("On View Created!!!");
        SavedGraphs savedGraphs = SavedGraphs.getInstance();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FHG = new FragmentHelperClass();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                FHG.ChangeFragmentBack(fragmentTransaction);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FHG = new FragmentHelperClass();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                FHG.ChangeFragmentSave(fragmentTransaction);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph, container, false);

    }
    class HelperClass extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener{
        private static final String TAG = "fragment_graph";
        private LineChart mChart;

        public HelperClass(View view){
            mChart = (LineChart) view.findViewById(R.id.lineChart);
            mChart.setOnChartGestureListener(FragmentGraph.HelperClass.this);
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(false);
            mChart.setDrawGridBackground(true);
            ArrayList<Entry> yValues = new ArrayList<>();
            ArrayList<String[]> values = GraphData.getInstance().getDaysList();
            int index = 0;
            for(String[] days : values){
                yValues.add(new Entry(index,Float.valueOf(days[0])));
                index++;
            }

            LineDataSet set1 = new LineDataSet(yValues, GraphData.getInstance().getSensor());
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            data.setDrawValues(false);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.removeAllLimitLines();
            leftAxis.enableGridDashedLine(10f,10f,0);
            leftAxis.setDrawLimitLinesBehindData(true);
            leftAxis.setTextSize(15);

            set1.setFillAlpha(110);
            set1.setColor(Color.RED);
            set1.setLineWidth(3f);

            mChart.getAxisRight().setEnabled(false);
            mChart.setData(data);

            String[] values2 = new String[values.size()];
            for(int k = 0; k<values2.length;k++){
                String[] tempStr = values.get(k);
                values2[k]=tempStr[1];
            }
            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new MyAxisValueFormatter(values2));
            xAxis.setGranularity(1);
            xAxis.setLabelRotationAngle(15f);
            xAxis.setTextSize(10);
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        }

        public LineChart getLineChart(){
            return mChart;
        }


        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartLongPressed(MotionEvent me) {

        }

        @Override
        public void onChartDoubleTapped(MotionEvent me) {

        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {

        }

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

        }

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

        }

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {

        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {

        }

        @Override
        public void onNothingSelected() {

        }
    }
    public class MyAxisValueFormatter extends ValueFormatter{
        private String[] mValues;

        public MyAxisValueFormatter(String[] values){
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value) {
            try{
                return mValues[(int)value];

            }catch(IndexOutOfBoundsException e){
                System.out.println("Exception: "+e);
            }return "virhe";
        }
    }
}
class FragmentHelperClass extends Fragment{

    public void ChangeFragmentBack(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(R.id.fragment_container, new FragmentGraphMain());
        fragmentTransaction.commit();
    }
    public void ChangeFragmentSave(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(R.id.fragment_container, new FragmentGraphSave());
        fragmentTransaction.commit();
    }
}
