package com.example.rossoneri.loto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rossoneri.loto.adapters.DrawListAdapter;
import com.example.rossoneri.loto.adapters.DrawTicketListAdapter;
import com.example.rossoneri.loto.entities.Draw;
import com.example.rossoneri.loto.entities.DrawResponse;
import com.example.rossoneri.loto.entities.DrawTicket;
import com.example.rossoneri.loto.entities.DrawTicketResponse;
import com.example.rossoneri.loto.network.ApiService;
import com.example.rossoneri.loto.network.RetrofitBuilder;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawFragment extends Fragment {

    private static final String TAG = "DrawFragment";

    private ListView listView;
    private List<DrawTicket> drawTickets;

    TextView textViewDrawName;
    TextView textViewDrawCounter;

    Draw draw;

    ApiService service;
    TokenManager tokenManager;
    Call<DrawTicketResponse> call;
    SharedPreferences preferences;

    private Intent intent;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton fabType, fabScan;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.draw_item_layout, container, false);
        intent = new Intent(view.getContext(), LoginActivity.class);

        draw = (Draw) getArguments().getSerializable("draw");

        Log.w(TAG, "onCreateView:2 " + draw.getLottery());

        textViewDrawName = (TextView) view.findViewById(R.id.drawName);
        textViewDrawCounter = (TextView) view.findViewById(R.id.drawCounter);

        textViewDrawName.setText(draw.getLottery() + " Тираж №" + String.valueOf(draw.getDraw_number()));
        textViewDrawCounter.setText(String.valueOf(draw.getSold_tickets_count()) + "/" + String.valueOf(draw.getSeller_tickets_count()));

        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", getContext().MODE_PRIVATE);

        ButterKnife.bind(this, view);
        tokenManager = TokenManager.getInstance(preferences);

        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
        drawTickets = new ArrayList<DrawTicket>();

        call = service.drawTickets(draw.getId());
        call.enqueue(new Callback<DrawTicketResponse>() {
            @Override
            public void onResponse(Call<DrawTicketResponse> call, Response<DrawTicketResponse> response) {
                Log.w(TAG, "onResponse: " + response );

                if(response.isSuccessful()){

                    drawTickets = response.body().getData();
                    listView = (ListView) getActivity().findViewById(R.id.drawTicketsList);
                    listView.setAdapter(new DrawTicketListAdapter(getActivity(), drawTickets));

                } else {
                    tokenManager.deleteToken();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<DrawTicketResponse> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage() );
            }
        });

        materialDesignFAM = (FloatingActionMenu) view.findViewById(R.id.faMenu);
        fabType = (FloatingActionButton) view.findViewById(R.id.fabType);
        fabScan = (FloatingActionButton) view.findViewById(R.id.fabScan);

        fabType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Type", Toast.LENGTH_SHORT).show();

            }
        });
        fabScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Scan", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
