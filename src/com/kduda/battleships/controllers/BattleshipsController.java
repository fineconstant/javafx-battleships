package com.kduda.battleships.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {
    public GridPane myBoard;
    public GridPane opponentBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<GridPane> boards = new ArrayList<>();
        boards.add(myBoard);
        boards.add(opponentBoard);
        initializeBoards(boards);
    }

    private void initializeBoards(ArrayList<GridPane> boards) {
        for (int row = 0; row < 22; row++) {
            for (int col = 0; col < 14; col++) {
                for (GridPane board : boards) {
                    board.add(new Button(col + " " + row), col, row);
                }
            }
        }
    }
}
