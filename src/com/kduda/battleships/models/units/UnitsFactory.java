package com.kduda.battleships.models.units;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public enum UnitsFactory {
    INSTANCE;
    final int UNITS_NUMBER;
    List<Unit> units;
    ListIterator<Unit> unitsIterator;

    UnitsFactory() {
        initializeUnitsFactory();
        this.UNITS_NUMBER = units.size();
    }

    public void initializeUnitsFactory() {
        units = new ArrayList<>();

        units.add(createGroundLevelUnit(UnitType.SHIP, 4));

        units.add(createGroundLevelUnit(UnitType.SHIP, 3));
        units.add(createGroundLevelUnit(UnitType.SHIP, 3));

        units.add(createGroundLevelUnit(UnitType.SHIP, 2));
        units.add(createGroundLevelUnit(UnitType.SHIP, 2));
        units.add(createGroundLevelUnit(UnitType.SHIP, 2));

        units.add(createGroundLevelUnit(UnitType.SHIP, 1));
        units.add(createGroundLevelUnit(UnitType.SHIP, 1));
        units.add(createGroundLevelUnit(UnitType.SHIP, 1));
        units.add(createGroundLevelUnit(UnitType.SHIP, 1));

        units.add(createGroundLevelUnit(UnitType.TANK, 4));

        units.add(createGroundLevelUnit(UnitType.TANK, 3));
        units.add(createGroundLevelUnit(UnitType.TANK, 3));
        units.add(createGroundLevelUnit(UnitType.TANK, 3));

        units.add(createGroundLevelUnit(UnitType.TANK, 2));
        units.add(createGroundLevelUnit(UnitType.TANK, 2));
        units.add(createGroundLevelUnit(UnitType.TANK, 2));
        units.add(createGroundLevelUnit(UnitType.TANK, 2));

        units.add(createPlane());
        units.add(createPlane());
        units.add(createPlane());
        units.add(createPlane());

        unitsIterator = units.listIterator();
    }

    Unit createGroundLevelUnit(UnitType type, int size) {
        switch (type) {
            case SHIP:
                return new Ship(size);
            case TANK:
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

    public int getUnitsNumber() {
        return this.UNITS_NUMBER;
    }
}
