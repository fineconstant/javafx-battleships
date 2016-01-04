package com.kduda.battleships.models.units;

public class Plane extends Unit {
    private Direction direction;

    public Plane(int size, Direction direction) {
        super(size);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
