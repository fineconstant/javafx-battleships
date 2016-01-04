package com.kduda.battleships.models.board;

import com.kduda.battleships.models.Position;
import com.kduda.battleships.models.units.Unit;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Cell extends Rectangle {
    public final Position POSITION;
    public final CellType TYPE;
    private final Board BOARD;
    public Unit unit = null;
    public boolean wasShot = false;

    public Cell(int x, int y, Board board) {
        super(40, 40);

        this.POSITION = new Position(x, y);
        this.BOARD = board;

        if (y > 11) {
            this.TYPE = CellType.Land;
            setFill(Color.SANDYBROWN);
        } else {
            this.TYPE = CellType.Water;
            setFill(Color.BLUE);
        }

        setStroke(Color.BLACK);
    }


    public boolean shootCell() {
        wasShot = true;
        setFill(Color.GRAY);

        if (unit != null) {
            unit.hit();
            setFill(Color.RED);
            if (!unit.isAlive()) {
                BOARD.units--;
            }
            return true;
        }
        return false;
    }


}
