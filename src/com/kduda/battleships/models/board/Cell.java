package com.kduda.battleships.models.board;

import com.kduda.battleships.assets.Colors;
import com.kduda.battleships.controllers.BattleshipsConfig;
import com.kduda.battleships.controllers.SoundPlayer;
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
        super(BattleshipsConfig.INSTANCE.cellSize, BattleshipsConfig.INSTANCE.cellSize);

        this.POSITION = new Position(x, y);
        this.BOARD = board;

        if (y > 11) {
            this.TYPE = CellType.Land;
            setColors(Colors.LANDFILL.getColor(), Colors.LANDSTROKE.getColor());


        } else {
            this.TYPE = CellType.Sea;
            setColors(Colors.WATERFILL.getColor(), Colors.WATERSTROKE.getColor());
        }

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
            setColorsAndSave(Colors.HIT.getColor(), Colors.HIT.getColor());

            if (!unit.isAlive()) {
                SoundPlayer.INSTANCE.destroyed();
                Unit unit = this.getUnit();
                BOARD.destroyUnit(unit);
            } else SoundPlayer.INSTANCE.hit();
            return true;
        } else SoundPlayer.INSTANCE.missSound(this.TYPE);

        setColorsAndSave(Colors.MISS.getColor(), Colors.MISS.getColor());
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

    //region colors setters
    public void saveCurrentColors() {
        this.currentFill = this.getFill();
        this.currentStroke = this.getStroke();
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
    //endregion

    @Override
    public String toString() {
        return "x = " + this.POSITION.getX() + ", y = " + this.POSITION.getY();
    }

    public boolean wasShot() {
        return this.wasShot;
    }
}
