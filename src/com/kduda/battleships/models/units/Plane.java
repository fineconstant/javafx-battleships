package com.kduda.battleships.models.units;

import static com.kduda.battleships.models.units.Direction.*;

public class Plane extends Unit {
    private final int LENGTH;
    private Direction direction;

    public Plane() {
        super(5);
        this.direction = South;
        this.LENGTH = 3;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void rotateUnit() {
        switch (this.direction) {
            case North:
                this.direction = East;
                break;
            case East:
                this.direction = South;
                break;
            case South:
                this.direction = West;
                break;
            case West:
                this.direction = North;
                break;
        }
    }

    @Override
    public int getLength() {
        return this.LENGTH;
    }
}
