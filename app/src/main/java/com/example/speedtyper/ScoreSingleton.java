package com.example.speedtyper;

public class ScoreSingleton {
    private static ScoreSingleton _instance;
    private static int score;

    public static ScoreSingleton getInstance() {
        if(_instance == null) {
            _instance = new ScoreSingleton();
        }

        return _instance;
    }

    private ScoreSingleton() {
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
