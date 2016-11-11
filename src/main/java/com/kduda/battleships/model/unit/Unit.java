package com.kduda.battleships.model.unit;

import com.kduda.battleships.model.board.Cell;

import java.util.ArrayList;

public abstract class Unit {
    private ArrayList<Cell> cells;
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

    public void registerCell(Cell cell) {
        if (this.cells == null)
            this.cells = new ArrayList<>();
        this.cells.add(cell);
    }
}