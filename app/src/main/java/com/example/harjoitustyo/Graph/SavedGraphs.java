package com.example.harjoitustyo.Graph;

import android.util.Log;
import android.widget.Toast;

import com.example.harjoitustyo.ContextClass;
import com.example.harjoitustyo.THL.ThlObjects;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SavedGraphs {
    private static SavedGraphs instance = null;
    ArrayList<Graph> dataSetList = new ArrayList<>();

    private SavedGraphs() {
        readFile();
    }

    public static SavedGraphs getInstance() {
        if (instance == null) {
            instance = new SavedGraphs();
        }
        return instance;
    }
    public void getGraphListStr(){
        ArrayList<String>listC = new ArrayList<>();
        for(Graph g : dataSetList){
            listC.add(g.getFilename());
        }
    }

    public void addItem(Graph graph) {
        dataSetList.add(graph);
    }
    public void readFile(){
        String output="";
        File path = ContextClass.getInstance().getContext().getFilesDir();
        String pathStr = path.toString()+"/SavedGraphs";
        try{
            InputStream inputStream = ContextClass.getInstance().getContext().openFileInput("SavedGraphs");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            while((s= bufferedReader.readLine())!=null){
                output+=s;
            }
            Gson gson = new Gson();
            inputStream.close();
            JsonStreamParser jsonStreamParser = new JsonStreamParser(output);
            JsonObject jsonObject = new JsonObject();
            while(jsonStreamParser.hasNext()){
                JsonElement element = jsonStreamParser.next();
                jsonObject = element.getAsJsonObject();
                String jsonStr = jsonObject.toString();
                Graph graph = gson.fromJson(jsonStr,Graph.class);
                dataSetList.add(graph);
            }

        }catch (IOException e){
            Log.e("IOException","Virhe tiedoston tallennuksessa");
        }finally{

        }
    }
    public void writeFile(Graph graph){
        File path = null;
        path = ContextClass.getInstance().getContext().getFilesDir();
        String output="";
        Gson gson = new Gson();
        String json = gson.toJson(graph);
        try{
            FileOutputStream osw = new FileOutputStream(new File(path, "SavedGraphs"),true);
            osw.write(json.getBytes());
            osw.close();
        }catch (IOException e){
            Log.e("","");
        }
    }
}
class Graph{
    private ArrayList<String[]>dataSet;
    private String dateStr;
    private String filename;
    public Graph(ArrayList<String[]> daysList,String dateStr,String filename){
        this.dataSet =daysList;
        this.dateStr = dateStr;
        this.filename = filename;
    }
    public String getFilename(){
        return filename;
    }
    public String getDate(){return dateStr;}
    public ArrayList<String[]>getDataSet(){
     return this.dataSet;
    }
}
