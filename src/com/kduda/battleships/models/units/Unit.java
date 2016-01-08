package com.kduda.battleships.models.units;

import com.kduda.battleships.models.board.Cell;

import java.util.ArrayList;

public abstract class Unit {
    protected ArrayList<Cell> cells;
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

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
}