package com.kduda.battleships.models.board;

import com.kduda.battleships.models.units.Plane;
import com.kduda.battleships.models.units.Ship;
import com.kduda.battleships.models.units.Tank;
import com.kduda.battleships.models.units.Unit;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Cell extends Rectangle {
    public final Position POSITION;
    public final CellType TYPE;

    private final Board BOARD;
    private boolean wasShot = false;
    private Unit unit = null;
    private Paint currentFill;
    private Paint currentStroke;

    public Cell(int x, int y, Board board) {
        super(30, 30);

        this.POSITION = new Position(x, y);
        this.BOARD = board;

        if (y > 11) {
            this.TYPE = CellType.Land;
            setFill(Color.SANDYBROWN);
        } else {
            this.TYPE = CellType.Sea;
            setFill(Color.BLUE);
        }

        setStroke(Color.BLACK);

        saveCurrentColors();
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean shootCell() {
        this.wasShot = true;

        if (unit != null) {
            unit.hit();
            setColorsAndSave(Color.ORANGE,Color.ORANGE);

            if (!unit.isAlive()) {
                //FIXME: pozmieniac to
                Unit unit = this.getUnit();
                BOARD.destroyUnit(unit);
            }
            return true;
        }
        setColorsAndSave(Color.GRAY,Color.GRAY);
        return false;
    }

    public boolean isEmpty() {
        return this.unit == null;
    }

    public boolean isSurfaceValid(Unit unit) {
        if (unit instanceof Ship && this.TYPE == CellType.Land)
            return false;

        //noinspection RedundantIfStatement
        if (unit instanceof Tank && this.TYPE == CellType.Sea)
            return false;

        return true;
    }

    public void saveCurrentColors() {
        this.currentFill = this.getFill();
        this.currentStroke = this.getStroke();
    }

    public boolean isSurfaceValid(Plane plane) {
        return true;
    }

    public void loadSavedColors() {
        this.setFill(this.currentFill);
        this.setStroke(this.currentStroke);
    }

    public void setColors(Color fillColor, Color strokeColor) {
        this.setFill(fillColor);
        this.setStroke(strokeColor);
    }

    public void setColorsAndSave(Color fillColor, Color strokeColor) {
        setColors(fillColor, strokeColor);
        saveCurrentColors();
    }

    @Override
    public String toString() {
        return "x = " + this.POSITION.getX() + ", y = " + this.POSITION.getY();
    }

    public boolean wasShot() {
        return this.wasShot;
    }
}
