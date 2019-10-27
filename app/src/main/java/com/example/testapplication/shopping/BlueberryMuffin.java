package com.example.testapplication.shopping;

import com.example.testapplication.R;
import com.example.testapplication.shopping.ShoppingItem;

public class BlueberryMuffin extends ShoppingItem {
    public BlueberryMuffin(String name, int points, int resource) {
        super(name, points, resource);
        imageValue = R.drawable.blueberrymuffin;
    }
}
