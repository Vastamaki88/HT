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
//This class contains list of saved datasets used for drawing graph
//Saved datasets are stored to device as JSON data. The data
//is read when the program starts, and saved data is set to objects in this class
public class SavedGraphs {
    private static SavedGraphs instance = null;
    ArrayList<Graph> dataSetList = new ArrayList<>();
    //Constructur calls the readFile method to retrieve the saved data
    private SavedGraphs() {
        readFile();
    }

    public static SavedGraphs getInstance() {
        if (instance == null) {
            instance = new SavedGraphs();
        }
        return instance;
    }
    //Adds new graph to list of graphs
    public void addItem(Graph graph) {
        dataSetList.add(graph);
    }
    //Reads the saved data from the file
    public void readFile(){
        String output="";
        try{
            InputStream inputStream = ContextClass.getInstance().getContext().openFileInput("SavedGraphs");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            while((s= bufferedReader.readLine())!=null){
                output+=s;
            }
            inputStream.close();
            //Gson library is used to form a object
            Gson gson = new Gson();
            JsonStreamParser jsonStreamParser = new JsonStreamParser(output);
            JsonObject jsonObject = new JsonObject();
            //Data is looped through
            while(jsonStreamParser.hasNext()){
                //Individual elements are formed from the data
                JsonElement element = jsonStreamParser.next();
                jsonObject = element.getAsJsonObject();
                String jsonStr = jsonObject.toString();
                //New graph object is created
                Graph graph = gson.fromJson(jsonStr,Graph.class);
                dataSetList.add(graph);
            }

        }catch (IOException e){
            Log.e("IOException","Error reading the file");
        }finally{

        }
    }
    //Is used to write graph data to file
    public void writeFile(Graph graph){
        File path = null;
        path = ContextClass.getInstance().getContext().getFilesDir();
        Gson gson = new Gson();
        String json = gson.toJson(graph);
        try{
            //Writer appends the data, so old data won't get lost
            FileOutputStream osw = new FileOutputStream(new File(path, "SavedGraphs"),true);
            osw.write(json.getBytes());
            osw.close();
        }catch (IOException e){
            Log.e("","");
        }
    }
}
//This class is used to form Graph objects
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
