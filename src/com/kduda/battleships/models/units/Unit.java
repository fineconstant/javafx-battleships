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
        this.health--;
    }

    public abstract void rotateUnit();
    public abstract int getLength();
}