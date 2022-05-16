package com.example.senfacture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> intitules;
    ArrayList<String> entreprises;
    ArrayList<String> numeros;
    ArrayList<String> dates;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> intitules, ArrayList<String> entreprises, ArrayList<String> numeros,ArrayList<String> dates) {
        this.context = context;
        this.intitules = intitules;
        this.entreprises = entreprises;
        this.numeros = numeros;
        this.dates = dates;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_impayees, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // set the data in items
        holder.intitule.setText(intitules.get(position));
        holder.entreprise.setText(entreprises.get(position));
        holder.numero.setText(numeros.get(position));
        holder.date.setText(dates.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, intitules.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return intitules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView intitule, entreprise, numero, date;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            intitule = (TextView) itemView.findViewById(R.id.intitule);
            entreprise = (TextView) itemView.findViewById(R.id.entreprise);
            numero = (TextView) itemView.findViewById(R.id.numero);
            date = (TextView) itemView.findViewById(R.id.date);

        }
    }
}