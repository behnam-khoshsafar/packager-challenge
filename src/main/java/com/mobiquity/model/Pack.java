package com.mobiquity.model;

import java.util.List;

public class Pack {

    private final int maxCapacity;
    private final List<Item> items;

    public Pack(int maxCapacity, List<Item> items) {
        this.maxCapacity = maxCapacity;
        this.items = items;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Item> getItems() {
        return items;
    }
}
