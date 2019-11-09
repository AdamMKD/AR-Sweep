package com.example.testapplication.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exitButton = findViewById(R.id.exit);
        //exitButton exit game
        exitButton.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    public void quizPage(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        finish();
    }

    public void leaderBoardPage(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}