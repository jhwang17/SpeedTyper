package com.example.speedtyper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ResultDataSource {

    private SQLiteDatabase ds;
    private DBHelper dbHelper;

    public ResultDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        ds = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertResult(Result r) {
        boolean didAdd = false;

        try {
            ContentValues initialVals = new ContentValues();

            initialVals.put("playerinitials", r.getPlayerInitials());
            initialVals.put("playerscore", r.getPlayerScore());

            didAdd = ds.insert("scoreboard", null, initialVals) > 0;
        } catch(Exception e) {

        }

        return didAdd;
    }

    public boolean updateResult(Result r) {
        boolean didUpdate = false;

        try {
            ContentValues updateVals = new ContentValues();

            updateVals.put("playerinitials", r.getPlayerInitials());
            updateVals.put("playerscore", r.getPlayerScore());

            didUpdate = ds.update("scoreboard", updateVals, "_id=" + getLowestResultId(), null) > 0;
        } catch(Exception e) {

        }

        return didUpdate;
    }

    public int getLowestResultId() {
        int id = -1;

        try {
            String query = "SELECT _id FROM scoreboard ORDER BY playerscore DESC";
            Cursor cursor = ds.rawQuery(query, null);
            cursor.moveToLast();

            id = cursor.getInt(0);

            cursor.close();
        } catch(Exception e) {
            id = -1;
        }

        return id;
    }

    public int getLastResultId() {
        int lastId = -1;

        try {
            String query = "SELECT MAX(_id) FROM scoreboard";
            Cursor cursor = ds.rawQuery(query, null);

            cursor.moveToLast();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch(Exception e) {
            lastId = -1;
        }

        return lastId;
    }

    public ArrayList<Result> getResults() {
        ArrayList<Result> results = new ArrayList<>();

        try {
            String query = "SELECT * FROM scoreboard ORDER BY playerscore DESC";
            Cursor cursor = ds.rawQuery(query, null);

            Result newResult;
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                newResult = new Result();

                newResult.setResultID(cursor.getInt(0));
                newResult.setPlayerInitials(cursor.getString(1));
                newResult.setPlayerScore(cursor.getInt(2));

                results.add(newResult);
                cursor.moveToNext();
            }

            cursor.close();
        } catch(Exception e) {
            results = new ArrayList<>();
        }

        return results;
    }

    public void deleteData() {
        try {
            String query = "DELETE FROM scoreboard";
            ds.execSQL(query);
        } catch(Exception e) {

        }
    }
}
