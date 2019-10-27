package com.example.testapplication.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    private Button playButton;
    private Button exitButton;
    //Used for panorama on home screen
    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        playButton = (Button) findViewById(R.id.playButton);

        exitButton = findViewById(R.id.exit);

        //exitButton exit game
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }



    public void quizPage(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void leaderBoardPage(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void ArGame(View view) {
        Intent intent = new Intent(this, ARCoreActivity.class);
        startActivity(intent);
    }
}
