package com.circular.circular;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.circular.circular.local.PreferenceRepository;
import com.circular.circular.local.TinyDbManager;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 5000;
    PreferenceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        repository = new PreferenceRepository();

        ((TextView)findViewById(R.id.tv_splash_title)).setTypeface(CircularApplication.mTfMainRegular);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = repository.getString("token");
                if (token.equals("Bearer ") || token.equals("nil")) {
                    startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
        }, SPLASH_TIME);
    }


}