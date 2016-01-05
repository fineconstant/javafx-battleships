package com.kduda.battleships.controllers;

import com.kduda.battleships.models.board.Board;
import com.kduda.battleships.models.board.Cell;
import com.kduda.battleships.models.board.Position;
import com.kduda.battleships.models.units.Unit;
import com.kduda.battleships.models.units.UnitFactory;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {
    public VBox enemyBoardArea;
    public VBox playerBoardArea;

    private Board enemyBoard;
    private Board playerBoard;
    private Unit currentUnit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUnit = UnitFactory.INSTANCE.getNextUnit();
        initializeBoards();
    }

    private void initializeBoards() {
        enemyBoard = new Board(true, this::enemyBoardClick, this::enemyBoardEntered, this::enemyBoardExited);
        enemyBoardArea.getChildren().add(enemyBoard);

        playerBoard = new Board(false, this::playerBoardClick, this::playerBoardEntered, this::playerBoardExited);
        playerBoardArea.getChildren().addAll(playerBoard);
    }

    private void enemyBoardClick(MouseEvent event) {
        //TODO: click handler
        if (!BattleshipsConfig.INSTANCE.isGameRunning)
            return;
    }

    private void enemyBoardEntered(MouseEvent event) {
        //TODO: mouse entered handler
        return;
    }

    private void enemyBoardExited(MouseEvent event) {
        //TODO: mouse exited handler
        return;
    }

    private void playerBoardEntered(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning)
            return;

        //TODO: hint gdzie bedzie jednostka i czy ok
        Cell cell = (Cell) event.getSource();
//        playerBoard.highlightHint(unit);

    }

    private void playerBoardExited(MouseEvent event) {
        //TODO: mouse exited handler
        return;
    }

    private void playerBoardClick(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning)
            return;

        if (currentUnit == null) {
            startGame();
        }

        Cell cell = (Cell) event.getSource();
        Position cellPosition = new Position(cell.POSITION.getX(), cell.POSITION.getY());
        boolean wasPlacementSuccessful = playerBoard.placeUnit(currentUnit, cellPosition);

        if (wasPlacementSuccessful)
            this.currentUnit = UnitFactory.INSTANCE.getNextUnit();
    }


    //HANDLERS enemy board click
//        enemyBoard = new Board(true, event -> {
//            if (!running)
//                return;
//
//            Cell cell = (Cell) event.getSource();
//            if (cell.wasShot)
//                return;
//
//            enemyTurn = !cell.shootCell();
//
//            if (enemyBoard.ships == 0) {
//                System.out.println("YOU WIN");
//                System.exit(0);
//            }
//
//            if (enemyTurn)
//                enemyMove();
//        });


    private void startGame() {
        //TODO: place enemy ships
        BattleshipsConfig.INSTANCE.isGameRunning = true;
    }
}
