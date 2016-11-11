package com.kduda.battleships.model.board;

import com.kduda.battleships.model.util.Colors;
import com.kduda.battleships.model.unit.GroundLevelUnit;
import com.kduda.battleships.model.unit.Plane;
import com.kduda.battleships.model.unit.Unit;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;

public class PlayerBoard extends Board {
    public PlayerBoard(EventHandler<? super MouseEvent> mouseClickHandler,
                       EventHandler<? super MouseEvent> mouseEnteredHandler,
                       EventHandler<? super MouseEvent> mouseExitedHandler) {
        super(mouseClickHandler, mouseEnteredHandler, mouseExitedHandler);
    }

    //region not used
    @Override
    public void showShootingHint() {

    }

    @Override
    public void removeShootingHint() {

    }
    //endregion

    //region showing and removing hints
    public void showPlacementHint(Unit unit, Cell cell) {
        Position cellPosition = new Position(cell.POSITION.getX(), cell.POSITION.getY());
        currentUnitCells.clear();

        if (unit instanceof GroundLevelUnit)
            validateGroundUnit((GroundLevelUnit) unit, cellPosition);
        else validatePlane((Plane) unit, cellPosition);

        currentUnitCells.forEach(Cell::stopTimer);

        if (isCurrentUnitLocationValid)
            changeCurrentUnitColors(Colors.HINTVALIDFILL.getColor(), Colors.HINTVALIDSTROKE.getColor());
        else changeCurrentUnitColors(Colors.HINTINVALIDFILL.getColor(), Colors.HINTINVALIDSTROKE.getColor());
    }

    private void validateGroundUnit(GroundLevelUnit unit, Position cellPosition) {
        if (unit.getOrientation() == Orientation.VERTICAL)
            isCurrentUnitLocationValid = isVerticalLocationDownwardsValid(unit, cellPosition);
        else
            isCurrentUnitLocationValid = isHorizontalLocationForwardValid(unit, cellPosition);
    }

    private void validatePlane(Plane plane, Position cellPosition) {
        switch (plane.getDirection()) {
            case NORTH:
                isCurrentUnitLocationValid = (isNorthLocationValid(plane, cellPosition));
                break;
            case EAST:
                isCurrentUnitLocationValid = isEastLocationValid(plane, cellPosition);
                break;
            case SOUTH:
                isCurrentUnitLocationValid = isSouthLocationValid(plane, cellPosition);
                break;
            case WEST:
                isCurrentUnitLocationValid = isWestLocationValid(plane, cellPosition);
                break;
        }
    }

    public void removePlacementHint() {
        this.currentUnitCells.forEach(cell -> {
            cell.loadSavedColors();
            if(cell.isEmpty())cell.startTimer();
        });
    }
    //endregion
}
