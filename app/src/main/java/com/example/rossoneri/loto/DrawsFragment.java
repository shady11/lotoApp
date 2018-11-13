package com.example.rossoneri.loto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rossoneri.loto.adapters.DrawListAdapter;
import com.example.rossoneri.loto.entities.Draw;
import com.example.rossoneri.loto.entities.DrawResponse;
import com.example.rossoneri.loto.network.ApiService;
import com.example.rossoneri.loto.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class DrawsFragment extends Fragment {

    private static final String TAG = "DrawsFragment";

    private ListView listView;
    private List<Draw> draws;

    ApiService service;
    TokenManager tokenManager;
    Call<DrawResponse> call;
    SharedPreferences preferences;

    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_draws, container, false);
        intent = new Intent(view.getContext(), LoginActivity.class);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", getContext().MODE_PRIVATE);

        ButterKnife.bind(this, view);
        tokenManager = TokenManager.getInstance(preferences);

        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        draws = new ArrayList<Draw>();

        call = service.draws();
        call.enqueue(new Callback<DrawResponse>() {
            @Override
            public void onResponse(Call<DrawResponse> call, Response<DrawResponse> response) {
                Log.w(TAG, "onResponse: " + response );

                if(response.isSuccessful()){
                    draws = response.body().getData();
                    listView = (ListView) getActivity().findViewById(R.id.drawsList);
                    listView.setAdapter(new DrawListAdapter(getActivity(), draws));

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            Draw draw = draws.get(position);

                            DrawFragment drawFragment = new DrawFragment();

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("draw", draw);

                            drawFragment.setArguments(bundle);
                            switchFragment(drawFragment);
                        }
                    });

                    listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            Log.d(TAG, "itemSelect: position = " + position + ", id = "
                                    + id);
                        }

                        public void onNothingSelected(AdapterView<?> parent) {
                            Log.d(TAG, "itemSelect: nothing");
                        }
                    });

                }else {
                    tokenManager.deleteToken();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<DrawResponse> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage() );
            }
        });

//        draws.add(new Draw(1, 3, "Ала Тоо", 100, 20));
//        draws.add(new Draw(2, 3, "Ала Тоо", 100, 10));


        return view;
    }

    public void switchFragment(Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, f);
        transaction.addToBackStack(null).commit();
    }
}