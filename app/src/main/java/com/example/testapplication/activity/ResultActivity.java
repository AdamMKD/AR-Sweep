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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView infoView = findViewById(R.id.gameOverText);
        infoView.setText("Your final score is " + ARCoreActivity.TOTALSCORE);

        TextView enterNameView = findViewById(R.id.enterNameView);
        enterNameView.setText("Please Enter you're name to be added to the leader board:");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void highscorePage(View view) {
        EditText editText = findViewById(R.id.enterNameBox);
        USERNAME = editText.getText().toString();
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }

    public void homePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
