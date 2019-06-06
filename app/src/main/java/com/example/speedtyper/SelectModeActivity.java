package com.example.speedtyper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectModeActivity extends AppCompatActivity {

    Button speedRunBtn;
    Button speedRunProBtn;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        speedRunBtn = findViewById(R.id.btnSpeedRun);
        speedRunProBtn = findViewById(R.id.btnSpeedRunPro);
        backBtn = findViewById(R.id.btnBack);

        initSpeedRunBtn();
        initSpeedRunProBtn();
        initBackBtn();
    }

    private void initSpeedRunBtn() {

        speedRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectModeActivity.this, SpeedRunActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSpeedRunProBtn() {

        speedRunProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initBackBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectModeActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}
