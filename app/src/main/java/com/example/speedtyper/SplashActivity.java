package com.example.speedtyper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import static java.lang.Thread.sleep;


public class SplashActivity extends AppCompatActivity {

    //Button btn;
    //LinearLayout container;
    //TextView txtDev;
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pbLoading = findViewById(R.id.progressBar);
        //btn = findViewById(R.id.btnRun);
        //container = findViewById(R.id.layoutPH);
        //txtDev = findViewById(R.id.textDevName);

        //initHome();

        initLoading();
    }

    private void initLoading() {
        Thread t = new Thread() {
            public void run() {
                try {
                    //
                    sleep(2000);

                    pbLoading.setProgress(2000);

                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }


/*    private void initHome() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }*/
}