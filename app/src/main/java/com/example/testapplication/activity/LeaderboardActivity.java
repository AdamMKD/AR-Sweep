package com.example.testapplication.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.testapplication.DatabaseHelper;
import com.example.testapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        DatabaseHelper db = new DatabaseHelper(this);

        TextView nameTextView = findViewById(R.id.nameRank);
        TextView scoreTextView = findViewById(R.id.pointRank);

        db.geAllUsers().forEach(user -> {
            nameTextView.append(String.format("%s\n", user.getName()));
            scoreTextView.append(String.format("%d\n", user.getScore()));
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Delete the database to start fresh
     */
    private boolean deleteDatabaseAndCheck(String databaseName) {
        deleteDatabase(databaseName);
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(databaseName, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            Log.e("Database", "Database named " + databaseName + " does not exist.");
        }
        return checkDB != null;
    }
}
