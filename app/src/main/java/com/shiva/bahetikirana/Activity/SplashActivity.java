package com.shiva.bahetikirana.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shiva.bahetikirana.R;

public class SplashActivity extends AppCompatActivity {
    private TextView txtVersionCode;

    private Context context = this;
    private ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUi();
        initSplash();
    }

    private void initUi() {

    }

    private void initSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doSplash();
            }
        }, 3000);
    }

    private void doSplash() {
        Intent intentMain = new Intent(this, BaseActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentMain);
        finish();
    }


}
