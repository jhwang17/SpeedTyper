package com.example.speedtyper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    Button backBtn;
    TextView lblHowToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        backBtn = findViewById(R.id.btnBack);
        lblHowToPlay = findViewById(R.id.lblHowToPlay);

        SpannableString contentHowToPlay = new SpannableString("How to Play");

        contentHowToPlay.setSpan(new UnderlineSpan(), 0, contentHowToPlay.length(), 0);
        lblHowToPlay.setText(contentHowToPlay);

        initBackBtn();
    }

    private void initBackBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionsActivity.this, SpeedRunActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });
    }
}
