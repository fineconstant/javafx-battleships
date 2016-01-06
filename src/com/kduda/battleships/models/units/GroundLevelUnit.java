package com.kduda.battleships.models.units;

import javafx.geometry.Orientation;

public class GroundLevelUnit extends Unit {
    public final int LENGTH;
    private Orientation orientation;

    public GroundLevelUnit(int size) {
        super(size);
        this.LENGTH = size;
        this.orientation = Orientation.VERTICAL;
    }

    @Override
    public void rotateUnit() {
        if (this.LENGTH == 1) return;
        if (this.orientation == Orientation.VERTICAL) {
            this.orientation = Orientation.HORIZONTAL;
        } else if (this.orientation == Orientation.HORIZONTAL) {
            this.orientation = Orientation.VERTICAL;
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
