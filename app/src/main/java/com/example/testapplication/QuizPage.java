package com.example.testapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import androidx.appcompat.app.AppCompatActivity;

public class QuizPage extends AppCompatActivity {

    private static int score = 0;
    private QuestionManager questionManager;
    private int counter = 60;
    private TextView textView;
    private ArrayList<Integer> colourCheck = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    private Button highlightedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textView = findViewById(R.id.score);
        textView.setText("Score is " + score);
        // The timer for the challenge.
        final TextView counttime = findViewById(R.id.countdown);
        new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText("Timer left is " + counter);
                counter--;
            }

            @Override
            public void onFinish() {
                counttime.setText("Finished");
                // This should take you to a new page.
                cancel();
            }
        }.start();

        setQuestion();
        // Part of the back button functionality.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setQuestion() {
        TextView question = findViewById(R.id.question);
        Button option1 = findViewById(R.id.button1);
        option1.setBackgroundColor(Color.LTGRAY);
        Button option2 = findViewById(R.id.button2);
        option2.setBackgroundColor(Color.LTGRAY);
        Button option3 = findViewById(R.id.button3);
        option3.setBackgroundColor(Color.LTGRAY);
        Button option4 = findViewById(R.id.button4);
        option4.setBackgroundColor(Color.LTGRAY);

        questionManager = new QuestionManager();
        question.setText(questionManager.getQuestion());
        option1.setText(questionManager.pickOption());
        option2.setText(questionManager.pickOption());
        option3.setText(questionManager.pickOption());
        option4.setText(questionManager.pickOption());
    }

    private String result(Button button) {
        String buttonAnswer = button.getText().toString();
        AtomicReference<String> result = new AtomicReference<>("Wrong Answer.");
        questionManager.getDisplayOptions().forEach(s -> {
            if (buttonAnswer.equals(s.split(";")[1])
                    && s.split(";")[0].equals("true")) {
                score = score + 15;
                textView.setText("Score is : " + score);
                result.set("Correct!!");
            }
        });
        return result.get();
    }

    public void changeBackground(Button button, int colourState) {
        if (colourCheck.get(colourState) % 2 == 0) {
            button.setBackgroundColor(Color.argb(106, 185, 111, 1));
            colourCheck.set(colourState, 1);
            highlightedButton = button;
        } else {
            button.setBackgroundColor(Color.LTGRAY);
            colourCheck.set(colourState, 0);
            highlightedButton = null;
        }
    }

    private void resetOtherButtons(Button button, int colourState) {
        button.setBackgroundColor(Color.LTGRAY);
        colourCheck.set(colourState, 0);
    }

    public void pickButton(View view) {
        switch (view.getId()) {
            case R.id.button1:
                changeBackground(findViewById(R.id.button1), 0);
                resetOtherButtons(findViewById(R.id.button2), 1);
                resetOtherButtons(findViewById(R.id.button3), 2);
                resetOtherButtons(findViewById(R.id.button4), 3);
                break;
            case R.id.button2:
                resetOtherButtons(findViewById(R.id.button1), 0);
                changeBackground(findViewById(R.id.button2), 1);
                resetOtherButtons(findViewById(R.id.button3), 2);
                resetOtherButtons(findViewById(R.id.button4), 3);
                break;
            case R.id.button3:
                resetOtherButtons(findViewById(R.id.button1), 0);
                resetOtherButtons(findViewById(R.id.button2), 1);
                changeBackground(findViewById(R.id.button3), 2);
                resetOtherButtons(findViewById(R.id.button4), 3);
                break;
            case R.id.button4:
                resetOtherButtons(findViewById(R.id.button1), 0);
                resetOtherButtons(findViewById(R.id.button2), 1);
                resetOtherButtons(findViewById(R.id.button3), 2);
                changeBackground(findViewById(R.id.button4), 3);
                break;
        }
    }

    public void checkAnswer(View view) {
        if (highlightedButton == null) {
            Toast.makeText(this, "Please select an answer first", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, result(highlightedButton), Toast.LENGTH_SHORT).show();

//            switch (view.getId()) {
//                case R.id.button1:
//                    Toast.makeText(this, result(findViewById(R.id.button1)), Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.button2:
//                    Toast.makeText(this, result(findViewById(R.id.button2)), Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.button3:
//                    Toast.makeText(this, result(findViewById(R.id.button3)), Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.button4:
//                    Toast.makeText(this, result(findViewById(R.id.button4)), Toast.LENGTH_SHORT).show();
//                    break;
//            }
            for (int i = 0; i < colourCheck.size(); i++) {
                colourCheck.set(i, 0);
            }
            setQuestion();
            highlightedButton = null;
        }
    }
}
