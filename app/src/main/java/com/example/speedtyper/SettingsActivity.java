package com.example.speedtyper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    Button backBtn;
    Button clearDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backBtn = findViewById(R.id.btnSettingsBack);
        clearDataBtn = findViewById(R.id.btnClearData);

        initBackBtn();
        initClearDataBtn();
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });
    }

    private void initClearDataBtn() {
        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initClearDataAlert();
            }
        });
    }

    private void initClearDataAlert() {
        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle("Delete Saved Data?")
                .setMessage("All saved scores will be deleted. Continue?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSavedData();
                    }
                })
                .setNeutralButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteSavedData() {
        ArrayList<Result> savedData;
        ResultDataSource ds = new ResultDataSource(this);

        try {
            ds.open();
            ds.deleteData();
            savedData = ds.getResults();
            ds.close();

            if(savedData.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Current data deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Could not delete current data", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
