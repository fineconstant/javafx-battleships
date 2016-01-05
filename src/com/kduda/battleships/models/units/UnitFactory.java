package com.kduda.battleships.models.units;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public enum UnitFactory {
    INSTANCE;
    List<Unit> units;
    ListIterator<Unit> unitsIterator;

    UnitFactory() {
        initializeUnitsList();
    }

    void initializeUnitsList() {
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

//        units.add(createPlane());
//        units.add(createPlane());
//        units.add(createPlane());

        unitsIterator = units.listIterator();
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

    public Unit getNextUnit() {
        if (unitsIterator.hasNext())
            return unitsIterator.next();
        else
            return null;
    }

    public Unit getPreviousUnit() {
        if (unitsIterator.hasPrevious())
            return unitsIterator.previous();
        else
            return null;
    }
}
