package com.example.testapplication;

public class ShoppingItem {

    private String name;
    private int points;
    private int resource;

    public ShoppingItem(String name, int points, int resource) {
        this.name = name;
        this.points = points;
        this.resource = resource;
    }

    public ShoppingItem(String name, int points) {
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public int getResource() {
        return resource;
    }

}
