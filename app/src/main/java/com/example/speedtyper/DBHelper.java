package com.example.speedtyper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "topscores.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE_SCOREBOARD =
            "create table scoreboard (_id integer primary key autoincrement, " +
                    "playerinitials text not null, playerscore integer not null);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SCOREBOARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " +
                newVersion + ", deleting all the current saved data.");
        db.execSQL("DROP TABLE IF EXISTS scoreboard");
    }
}
