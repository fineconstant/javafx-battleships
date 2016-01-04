package com.kduda.battleships.models.units;

public abstract class Unit {
    private int health;

    public Unit(int size) {
        this.health = size;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void hit() {
        //TODO: implementation
    }
}



/* public final int LENGTH;
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

    }*/