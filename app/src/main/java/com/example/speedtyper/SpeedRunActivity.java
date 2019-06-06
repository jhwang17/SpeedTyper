package com.example.speedtyper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SpeedRunActivity extends AppCompatActivity {

    ScoreSingleton scoreSingleton;
    WordBank wordBank;

    TextView txtTime;
    EditText etInput;
    TextView lblWord, txtWord;
    TextView lblScore, txtScore;
    Button backBtn;
    ImageButton ibHelp;
    ImageButton ibSR;

    int score;
    int difficulty;
    boolean inGame, inputMatched;
    boolean isInterrupted;

    private long MINUTE = (long) 10000.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_run);

        scoreSingleton = ScoreSingleton.getInstance();
        wordBank = new WordBank();

        txtTime = findViewById(R.id.textTime);
        etInput = findViewById(R.id.editInput);
        lblWord = findViewById(R.id.lblWord);
        txtWord = findViewById(R.id.textWord);
        lblScore = findViewById(R.id.lblScore);
        txtScore = findViewById(R.id.textScore);
        backBtn = findViewById(R.id.btnBack);
        ibHelp = findViewById(R.id.ibHelp);
        ibSR = findViewById(R.id.ibSR);

        inGame = false;
        isInterrupted = false;

        initHelpBtn();
        initBackBtn();
        initStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String difficultyLevel = getSharedPreferences("SpeedTyperPreferences", Context.MODE_PRIVATE)
                .getString("difficultylevel", "easy");

        if(difficultyLevel.equalsIgnoreCase("easy")) {
            difficulty = 0;
        } else if(difficultyLevel.equalsIgnoreCase("medium")) {
            difficulty = 1;
        } else {
            difficulty = 2;
        }
    }

    @Override
    public void onRestart() {
        Intent intent = new Intent(SpeedRunActivity.this, SelectModeActivity.class);

        if(inGame) {
            showStoppedAlert(intent);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SpeedRunActivity.this, SelectModeActivity.class);

        if(inGame) {
            showStoppedAlert(intent);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void initHelpBtn() {

        ibHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initBackBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpeedRunActivity.this, SelectModeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initStart() {

        ibSR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v.isPressed()) {
                    score = 0;
                    wordHandler();
                    lblWord.setVisibility(View.VISIBLE);
                    lblWord.setFreezesText(true);
                    ibSR.setEnabled(false);
                    ibSR.setVisibility(View.INVISIBLE);
                    backBtn.setEnabled(false);
                    backBtn.setVisibility(View.INVISIBLE);
                    ibHelp.setEnabled(false);
                    ibHelp.setVisibility(View.INVISIBLE);

                    etInput.setEnabled(true);
                    etInput.requestFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etInput, InputMethodManager.SHOW_IMPLICIT);

                    initEditText();
                    startTimer();
                    inGame = true;
                    inputMatched = false;
                }
                return false;
            }
        });
    }

    private CountDownTimer startTimer() {

        CountDownTimer timer = new CountDownTimer(MINUTE, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                MINUTE = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                inGame = false;
                etInput.setEnabled(false);

                if(isInterrupted) {
                    cancel();
                } else {
                    endGame();
                }
            }

            public void endGame() {
                scoreSingleton.setScore(score);

                Intent intent = new Intent(SpeedRunActivity.this, ResultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }.start();

        return timer;
    }

    private void updateTimer() {
        int minutes = (int) MINUTE / 60000;
        int seconds = (int) MINUTE % 60000 / 1000;
        String time;

        time = "0" + minutes;

        if(seconds < 10) {
            time += ":0";
            txtTime.setTextColor(Color.RED);
        } else {
            time += ":";
        }

        time += seconds;
        txtTime.setText(time);
    }

    private void initEditText() {
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputMatched = validateInput(s, txtWord);

                if(inputMatched) {
                    score++;
                    txtScore.setText("" + score);
                    wordHandler();
                    inputMatched = false;
                    s.clear();
                }
            }
        });
    }

    private boolean validateInput(Editable e, TextView tv) {

        String inputted = e.toString().trim();
        String word = tv.getText().toString().trim();

        if(inputted.equalsIgnoreCase(word)) {
            return true;
        } else {
            return false;
        }
    }

    private void wordHandler() {
        String[] bank = generateBank(difficulty);
        String s = generateWord(bank);
        setWord(s);
    }

    private void setWord(String s) {
        txtWord.setText(s);
    }

    private String[] generateBank(int lvl) {
        //int ranNum = (int) ((Math.random() * 3));

        if(lvl == 0) {
            return wordBank.getEasyBank();
        } else if(lvl == 1) {
            return wordBank.getMediumBank();
        } else {
            return wordBank.getHardBank();
        }
    }

    private String generateWord(String[] s) {
        int ranNum = (int) ((Math.random() * s.length));
        String word = s[ranNum];

        return word;
    }

    private void showStoppedAlert(final Intent intent) {
        new AlertDialog.Builder(SpeedRunActivity.this)
                .setTitle("Game Interrupted!")
                .setMessage("The game was interrupted and terminated. Score was not saved.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isInterrupted = true;

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
