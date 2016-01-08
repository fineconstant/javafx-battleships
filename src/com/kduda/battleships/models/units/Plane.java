package com.kduda.battleships.models.units;

import static com.kduda.battleships.models.units.Direction.*;

public class Plane extends Unit {
    private final int LENGTH;
    private Direction direction;

    public Plane() {
        super(5);
        this.direction = NORTH;
        this.LENGTH = 3;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void rotateUnit() {
        switch (this.direction) {
            case NORTH:
                this.direction = EAST;
                break;
            case EAST:
                this.direction = SOUTH;
                break;
            case SOUTH:
                this.direction = WEST;
                break;
            case WEST:
                this.direction = NORTH;
                break;
        }
    }

    @Override
    public int getLength() {
        return this.LENGTH;
    }
}
