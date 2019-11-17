package com.example.testapplication.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.testapplication.R;
import com.example.testapplication.database.GetScoreRequest;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        // Todo: Display a different page if there are no players to display.
        // Todo: Limit the number of players displayed to 10.

        TextView nameTextView = findViewById(R.id.nameRank);
        TextView scoreTextView = findViewById(R.id.pointRank);

        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {
                    int size = jsonResponse.getInt("size");
                    for (int i = 0; i < size; i++) {
                        nameTextView.append(String.format("%s\n", jsonResponse.getString(String.valueOf(i))));
                        scoreTextView.append(String.format("%d\n", jsonResponse.getInt(String.valueOf(i + size))));
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LeaderboardActivity.this);
                    builder.setMessage("No internet connection.")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        GetScoreRequest getScoreRequest = new GetScoreRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(LeaderboardActivity.this);
        queue.add(getScoreRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePageActivity.class);
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
