package com.example.rossoneri.loto.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rossoneri.loto.R;
import com.example.rossoneri.loto.entities.DrawTicket;

import java.util.List;

public class DrawTicketListAdapter extends ArrayAdapter<DrawTicket> {

    private Context context;
    private List<DrawTicket> drawTickets;

    public DrawTicketListAdapter(Context context, List<DrawTicket> drawTickets) {
        super(context, R.layout.layout_draw_ticket, drawTickets);
        this.context = context;
        this.drawTickets = drawTickets;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_draw_ticket, parent, false);

        TextView textViewTicketNumber = (TextView) view.findViewById(R.id.ticketNumber);
        TextView textViewTicketSoldDate = (TextView) view.findViewById(R.id.ticketSoldDate);

        textViewTicketNumber.setText("Билет №" + drawTickets.get(position).getTicket_number());
        textViewTicketSoldDate.setText(drawTickets.get(position).getSold_at());

        return view;
    }
}
