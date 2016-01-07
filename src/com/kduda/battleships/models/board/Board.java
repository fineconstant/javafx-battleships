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

public class Board extends Parent {
    //TODO: nadklasa board, klasy playerboard i enemyboard
    public int units = 19;
    private VBox column = new VBox();
    private boolean isEnemyBoard = false;
    private ArrayList<Cell> currentUnitCells;
    private boolean isCurrentUnitLocationValid = false;

    public Board(boolean isEnemyBoard, EventHandler<? super MouseEvent> mouseClickHandler,
                 EventHandler<? super MouseEvent> mouseEnteredHandler,
                 EventHandler<? super MouseEvent> mouseExitedHandler) {
        this.isEnemyBoard = isEnemyBoard;
        this.isCurrentUnitLocationValid = false;
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

    public boolean placeUnit(Unit unit, Position cellPosition) {
        boolean wasUnitPlaced;

        if (unit instanceof GroundLevelUnit)
            wasUnitPlaced = placeGroundLevelUnit((GroundLevelUnit) unit);
        else
            wasUnitPlaced = placePlane((Plane) unit, cellPosition);

        return wasUnitPlaced;
    }

    private boolean placeGroundLevelUnit(GroundLevelUnit unit) {
            if (this.isCurrentUnitLocationValid) placeCurrentUnitInCells(unit);
            else return false;
        return true;
    }

    private boolean isVerticalLocationDownwardsValid(Unit unit, Position cellPosition) {
        int unitLength = unit.getLength();
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        for (int i = yPosition; i < yPosition + unitLength; i++) {
            if (!isValidPoint(xPosition, i)) return false;

            Cell cell = getCell(xPosition, i);
            if (cell != null) this.currentUnitCells.add(cell);

            if (!cell.isSurfaceValid(unit)) return false;

            if (!cell.isEmpty()) return false;

            for (Cell neighbor : getAdjacentCells(xPosition, i))
                if (!neighbor.isEmpty()) return false;
        }
        return true;
    }

    private boolean isHorizontalLocationForwardValid(Unit unit, Position cellPosition) {
        int unitLength = unit.getLength();
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        for (int i = xPosition; i < xPosition + unitLength; i++) {
            if (!isValidPoint(i, yPosition)) return false;

            Cell cell = getCell(i, yPosition);
            if (cell != null) this.currentUnitCells.add(cell);

            if (!cell.isSurfaceValid(unit)) return false;

            if (!cell.isEmpty()) return false;

            for (Cell neighbor : getAdjacentCells(i, yPosition))
                if (!neighbor.isEmpty()) return false;
        }
        return true;
    }

    private void placeCurrentUnitInCells(Unit unit) {
        for (Cell currentUnitCell : this.currentUnitCells) {
            placeUnitInCell(unit, currentUnitCell);
        }
        this.isCurrentUnitLocationValid = false;
    }

    private boolean placePlane(Plane plane, Position cellPosition) {
        switch (plane.getDirection()) {
            case North:
                if (isNorthLocationValid(plane, cellPosition)) placePlaneNorth(plane, cellPosition);
                else return false;
                break;
            case East:
                if (isEastLocationValid(plane, cellPosition)) placePlaneEast(plane, cellPosition);
                else return false;
                break;
            case South:
                if (isSouthLocationValid(plane, cellPosition)) placePlaneSouth(plane, cellPosition);
                else return false;
                break;
            case West:
                if (isWestLocationValid(plane, cellPosition)) placePlaneWest(plane, cellPosition);
                else return false;
                break;
        }
        return true;
    }

    private boolean isNorthLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();
        int length = plane.getLength();

        if (!isVerticalLocationPlaneValid(xPosition, yPosition, length)) return false;
        //noinspection RedundantIfStatement
        if (!areHorizontalNeighborsValid(xPosition, yPosition)) return false;

        return true;
    }

    private boolean isEastLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        if (!isHorizontalLocationForwardValid(plane, cellPosition)) return false;

        //noinspection RedundantIfStatement
        if (!areVerticalNeighborsValid(xPosition, yPosition)) return false;

        return true;
    }

    private boolean isSouthLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        if (!isVerticalLocationDownwardsValid(plane, cellPosition)) return false;
        //noinspection RedundantIfStatement
        if (!areHorizontalNeighborsValid(xPosition, yPosition)) return false;

        return true;
    }

    private boolean isWestLocationValid(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();
        int length = plane.getLength();

        if (!isHorizontalLocationPlaneValid(xPosition, yPosition, length)) return false;
        //noinspection RedundantIfStatement
        if (!areVerticalNeighborsValid(xPosition, yPosition)) return false;

        return true;
    }

    private boolean isVerticalLocationPlaneValid(int xPosition, int yPosition, int length) {
        for (int i = yPosition; i > yPosition - length; i--) {
            if (!isValidPoint(xPosition, i)) return false;

            Cell cell = getCell(xPosition, i);

            if (!cell.isEmpty()) return false;

            for (Cell neighbor : getAdjacentCells(xPosition, i))
                if (!neighbor.isEmpty()) return false;
        }
        return true;
    }

    private boolean isHorizontalLocationPlaneValid(int xPosition, int yPosition, int length) {
        for (int i = xPosition; i > xPosition - length; i--) {
            if (!isValidPoint(i, yPosition)) return false;

            Cell cell = getCell(i, yPosition);

            if (!cell.isEmpty()) return false;

            for (Cell neighbor : getAdjacentCells(i, yPosition))
                if (!neighbor.isEmpty()) return false;
        }
        return true;
    }

    private boolean areHorizontalNeighborsValid(int xPosition, int yPosition) {
        if (!isValidPlacementCell(xPosition - 1, yPosition)) return false;
        //noinspection RedundantIfStatement
        if (!isValidPlacementCell(xPosition + 1, yPosition)) return false;

        return true;
    }

    private boolean areVerticalNeighborsValid(int xPosition, int yPosition) {
        if (!isValidPlacementCell(xPosition, yPosition - 1)) return false;
        //noinspection RedundantIfStatement
        if (!isValidPlacementCell(xPosition, yPosition + 1)) return false;

        return true;
    }

    private boolean isValidPlacementCell(int xPosition, int yPosition) {
        if (!isValidPoint(xPosition, yPosition)) return false;

        Cell cell = getCell(xPosition, yPosition);

        if (!cell.isEmpty()) return false;

        for (Cell neighbor : getAdjacentCells(xPosition, yPosition))
            if (!neighbor.isEmpty()) return false;

        return true;
    }

    private void placePlaneNorth(Plane plane, Position cellPosition) {
        int unitLength = plane.getLength();
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        placeVerticalPlaneBody(plane, unitLength, xPosition, yPosition);
        placeHorizontalPlaneWings(plane, xPosition, yPosition);
    }

    private void placePlaneEast(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        placeCurrentUnitInCells(plane);
        placeVerticalPlaneWings(plane, xPosition, yPosition);
    }

    private void placePlaneSouth(Plane plane, Position cellPosition) {
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        placeCurrentUnitInCells(plane);
        placeHorizontalPlaneWings(plane, xPosition, yPosition);
    }

    private void placePlaneWest(Plane plane, Position cellPosition) {
        int unitLength = plane.getLength();
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        placeHorizontalPlaneBody(plane, unitLength, xPosition, yPosition);
        placeVerticalPlaneWings(plane, xPosition, yPosition);

    }

    private void placeVerticalPlaneBody(Plane plane, int unitLength, int xPosition, int yPosition) {
        for (int i = yPosition; i > yPosition - unitLength; i--) {
            Cell cell = getCell(xPosition, i);
            placeUnitInCell(plane, cell);
        }
    }

    private void placeHorizontalPlaneBody(Plane plane, int unitLength, int xPosition, int yPosition) {
        for (int i = xPosition; i > xPosition - unitLength; i--) {
            Cell cell = getCell(i, yPosition);
            placeUnitInCell(plane, cell);
        }
    }

    private void placeHorizontalPlaneWings(Plane plane, int xPosition, int yPosition) {
        Cell cell = getCell(xPosition - 1, yPosition);
        placeUnitInCell(plane, cell);
        cell = getCell(xPosition + 1, yPosition);
        placeUnitInCell(plane, cell);
    }

    private void placeVerticalPlaneWings(Plane plane, int xPosition, int yPosition) {
        Cell cell = getCell(xPosition, yPosition - 1);
        placeUnitInCell(plane, cell);
        cell = getCell(xPosition, yPosition + 1);
        placeUnitInCell(plane, cell);
    }

    private void placeUnitInCell(Unit unit, Cell cell) {
        cell.setUnit(unit);
        if (!this.isEnemyBoard) {
            //TODO: rozne kolory dla roznych jednostek
            cell.setColors(Color.WHITE, Color.GREEN);
            cell.saveCurrentColors();
        }
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

    public void showPlacementHint(Unit unit, Cell cell) {
        Position cellPosition = new Position(cell.POSITION.getX(), cell.POSITION.getY());
        this.currentUnitCells = new ArrayList<>();

        if (unit instanceof GroundLevelUnit)
            showGroundUnitHint((GroundLevelUnit) unit, cellPosition);
        else showPlaneHint((Plane) unit, cellPosition);
    }

    private void showGroundUnitHint(GroundLevelUnit unit, Position cellPosition) {
        if (unit.getOrientation() == Orientation.VERTICAL) {
            if (isVerticalLocationDownwardsValid(unit, cellPosition)) {
                this.isCurrentUnitLocationValid = true;
                changeCurrentUnitColors(Color.GREEN, Color.GREEN);
            } else {
                this.isCurrentUnitLocationValid = false;
                changeCurrentUnitColors(Color.RED, Color.RED);
            }

        } else {
            if (isHorizontalLocationForwardValid(unit, cellPosition)) {
                this.isCurrentUnitLocationValid = true;
                changeCurrentUnitColors(Color.GREEN, Color.GREEN);
            } else {
                this.isCurrentUnitLocationValid = false;
                changeCurrentUnitColors(Color.RED, Color.RED);
            }
        }
    }

    private void showPlaneHint(Plane plane, Position cellPosition) {
        int unitLength = plane.getLength();
        int xPosition = cellPosition.getX();
        int yPosition = cellPosition.getY();

        switch (plane.getDirection()) {
            case North:
                if (isNorthLocationValid(plane, cellPosition)) {
                    showPlaneNorthHint(unitLength, xPosition, yPosition, Color.GREEN, Color.GREEN);
                    return;
                } else {
                    showPlaneNorthHint(unitLength, xPosition, yPosition, Color.RED, Color.RED);
                    return;
                }
            case East:
                if (isEastLocationValid(plane, cellPosition)) {
                    showPlaneEastHint(unitLength, xPosition, yPosition, Color.GREEN, Color.GREEN);
                    return;
                } else {
                    showPlaneEastHint(unitLength, xPosition, yPosition, Color.RED, Color.RED);
                    return;
                }
            case South:
                if (isSouthLocationValid(plane, cellPosition)) {
                    showPlaneSouthHint(unitLength, xPosition, yPosition, Color.GREEN, Color.GREEN);
                    return;
                } else {
                    showPlaneSouthHint(unitLength, xPosition, yPosition, Color.RED, Color.RED);
                    return;
                }
            case West:
                if (isWestLocationValid(plane, cellPosition))
                    showPlaneWestHint(unitLength, xPosition, yPosition, Color.GREEN, Color.GREEN);
                else
                    showPlaneWestHint(unitLength, xPosition, yPosition, Color.RED, Color.RED);
        }
    }

    private void showPlaneNorthHint(int unitLength, int xPosition, int yPosition, Color fillColor, Color strokeColor) {
        Cell currCell;
        for (int i = yPosition; i > yPosition - unitLength; i--) {
            currCell = getCell(xPosition, i);
            currCell.saveCurrentColors();
            currCell.setColors(fillColor, strokeColor);
        }
        showHorizontalWingsHint(xPosition, yPosition, fillColor, strokeColor);
    }

    private void showPlaneEastHint(int unitLength, int xPosition, int yPosition, Color fillColor, Color strokeColor) {
        Cell currCell = getCell(xPosition, yPosition);

        changeCurrentUnitColors(fillColor, strokeColor);

        showVerticalWingsHint(xPosition, yPosition, fillColor, strokeColor);
    }

    private void showPlaneSouthHint(int unitLength, int xPosition, int yPosition, Color fillColor, Color strokeColor) {
        Cell currCell = getCell(xPosition, yPosition);

        changeCurrentUnitColors(fillColor, strokeColor);
        showHorizontalWingsHint(xPosition, yPosition, fillColor, strokeColor);
    }

    private void showPlaneWestHint(int unitLength, int xPosition, int yPosition, Color fillColor, Color strokeColor) {
        Cell currCell;

        for (int i = xPosition; i > xPosition - unitLength; i--) {
            currCell = getCell(i, yPosition);
            currCell.saveCurrentColors();
            currCell.setColors(fillColor, strokeColor);
        }
        showVerticalWingsHint(xPosition, yPosition, fillColor, strokeColor);
    }

    private void showHorizontalWingsHint(int xPosition, int yPosition, Color fillColor, Color strokeColor) {
        Cell currCell;
        currCell = getCell(xPosition - 1, yPosition);
        currCell.saveCurrentColors();
        currCell.setColors(fillColor, strokeColor);

        currCell = getCell(xPosition + 1, yPosition);
        currCell.saveCurrentColors();
        currCell.setColors(fillColor, strokeColor);

    }

    private void showVerticalWingsHint(int xPosition, int yPosition, Color fillColor, Color strokeColor) {
        Cell currCell;
        currCell = getCell(xPosition, yPosition - 1);
        currCell.saveCurrentColors();
        currCell.setColors(fillColor, strokeColor);

        currCell = getCell(xPosition, yPosition + 1);
        currCell.saveCurrentColors();
        currCell.setColors(fillColor, strokeColor);

    }

    private void changeCurrentUnitColors(Color fillColor, Color strokeColor) {
        for (Cell currCell : this.currentUnitCells) {
            currCell.saveCurrentColors();
            currCell.setColors(fillColor, strokeColor);
        }
    }

    public void removePlacementHint() {
        this.currentUnitCells.forEach(Cell::loadSavedColors);
    }
}