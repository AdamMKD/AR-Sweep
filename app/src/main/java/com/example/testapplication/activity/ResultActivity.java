package com.example.testapplication.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.testapplication.R;
import com.example.testapplication.database.AddScoreRequest;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.testapplication.activity.QuizActivity.SCORE;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView infoView = findViewById(R.id.gameOverText);
        infoView.setText("Your final score is " + QuizActivity.SCORE);

        TextView enterNameView = findViewById(R.id.enterNameView);
        enterNameView.setText("Please Enter you're name to be added to the leader board:");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void homePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void highscorePage(View view) {
        EditText editText = findViewById(R.id.enterNameBox);
        String name = editText.getText().toString();
        QuizActivity.SCORE = QuizActivity.RANDOM.nextInt(100) + 1;

        // Response received from the server
        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {
                    Intent intent = new Intent(ResultActivity.this, LeaderboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                    builder.setMessage("No internet connection.")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        AddScoreRequest addScoreRequest = new AddScoreRequest(name, SCORE, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ResultActivity.this);
        queue.add(addScoreRequest);
    }
}