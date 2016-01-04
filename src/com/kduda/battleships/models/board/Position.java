package com.kduda.battleships.models.board;

public class Position {
    private final int X;
    private final int Y;

    public Position(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
