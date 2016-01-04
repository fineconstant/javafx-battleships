package com.kduda.battleships.controllers;

import com.kduda.battleships.models.Board;
import com.kduda.battleships.models.Cell;
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
        enemyBoard = new Board(true, e -> {
            //TODO: click handler
            //TODO: hover handler
        });
        enemyBoardArea.getChildren().add(enemyBoard);

        playerBoard = new Board(false, e -> {
            //TODO: click handler
            //TODO: hover handler
        });
        playerBoardArea.getChildren().addAll(playerBoard);


        //HANDLERS

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
    }
}
