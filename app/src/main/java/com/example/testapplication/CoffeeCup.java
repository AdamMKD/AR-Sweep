package com.example.testapplication;

import com.example.testapplication.shopping.ShoppingItem;

public class CoffeeCup extends ShoppingItem {
    public CoffeeCup(String name, int points, int resource) {
        super(name, points, resource);
        imageValue = R.drawable.coffeecup;
    }
}
