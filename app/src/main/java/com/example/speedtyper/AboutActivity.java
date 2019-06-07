package com.example.speedtyper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    Button backBtn;
    TextView lblInformation, lblCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backBtn = findViewById(R.id.btnAboutBack);
        lblInformation = findViewById(R.id.lblInformation);
        lblCredits = findViewById(R.id.lblCredits);

        SpannableString contentInfo = new SpannableString("Information");
        SpannableString contentCred = new SpannableString("Credits");

        contentInfo.setSpan(new UnderlineSpan(), 0, contentInfo.length(), 0);
        lblInformation.setText(contentInfo);

        contentCred.setSpan(new UnderlineSpan(), 0, contentCred.length(), 0);
        lblCredits.setText(contentCred);

        initBackBtn();
    }

    private void initBackBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });
    }
}
