package com.example.testapplication.activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.testapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Log.w("Works?", "WORKSSSSSSSSS");

        TextView textView = findViewById(R.id.instructions);
        textView.setText("Rules of the game:");
    }
}
