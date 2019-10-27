package com.example.testapplication;

import com.example.testapplication.shopping.ShoppingItem;

public class Snowman extends ShoppingItem {
    public Snowman(String name, int points, int resource) {
        super(name, points, resource);
        imageValue = R.drawable.snowman;
    }
}
