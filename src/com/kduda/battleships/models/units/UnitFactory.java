package com.kduda.battleships.models.units;

import javafx.geometry.Orientation;

public enum UnitFactory {
    INSTANCE;

    Unit createUnit(UnitType type, int size, Orientation orientation) {
        switch (type) {
            case Ship:
                return new Ship(size, orientation);
            case Tank:
                break;
            case Plane:
                break;
        }
        return null;
    }


}
