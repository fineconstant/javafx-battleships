package com.kduda.battleships.models.board;

import com.kduda.battleships.models.units.GroundLevelUnit;
import com.kduda.battleships.models.units.Plane;
import com.kduda.battleships.models.units.Unit;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Board extends Parent {
    //TODO: podzial na podklasy playerboard enemyboard
    public int units = 19;
    protected ArrayList<Cell> currentUnitCells;
    protected boolean isCurrentUnitLocationValid = false;
    private VBox column = new VBox();
    private boolean isEnemyBoard = false;
    private Random random = new Random();

    public Board(boolean isEnemyBoard, EventHandler<? super MouseEvent> mouseClickHandler,
                 EventHandler<? super MouseEvent> mouseEnteredHandler,
                 EventHandler<? super MouseEvent> mouseExitedHandler) {
        this.isEnemyBoard = isEnemyBoard;
        this.isCurrentUnitLocationValid = false;
        this.currentUnitCells = new ArrayList<>();
        this.random = new Random();

        for (int y = 0; y < 22; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 14; x++) {
                Cell cell = new Cell(x, y, this);
                cell.setOnMouseClicked(mouseClickHandler);
                cell.setOnMouseEntered(mouseEnteredHandler);
                cell.setOnMouseExited(mouseExitedHandler);
                row.getChildren().add(cell);
            }
            column.getChildren().add(row);
        }
        getChildren().add(column);
    }

    //region unit placement
    public boolean placeUnit(Unit unit) {
        boolean wasUnitPlaced = placeUnitOnBoard(unit);
        return wasUnitPlaced;
    }

    public void placeUnitRandomly(Unit unit) {
        //TODO: implement
        boolean wasUnitPlaced = false;

        do {
            int xPosition = this.random.nextInt(14);
            int yPosition = this.random.nextInt(22);
            int rotation = this.random.nextInt(4);
            for (int i = 0; i < rotation; i++) {
                unit.rotateUnit();
            }

            Position cellPosition = new Position(xPosition, yPosition);
            currentUnitCells.clear();

            if (unit instanceof GroundLevelUnit) {
                if (((GroundLevelUnit) unit).getOrientation() == Orientation.VERTICAL)
                    isCurrentUnitLocationValid = isVerticalLocationDownwardsValid(unit, cellPosition);
                else
                    isCurrentUnitLocationValid = isHorizontalLocationForwardValid(unit, cellPosition);
            } else {
                Plane plane = (Plane) unit;
                switch (plane.getDirection()) {
                    case North:
                        isCurrentUnitLocationValid = isNorthLocationValid(plane, cellPosition);
                        break;
                    case East:
                        isCurrentUnitLocationValid = isEastLocationValid(plane, cellPosition);
                        break;
                    case South:
                        isCurrentUnitLocationValid = isSouthLocationValid(plane, cellPosition);
                        break;
                    case West:
                        isCurrentUnitLocationValid = isWestLocationValid(plane, cellPosition);
                        break;
                }
            }
            wasUnitPlaced = placeUnitOnBoard(unit);
        }

        while (!wasUnitPlaced);
        //dodac punkt do hash
    }

    private boolean placeUnitOnBoard(Unit unit) {
        if (this.isCurrentUnitLocationValid) placeCurrentUnitInCells(unit);
        else return false;
        return true;
    }

    private void placeCurrentUnitInCells(Unit unit) {
        for (Cell currentUnitCell : this.currentUnitCells) {
            placeUnitInCell(unit, currentUnitCell);
        }
        this.isCurrentUnitLocationValid = false;
    }
    //endregion

    //region universal location checks
    protected boolean isVerticalLocationDownwardsValid(Unit unit, Position cellPosition) {
        int unitLength = unit.getLength();
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        boolean result = true;
        for (int i = yPosition; i < yPosition + unitLength; i++) {
            Cell cell = null;
            if (isValidPoint(xPosition, i)) {
                cell = getCell(xPosition, i);
                if (cell != null) this.currentUnitCells.add(cell);
            } else result = false;

            if (result) {
                if (!cell.isSurfaceValid(unit)) result = false;

                if (!cell.isEmpty()) result = false;

                for (Cell neighbor : getAdjacentCells(xPosition, i))
                    if (!neighbor.isEmpty()) result = false;
            }
        }
        return result;
    }

    private boolean isVerticalLocationUpwardsValid(int xPosition, int yPosition, int length) {
        boolean result = true;
        for (int i = yPosition; i > yPosition - length; i--) {

            Cell cell = null;
            if (isValidPoint(xPosition, i)) {
                cell = getCell(xPosition, i);
                if (cell != null) this.currentUnitCells.add(cell);
            } else result = false;

            if (result) {
                if (!cell.isEmpty()) result = false;

                for (Cell neighbor : getAdjacentCells(xPosition, i))
                    if (!neighbor.isEmpty()) result = false;
            }
        }
        return result;
    }

    protected boolean isHorizontalLocationForwardValid(Unit unit, Position cellPosition) {
        int unitLength = unit.getLength();
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        boolean result = true;
        for (int i = xPosition; i < xPosition + unitLength; i++) {
            Cell cell = null;
            if (isValidPoint(i, yPosition)) {
                cell = getCell(i, yPosition);
                if (cell != null) this.currentUnitCells.add(cell);
            } else result = false;

            if (result) {
                if (!cell.isSurfaceValid(unit)) result = false;

                if (!cell.isEmpty()) result = false;

                for (Cell neighbor : getAdjacentCells(i, yPosition))
                    if (!neighbor.isEmpty()) result = false;
            }
        }
        return result;
    }

    private boolean isHorizontalLocationBackwardsValid(int xPosition, int yPosition, int length) {
        boolean result = true;
        for (int i = xPosition; i > xPosition - length; i--) {
            if (!isValidPoint(i, yPosition)) result = false;

            Cell cell = null;
            if (isValidPoint(i, yPosition)) {
                cell = getCell(i, yPosition);
                if (cell != null) this.currentUnitCells.add(cell);
            }

            if (result) {
                if (!cell.isEmpty()) result = false;

                for (Cell neighbor : getAdjacentCells(i, yPosition))
                    if (!neighbor.isEmpty()) result = false;
            }
        }
        return result;
    }
    //endregion

    //region planes specific location checks
    protected boolean isNorthLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();
        int length = plane.getLength();

        boolean result = true;
        if (!isVerticalLocationUpwardsValid(xPosition, yPosition, length)) result = false;
        //noinspection RedundantIfStatement
        if (!areHorizontalNeighborsValid(xPosition, yPosition)) result = false;

        return result;
    }

    protected boolean isEastLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        boolean result = true;
        if (!isHorizontalLocationForwardValid(plane, cellPosition)) result = false;
        //noinspection RedundantIfStatement
        if (!areVerticalNeighborsValid(xPosition, yPosition)) result = false;

        return result;
    }

    protected boolean isSouthLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        boolean result = true;
        if (!isVerticalLocationDownwardsValid(plane, cellPosition)) result = false;
        //noinspection RedundantIfStatement
        if (!areHorizontalNeighborsValid(xPosition, yPosition)) result = false;

        return result;
    }

    protected boolean isWestLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();
        int length = plane.getLength();

        boolean result = true;
        if (!isHorizontalLocationBackwardsValid(xPosition, yPosition, length)) result = false;
        //noinspection RedundantIfStatement
        if (!areVerticalNeighborsValid(xPosition, yPosition)) result = false;

        return result;
    }

    private boolean areHorizontalNeighborsValid(int xPosition, int yPosition) {
        boolean result = true;
        if (!isValidPlacementCell(xPosition - 1, yPosition)) result = false;
        //noinspection RedundantIfStatement
        if (!isValidPlacementCell(xPosition + 1, yPosition)) result = false;
        return result;
    }

    private boolean areVerticalNeighborsValid(int xPosition, int yPosition) {
        boolean result = true;
        if (!isValidPlacementCell(xPosition, yPosition - 1)) result = false;
        //noinspection RedundantIfStatement
        if (!isValidPlacementCell(xPosition, yPosition + 1)) result = false;
        return result;
    }

    private boolean isValidPlacementCell(int xPosition, int yPosition) {
        if (!isValidPoint(xPosition, yPosition)) return false;

        Cell cell = getCell(xPosition, yPosition);
        if (cell != null) this.currentUnitCells.add(cell);

        if (!cell.isEmpty()) return false;

        for (Cell neighbor : getAdjacentCells(xPosition, yPosition))
            if (!neighbor.isEmpty()) return false;

        return true;
    }
    //endregion

    //region showing and removing hints - PlayerBoard
    public abstract void showPlacementHint(Unit unit, Cell cell);

    public abstract void removePlacementHint();
    //endregion

    //region helper methods
    protected void changeCurrentUnitColors(Color fillColor, Color strokeColor) {
        for (Cell currCell : this.currentUnitCells) {
            currCell.saveCurrentColors();
            currCell.setColors(fillColor, strokeColor);
        }
    }

    private void placeUnitInCell(Unit unit, Cell cell) {
        cell.setUnit(unit);
//        if (!this.isEnemyBoard) {
//            //TODO: rozne kolory dla roznych jednostek
//            cell.setColors(Color.WHITE, Color.GREEN);
//            cell.saveCurrentColors();
//        }
        //FIXME: enemy debug
        cell.setColors(Color.WHITE, Color.GREEN);
        cell.saveCurrentColors();

    }

    private boolean isValidPoint(int x, int y) {
        return x >= 0 && x < 14 && y >= 0 && y < 22;
    }

    public Cell getCell(int x, int y) {
        HBox row = (HBox) column.getChildren().get(y);
        return (Cell) row.getChildren().get(x);
    }

    private Cell[] getAdjacentCells(int x, int y) {
        Position[] positions = new Position[]{
                new Position(x, y - 1), //N
                new Position(x + 1, y - 1), //NE
                new Position(x + 1, y), //E
                new Position(x + 1, y + 1), //SE
                new Position(x, y + 1), //S
                new Position(x - 1, y + 1), //SW
                new Position(x - 1, y), //W
                new Position(x - 1, y - 1) //NW
        };

        List<Cell> neighbors = new ArrayList<>();

        for (Position position : positions) {
            int xPosition = position.getX();
            int yPosition = position.getY();
            if (isValidPoint(xPosition, yPosition))
                neighbors.add(getCell(xPosition, yPosition));
        }
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        return neighbors.toArray(new Cell[0]);
    }
    //endregion
}