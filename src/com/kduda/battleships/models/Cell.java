package com.kduda.battleships.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Cell extends Rectangle {
    public int x, y;
    public Unit unit = null;
    public boolean wasShot = false;

    private Board board;

    public Cell(int x, int y, Board board) {
        super(40, 40);

        this.board = board;

        this.x = x;
        this.y = y;

        setFillColor(y);

        setStroke(Color.BLACK);
    }

    private void setFillColor(int y) {
        if (y > 11)
            setFill(Color.SANDYBROWN);
        else
            setFill(Color.BLUE);
    }

    public boolean shootCell() {
        wasShot = true;
        setFill(Color.GRAY);

        if (unit != null) {
            unit.hit();
            setFill(Color.RED);
            if (!unit.isAlive()) {
                board.units--;
            }
            return true;
        }
        return false;
    }
}
