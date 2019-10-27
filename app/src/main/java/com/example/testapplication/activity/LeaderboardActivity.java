package com.example.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.testapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.appcompat.app.AppCompatActivity;

import static java.util.stream.Collectors.toMap;

public class LeaderboardActivity extends AppCompatActivity {

    private Map<String, Integer> leaderBoardMap = new HashMap<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderBoardMap.put("Abdi", 10);
        leaderBoardMap.put("Kumail", 8);
        leaderBoardMap.put("Haashim", 6);
        leaderBoardMap.put(ResultActivity.USERNAME, QuizActivity.SCORE);

        Map<String, Integer> sorted = leaderBoardMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        sorted.forEach((s, integer) -> {
            names.add(s);
            score.add(integer);
        });

        TextView nameTextView = findViewById(R.id.nameRank);
        TextView scoreTextView = findViewById(R.id.pointRank);

        nameTextView.setText("");
        scoreTextView.setText("");
        AtomicInteger count = new AtomicInteger();
        names.forEach(s -> {
            if (s != null) {
                count.getAndIncrement();
                nameTextView.append(String.format("%s\n", s));
            }
        });
        if (count.get() != score.size()) {
            score.remove(score.get(score.size() - 1));
        }
        score.forEach(integer -> scoreTextView.append(String.format("%d\n", integer)));

        // Part of the back button functionality.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This is for the back button
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
