package com.kduda.battleships.models.units;

public class Plane extends Unit {
    private Direction direction;

    public Plane(int size) {
        super(size);
        this.direction = Direction.North;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
