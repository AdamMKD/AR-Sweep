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

import java.util.concurrent.atomic.AtomicReference;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    public static int SCORE = 0;
    private QuestionManager questionManager;
    private int counter = 60;
    private TextView textView;

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
                timeDisplayPage();
            }
        }.start();

        setQuestion();
    }

    /**
     * Sets the questions that the user needs to answer and the options the user can pick from.
     */
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

    /**
     * This takes you to the page that gives you the shopping list and a gateway to the AR.
     */
    public void timeDisplayPage() {
        Intent intent = new Intent(this, TimeDisplayActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * This is called when a user presses a button in the quiz.
     * It will tell the user if they answered correctly or incorrectly then show new questions.`
     *
     * @param view the view that the button needs for onClick.
     */
    public void pickButton(View view) {
        switch (view.getId()) {
            case R.id.option1:
                Toast.makeText(this, result(findViewById(R.id.option1)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.option2:
                Toast.makeText(this, result(findViewById(R.id.option2)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.option3:
                Toast.makeText(this, result(findViewById(R.id.option3)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.option4:
                Toast.makeText(this, result(findViewById(R.id.option4)), Toast.LENGTH_SHORT).show();
                break;
        }
        // Set the new questions
        setQuestion();
    }

    /**
     * Checks whether the button clicked was the right answer or not.
     * Adds score to the point if it was.
     *
     * @param button the button that the user clicked on.
     * @return return the string that tells us if the answer was right or wrong.
     */
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

    // This is to disable the back button so the user can't leave mid way during the quiz
    @Override
    public void onBackPressed() {
    }

}