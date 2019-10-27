package com.example.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    public static String USERNAME;
    public static boolean pass;
    public static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView infoView = findViewById(R.id.gameOverText);
        if (pass) {
            infoView.setText("Congrats for completed the lists, your final score is " + score);
        } else {
            infoView.setText("Unfortunately you have not completed the list, your final score is  " + score);
        }

        TextView enterNameView = findViewById(R.id.enterNameView);
        enterNameView.setText("Please Enter you're name to be added to the leader board:");

        EditText editText = findViewById(R.id.enterNameBox);
        USERNAME = editText.getText().toString();
    }

    public void highscorePage(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void homePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
