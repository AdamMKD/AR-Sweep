package com.example.testapplication;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<ShoppingItem> myCart;
    private int totalPoints;
    private int initialTime;

    public ShoppingCart(int initialTime){
        this.initialTime = initialTime;
        myCart = new ArrayList<ShoppingItem>();
    }

    public int getInitialTime() {
        return initialTime;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void addShoppingItemsToCart(ShoppingItem shoppingItem) {
        myCart.add(shoppingItem);
        totalPoints+=10;
    }
}
