package com.mobiquity.model;

public class Item {

    private final int index;
    private final float weight;
    private final int cost;

    public Item(int index, float weight, int cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public float getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
