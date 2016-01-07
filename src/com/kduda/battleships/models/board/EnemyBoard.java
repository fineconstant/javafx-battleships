package com.kduda.battleships.models.board;

import com.kduda.battleships.models.units.Unit;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class EnemyBoard extends Board {
    public EnemyBoard(boolean isEnemyBoard,
                      EventHandler<? super MouseEvent> mouseClickHandler,
                      EventHandler<? super MouseEvent> mouseEnteredHandler,
                      EventHandler<? super MouseEvent> mouseExitedHandler) {
        super(isEnemyBoard, mouseClickHandler, mouseEnteredHandler, mouseExitedHandler);
    }

    @Override
    public void showPlacementHint(Unit unit, Cell cell) {
    }

    @Override
    public void removePlacementHint() {
    }
}
