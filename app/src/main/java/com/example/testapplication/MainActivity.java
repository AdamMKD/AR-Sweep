package com.example.testapplication;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

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

        backgroundOne = (ImageView) findViewById(R.id.background1);
        backgroundTwo = (ImageView) findViewById(R.id.background2);


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

        animate(animator);
    }

    private void animate(ValueAnimator animator) {
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator()); //what is this
        animator.setDuration(10000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });

        animator.start();
    }


    public void quizPage(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void ArGame(View view) {
        Intent intent = new Intent(this, ARCoreTest.class);
        startActivity(intent);
    }
}
