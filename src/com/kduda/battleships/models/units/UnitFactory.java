package com.kduda.battleships.models.units;

import java.util.ArrayList;

public enum UnitFactory {
    INSTANCE;
    ArrayList<Unit> units;

    UnitFactory() {
        units = new ArrayList<>();
        units.add(createGroundLevelUnit(UnitType.Ship, 4));

        units.add(createGroundLevelUnit(UnitType.Ship, 3));
        units.add(createGroundLevelUnit(UnitType.Ship, 3));

        units.add(createGroundLevelUnit(UnitType.Ship, 2));
        units.add(createGroundLevelUnit(UnitType.Ship, 2));
        units.add(createGroundLevelUnit(UnitType.Ship, 2));

        units.add(createGroundLevelUnit(UnitType.Ship, 1));
        units.add(createGroundLevelUnit(UnitType.Ship, 1));
        units.add(createGroundLevelUnit(UnitType.Ship, 1));

        units.add(createGroundLevelUnit(UnitType.Tank, 4));

        units.add(createGroundLevelUnit(UnitType.Tank, 3));
        units.add(createGroundLevelUnit(UnitType.Tank, 3));

        units.add(createGroundLevelUnit(UnitType.Tank, 2));
        units.add(createGroundLevelUnit(UnitType.Tank, 2));
        units.add(createGroundLevelUnit(UnitType.Tank, 2));


        units.add(createPlane());
        units.add(createPlane());
        units.add(createPlane());
    }

    public Unit createNextUnit() {

        return null;
    }

    Unit createGroundLevelUnit(UnitType type, int size) {
        switch (type) {
            case Ship:
                return new Ship(size);
            case Tank:
                return new Tank(size);
        }
        return null;
    }

    Unit createPlane() {
        return new Plane();
    }
}
