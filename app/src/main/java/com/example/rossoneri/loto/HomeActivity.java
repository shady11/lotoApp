package com.example.rossoneri.loto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rossoneri.loto.entities.DrawResponse;
import com.example.rossoneri.loto.network.ApiService;
import com.example.rossoneri.loto.network.RetrofitBuilder;

import butterknife.ButterKnife;
import retrofit2.Call;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    ApiService service;
    TokenManager tokenManager;
    Call<DrawResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        Log.w(TAG, "onCreate: " + tokenManager.getToken());

        if(tokenManager.getToken() == null){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }

        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null){
            call.cancel();
            call = null;
        }
    }
}