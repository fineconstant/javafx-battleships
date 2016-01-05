package com.kduda.battleships.controllers;

import com.kduda.battleships.models.board.Board;
import com.kduda.battleships.models.board.Cell;
import com.kduda.battleships.models.units.Unit;
import com.kduda.battleships.models.units.UnitFactory;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {
    public VBox enemyBoardArea;
    public VBox playerBoardArea;
    Board enemyBoard;
    Board playerBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBoards();
    }

    private void initializeBoards() {
        enemyBoard = new Board(true, event -> {
            //TODO: click handler
            if (!BattleshipsConfig.INSTANCE.isGameRunning)
                return;
        }, event -> {
            //TODO: hover handler
            return;
        });
        enemyBoardArea.getChildren().add(enemyBoard);

        playerBoard = new Board(false, event -> {
            if (BattleshipsConfig.INSTANCE.isGameRunning)
                return;

            //TODO: rozstawianie statkow
            Unit unit = UnitFactory.INSTANCE.getNextUnit();
            if (unit == null) {
                //TODO: start game
                startGame();
            }
            Cell cell = (Cell) event.getSource();
            playerBoard.placeUnit(unit, cell);

        }, event -> {
            //TODO: hover handler
            if (BattleshipsConfig.INSTANCE.isGameRunning)
                return;
            Cell cell = (Cell) event.getSource();
        });
        playerBoardArea.getChildren().addAll(playerBoard);


        //HANDLERS

//        playerBoard = new Board(false, event -> {
//            if (running)
//                return;
//
//            Cell cell = (Cell) event.getSource();
//            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
//                if (--shipsToPlace == 0) {
//                    startGame();
//                }
//            }
//        });



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


    }

    private void startGame() {
        System.out.println("game started");
        BattleshipsConfig.INSTANCE.isGameRunning = true;
    }
}
