package com.kduda.battleships.models.units;

import javafx.geometry.Orientation;

public abstract class Unit {
    public final int LENGTH;
    public final Orientation ORIENTATION;
    private int health;

    public Unit(int type, Orientation orientation) {
        this.LENGTH = type;
        this.health = type;
        this.ORIENTATION = orientation;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void hit() {

    }
}
