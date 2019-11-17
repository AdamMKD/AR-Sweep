package com.example.testapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.testapplication.UserScore;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AR_Sweep";
    private static final String TABLE_NAME = "UserScore";
    private static final String NAME = "name";
    private static final String SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("ON CREATE", "OnCreate: The on create is now called.");
        String CREATE_TABLE = String.format("CREATE TABLE %s(%s TEXT, %s INTEGER)", TABLE_NAME, NAME, SCORE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("ON UPGRADE", "onUpgrade: The on upgrade is now called.");
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public void addUser(UserScore userScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, userScore.getName());
        values.put(SCORE, userScore.getScore());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public UserScore getUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{NAME, SCORE}, NAME + "=?",
                new String[]{name}, null, null, null, null);

        UserScore userScore = null;
        if (cursor != null) {
            cursor.moveToFirst();
            userScore = new UserScore(cursor.getString(0), Integer.parseInt(cursor.getString(1)));
            cursor.close();
        }

        db.close();
        return userScore;
    }

    public List<UserScore> geAllUsers() {
        List<UserScore> userScoreList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                userScoreList.add(new UserScore(cursor.getString(0),
                        Integer.parseInt(cursor.getString(1))));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userScoreList;
    }

    public int updateUser(UserScore userScore) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, userScore.getName());
        values.put(SCORE, userScore.getScore());

        int i = db.update(TABLE_NAME, values, NAME + "=?", new String[]{userScore.getName()});
        db.close();
        return i;
    }

    public int getNumberOfUsers() {
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
//        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int userCount = cursor.getCount();
        cursor.close();
        db.close();

        return userCount;
    }
}
