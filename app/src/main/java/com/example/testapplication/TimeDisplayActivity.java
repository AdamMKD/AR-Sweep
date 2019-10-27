package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class TimeDisplayActivity extends AppCompatActivity {

    public static Map<ShoppingItem, Integer> shoppingListMap = new HashMap<>();
    private ArrayList<ShoppingItem> shoppingItems = new ArrayList<>(Arrays.asList(
            new Banana("Banana", 5, R.raw.banana),
            new Chocolate("Chocolate", 5, R.raw.chocolatebar),
            new Cookie("Cookie", 3, R.raw.cookie),
            new MilkCarton("Milk Carton", 7, R.raw.milkcarton),
            new WhippedCream("Whipped Cream", 12, R.raw.cannedwhipcream)
    ));
    private Random random = new Random();
    private int numberOfitems = 5;
    private ArrayList<ShoppingItem> displayedListitems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_display);

        TextView scoreTextView = findViewById(R.id.scoreText);
        scoreTextView.setText("Congratulation, your score is : " + QuizActivity.SCORE);

        TextView shoppingListTextView = findViewById(R.id.shoppingList);
        shoppingListTextView.setText("This is you shopping list:");

        populateItemMap();
        prepearShoppingList();

//        // Part of the back button functionality.
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // This is for the back button
//        QuizActivity.SCORE = 0;
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * This takes you to the AR game part of the app.
     *
     * @param view the view that the button needs.
     */
    public void arPage(View view) {
        Intent intent = new Intent(this, ARCoreTest.class);
        startActivity(intent);
    }

    /**
     * Populates the map that is passed to create the list to be passed to the AR part.
     */
    private void populateItemMap() {
        for (int i = 0; i < 4; i++) {
            int item = random.nextInt(numberOfitems);
            Log.w("Item number", "Item is " + item);
            while (displayedListitems.contains(shoppingItems.get(item))) {
                item = random.nextInt(numberOfitems);
            }
            shoppingListMap.put(shoppingItems.get(item), random.nextInt(10) + 1);
            displayedListitems.add(shoppingItems.get(item));
        }
    }

    /**
     * Displayed the image and text using random images.
     */
    private void prepearShoppingList() {
        ImageView item1Image = findViewById(R.id.item1Image);
        ImageView item2Image = findViewById(R.id.item2Image);
        ImageView item3Image = findViewById(R.id.item3Image);
        ImageView item4Image = findViewById(R.id.item4Image);

        TextView item1Text = findViewById(R.id.item1Text);
        TextView item2Text = findViewById(R.id.item2Text);
        TextView item3Text = findViewById(R.id.item3Text);
        TextView item4Text = findViewById(R.id.item4Text);

        item1Image.setImageResource(displayedListitems.get(0).imageValue);
        item2Image.setImageResource(displayedListitems.get(1).imageValue);
        item3Image.setImageResource(displayedListitems.get(2).imageValue);
        item4Image.setImageResource(displayedListitems.get(3).imageValue);

        item1Text.setText(String.format("%s x%d", displayedListitems.get(0).getName(), shoppingListMap.get(displayedListitems.get(0))));
        item2Text.setText(String.format("%s x%d", displayedListitems.get(1).getName(), shoppingListMap.get(displayedListitems.get(1))));
        item3Text.setText(String.format("%s x%d", displayedListitems.get(2).getName(), shoppingListMap.get(displayedListitems.get(2))));
        item4Text.setText(String.format("%s x%d", displayedListitems.get(3).getName(), shoppingListMap.get(displayedListitems.get(3))));
    }
}
