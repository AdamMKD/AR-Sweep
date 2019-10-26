package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class TimeDisplayActivity extends AppCompatActivity {

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_display);

        TextView scoreTextView = findViewById(R.id.scoreText);
        scoreTextView.setText("Congratulation, your score is : " + QuizActivity.SCORE);

        TextView shoppingListTextView = findViewById(R.id.shoppingList);
        shoppingListTextView.setText("This is you shopping list:");

        prepearShoppingList();

        // Part of the back button functionality.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This is for the back button
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void arPage(View view) {
        Intent intent = new Intent(this, ARCoreTest.class);
        startActivity(intent);
    }

    private void prepearShoppingList() {
        ImageView item1Image = findViewById(R.id.item1Image);
        ImageView item2Image = findViewById(R.id.item2Image);
        ImageView item3Image = findViewById(R.id.item3Image);
        ImageView item4Image = findViewById(R.id.item4Image);

        TextView item1Text = findViewById(R.id.item1Text);
        TextView item2Text = findViewById(R.id.item2Text);
        TextView item3Text = findViewById(R.id.item3Text);
        TextView item4Text = findViewById(R.id.item4Text);

        item1Image.setImageResource(R.drawable.icon);
    }
}
