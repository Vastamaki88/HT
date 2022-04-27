package com.example.harjoitustyo.Graph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harjoitustyo.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String[]>dataSet;

    public RecyclerViewAdapter(Context context, ArrayList<String[]>dataSet){
        this.context = context;
        this.dataSet = dataSet;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view,parent,false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.textView1.setText(dataSet.get(position)[0]);
        holder.textView2.setText(dataSet.get(position)[1]);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textview1);
            textView2 = itemView.findViewById(R.id.textview2);
        }
    }

    // convenience method for getting data at click position
    public String[] getItem(int id) {
        return dataSet.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
