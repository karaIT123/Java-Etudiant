package com.example.etudiant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListArrayAdapter extends ArrayAdapter<list> {
    private Context context;
    private ArrayList<list> data;
    private int resource;

    public ListArrayAdapter(@NonNull Context context, int resource, @NonNull List<list> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.data = new ArrayList<list>();
        this.data = (ArrayList<list>)objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        list dataTemp = this.data.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        convertView = layoutInflater.inflate(this.resource,parent,false);

        TextView nom = (TextView)convertView.findViewById(R.id.datanom);
        TextView datafirst = (TextView)convertView.findViewById(R.id.datafirst);
        TextView datasecond = (TextView)convertView.findViewById(R.id.datasecond);
        TextView datafird = (TextView)convertView.findViewById(R.id.datafird);

        nom.setText(dataTemp.getName());
        datafirst.setText(dataTemp.getFirst());
        datasecond.setText(dataTemp.getSecond());
        datafird.setText(dataTemp.getFird());

        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
