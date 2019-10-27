package com.example.testapplication;

import com.example.testapplication.shopping.ShoppingItem;

public class PoisonBottle extends ShoppingItem {

    public PoisonBottle(String name, int points, int resource) {
        super(name, points, resource);
        imageValue = R.drawable.poisonbottle;
    }
}
