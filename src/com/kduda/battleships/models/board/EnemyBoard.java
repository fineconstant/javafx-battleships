package com.kduda.battleships.models.board;

import com.kduda.battleships.models.units.Unit;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EnemyBoard extends Board {
    public EnemyBoard(boolean isEnemyBoard,
                      EventHandler<? super MouseEvent> mouseClickHandler,
                      EventHandler<? super MouseEvent> mouseEnteredHandler,
                      EventHandler<? super MouseEvent> mouseExitedHandler) {
        super(isEnemyBoard, mouseClickHandler, mouseEnteredHandler, mouseExitedHandler);
    }

    //region not used
    @Override
    public void showPlacementHint(Unit unit, Cell cell) {
    }

    @Override
    public void removePlacementHint() {
    }
    //endregion

    @Override
    public void showShootingHint() {
        targetCell.saveCurrentColors();

        if (isTargetCellValid)
            targetCell.setColors(Color.GREEN, Color.GREEN);
        else
            targetCell.setColors(Color.RED, Color.RED);
    }

    @Override
    public void removeShootingHint() {
        targetCell.loadSavedColors();
    }
}
