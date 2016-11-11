package com.kduda.battleships.model.board;

class Position {
    private final int X;
    private final int Y;

    Position(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    int getX() {
        return X;
    }

    int getY() {
        return Y;
    }
}
