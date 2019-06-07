package com.example.speedtyper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Formatter;

public class ResultActivity extends AppCompatActivity {

    private Result currentResult;
    ArrayList<Result> savedResults;
    ScoreSingleton scoreSingleton;

    Button homeBtn, tryAgainBtn, rankingBtn;
    TextView txtScore, txtWPS;
    TextView txtComment;
    EditText etPlayerInitial;
    RelativeLayout layoutNewTop;

    int score;
    boolean isNewHighScore, initialEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreSingleton = ScoreSingleton.getInstance();
        currentResult = new Result();

        score = scoreSingleton.getScore();
        currentResult.setPlayerScore(scoreSingleton.getScore());

        txtScore = findViewById(R.id.textScore);
        txtWPS = findViewById(R.id.textWPS);
        txtComment = findViewById(R.id.textComment);
        etPlayerInitial = findViewById(R.id.etName);

        homeBtn = findViewById(R.id.btnHome);
        tryAgainBtn = findViewById(R.id.btnTryAgain);
        rankingBtn = findViewById(R.id.btnRanking);

        layoutNewTop = findViewById(R.id.layoutNewTop);

        layoutHandler();

        initHomeBtn();
        initRankingBtn();
        initTryAgainBtn();
    }

    private void initHomeBtn() {
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);

                if(isNewHighScore) {
                    if(initialEntered) {
                        showSavedAlert(intent);
                    } else {
                        showUnSavedAlert(intent);
                    }
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initTryAgainBtn() {
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, SpeedRunActivity.class);

                if(isNewHighScore) {
                    if(initialEntered) {
                        showSavedAlert(intent);
                    } else {
                        showUnSavedAlert(intent);
                    }
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initRankingBtn() {
        rankingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, RankingActivity.class);

                if(isNewHighScore) {
                    if(initialEntered) {
                        showSavedAlert(intent);
                    } else {
                        showUnSavedAlert(intent);
                    }
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void layoutHandler() {
        setScore();
        setWPS();

        ArrayList<Result> topScoreList;
        ResultDataSource ds = new ResultDataSource(this);

        try {
            ds.open();
            topScoreList = ds.getResults();
            ds.close();

            isNewHighScore = newHighScoreCheck(topScoreList);

            if(isNewHighScore) {
                txtComment.setText("New High Score!");
                layoutNewTop.setVisibility(View.VISIBLE);
                validateInitials();
            } else {
                txtComment.setText("Not a new high score");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean newHighScoreCheck(ArrayList<Result> topScores) {
        if(topScores.size() < 10) {
            return true;
        } else {
            for (Result r : topScores) {
                if(currentResult.getPlayerScore() > r.getPlayerScore()) {
                    return true;
                }
            }
        }

        return false;
    }

    private void submitScore() {
        ResultDataSource ds = new ResultDataSource(ResultActivity.this);

        try {
            ds.open();

            savedResults = ds.getResults();

            if(savedResults.size() < 10) {
                int newId = ds.getLastResultId() + 1;
                currentResult.setResultID(newId);
                ds.insertResult(currentResult);

            } else {
                ds.updateResult(currentResult);
            }

            ds.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void validateInitials() {
        etPlayerInitial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().trim().isEmpty()) {
                    currentResult.setPlayerInitials(s.toString());
                    initialEntered = true;
                } else {
                    initialEntered = false;
                }
            }
        });
    }

    private void setScore() {
        txtScore.setText("" + score);
    }

    private void setWPS() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        float temp = (float) scoreSingleton.getScore();
        float eq = (float) (temp / 600) * 100;
        String wps = formatter.format("%.2f", eq).toString();

        txtWPS.setText(wps + " wps");
    }

    private void showSavedAlert(final Intent i) {
        new AlertDialog.Builder(ResultActivity.this)
                .setTitle("High Score Saved!")
                .setMessage("Good job! Check out the new Top 10 Ranking!")
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitScore();

                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_popup_reminder)
                .show();
    }

    private void showUnSavedAlert(final Intent i) {
        new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage("Going back or leaving initials blank will result to current game's score not being saved. Leave?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etPlayerInitial.requestFocus();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
