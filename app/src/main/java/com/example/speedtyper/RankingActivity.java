package com.example.speedtyper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    ArrayList<Result> results;
    ResultAdapter resultAdapter;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        backBtn = findViewById(R.id.btnBack);

        initBackBtn();

        ResultDataSource ds = new ResultDataSource(this);

        try {
            ds.open();
            results = ds.getResults();
            ds.close();

            if(results.size() > 0) {
                ListView lv = findViewById(R.id.lvResults);
                resultAdapter = new ResultAdapter(this, results);
                lv.setAdapter(resultAdapter);
            } else {

            }

        } catch(Exception e) {
            Toast.makeText(this, "There are no scores saved", Toast.LENGTH_LONG).show();
        }
    }

    private void initBackBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });
    }
}
