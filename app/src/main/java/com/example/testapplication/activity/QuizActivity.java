package com.example.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.QuestionManager;
import com.example.testapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    public static int SCORE = 0;
    private QuestionManager questionManager;
    private int counter = 60;
    private TextView textView;
    private ArrayList<Integer> colourCheck = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    private Button highlightedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        SCORE = 0;
        // This is the SCORE based on the questions that the user had
        textView = findViewById(R.id.gameOverText);
        textView.setText("Score is " + SCORE);
        // The timer for the challenge.
        final TextView countTime = findViewById(R.id.countdown);
        new CountDownTimer(counter * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeLeft = "00:" + (counter < 10 ? "0" + counter : counter);
                countTime.setText(timeLeft);
                counter--;
            }

            @Override
            public void onFinish() {
                cancel();
                timeDisplayPage(findViewById(android.R.id.content));
            }
        }.start();

        setQuestion();
    }

    // This is to disable the back button so the user can't leave mid way during the quiz
    @Override
    public void onBackPressed() {
    }

    public void timeDisplayPage(View view) {
        Intent intent = new Intent(this, TimeDisplayActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Highlight/unhighlight the picked button and change the colour flag accordingly.
     * Unhighlight all the others buttons and reset their colour flag to zero.
     *
     * @param view the view that the button needs for onClick.
     */
    public void pickButton(View view) {
        switch (view.getId()) {
            case R.id.option1:
                changeBackground(findViewById(R.id.option1), 0);
                resetOtherButtons(findViewById(R.id.option2), 1);
                resetOtherButtons(findViewById(R.id.option3), 2);
                resetOtherButtons(findViewById(R.id.option4), 3);
                break;
            case R.id.option2:
                resetOtherButtons(findViewById(R.id.option1), 0);
                changeBackground(findViewById(R.id.option2), 1);
                resetOtherButtons(findViewById(R.id.option3), 2);
                resetOtherButtons(findViewById(R.id.option4), 3);
                break;
            case R.id.option3:
                resetOtherButtons(findViewById(R.id.option1), 0);
                resetOtherButtons(findViewById(R.id.option2), 1);
                changeBackground(findViewById(R.id.option3), 2);
                resetOtherButtons(findViewById(R.id.option4), 3);
                break;
            case R.id.option4:
                resetOtherButtons(findViewById(R.id.option1), 0);
                resetOtherButtons(findViewById(R.id.option2), 1);
                resetOtherButtons(findViewById(R.id.option3), 2);
                changeBackground(findViewById(R.id.option4), 3);
                break;
        }
    }

    /**
     * Checks the button the user clicked and see if it's correct and assign the SCORE accordingly.
     *
     * @param view the view that the onClick needs.
     */
    public void checkAnswer(View view) {
        // Check if the user has clicked on a button
        if (highlightedButton == null) {
            Toast.makeText(this, "Please select an answer first", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, result(highlightedButton), Toast.LENGTH_SHORT).show();

            // Set all the colours flag back to 0 so the colour is default
            for (int i = 0; i < colourCheck.size(); i++) {
                colourCheck.set(i, 0);
            }
            findViewById(R.id.confirm).setBackground(getResources().getDrawable(R.drawable.next_page_confirm_f));

            resetOtherButtons(highlightedButton, 0);
            // Set the new questions
            setQuestion();
            // Reset the picked button
            highlightedButton = null;
        }
    }

    private void setQuestion() {
        TextView question = findViewById(R.id.question);
        Button option1 = findViewById(R.id.option1);
        Button option2 = findViewById(R.id.option2);
        Button option3 = findViewById(R.id.option3);
        Button option4 = findViewById(R.id.option4);

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
                SCORE = SCORE + 15;
                textView.setText("Score is : " + SCORE);
                result.set("Correct!!");
            }
        });
        return result.get();
    }

    private void changeBackground(Button button, int colourState) {
        if (colourCheck.get(colourState) % 2 == 0) {
            button.setBackground(getResources().getDrawable(R.drawable.roundedbutton2));
            findViewById(R.id.confirm).setBackground(getResources().getDrawable(R.drawable.next_page_confirm_t));
            colourCheck.set(colourState, 1);
            highlightedButton = button;
        } else {
            button.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
            findViewById(R.id.confirm).setBackground(getResources().getDrawable(R.drawable.next_page_confirm_f));
            colourCheck.set(colourState, 0);
            highlightedButton = null;
        }
    }

    private void resetOtherButtons(Button button, int colourState) {
        button.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
        colourCheck.set(colourState, 0);
    }

}
