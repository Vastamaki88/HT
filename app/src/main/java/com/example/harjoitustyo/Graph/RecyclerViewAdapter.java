package com.example.harjoitustyo.Graph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harjoitustyo.R;

import java.util.ArrayList;
//Recycler View adaptar class for the recycler view
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Graph>dataSet;
    private SelectListener listener;

    public RecyclerViewAdapter(Context context, ArrayList<Graph>dataSet, SelectListener listener){
        this.context = context;
        this.dataSet = dataSet;
        this.listener = listener;
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
        //Text values for the CardView are set here
        holder.textView1.setText(dataSet.get(position).getFilename());
        holder.textView2.setText(dataSet.get(position).getDate());
        //Listener for the CardView to acces the CardView data
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(dataSet.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;
        CardView cardView;

        //Initializes components
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textview_recycler_1);
            textView2 = itemView.findViewById(R.id.textview_recycler_2);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    // convenience method for getting data at click position
    public Graph getItem(int id) {
        return dataSet.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
