package com.example.lsd.Models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Category {
    private final int categoryId;
    private String name;
    private double price;
    private final BooleanProperty selected;

    public Category(int categoryId, String name, double price) {
        this.categoryId = categoryId;
        this.name = name;
        this.selected = new SimpleBooleanProperty(false);
    }

    @Override
    public String toString() {
        return name;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {return categoryId;}

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
