package com.example.testapplication;

public class ShoppingItem {

    private String name;
    private int points;

    public ShoppingItem(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}
