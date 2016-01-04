package com.kduda.battleships.models.units;

import javafx.geometry.Orientation;

public class GroundLevelUnit extends Unit {
    public final int LENGTH;
    private Orientation orientation;

    public GroundLevelUnit(int size, Orientation orientation) {
        super(size);
        this.LENGTH = size;
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
