package com.example.rossoneri.loto.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rossoneri.loto.R;
import com.example.rossoneri.loto.entities.Draw;

import java.util.List;

public class DrawListAdapter extends ArrayAdapter<Draw> {

    private Context context;
    private List<Draw> draws;

    public DrawListAdapter(Context context, List<Draw> draws) {
        super(context, R.layout.draw_list_layout, draws);
        this.context = context;
        this.draws = draws;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.draw_list_layout, parent, false);

        TextView textViewDrawName = (TextView) view.findViewById(R.id.drawName);
        TextView textViewDrawCounter = (TextView) view.findViewById(R.id.drawCounter);

        textViewDrawName.setText(draws.get(position).getLottery() + " Тираж №" + String.valueOf(draws.get(position).getDraw_number()));
        textViewDrawCounter.setText(String.valueOf(draws.get(position).getSold_tickets_count()) + "/" + String.valueOf(draws.get(position).getSeller_tickets_count()));

        return view;
    }
}
