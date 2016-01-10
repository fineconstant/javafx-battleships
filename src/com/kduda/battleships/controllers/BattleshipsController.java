package com.kduda.battleships.controllers;

import com.kduda.battleships.models.board.Board;
import com.kduda.battleships.models.board.Cell;
import com.kduda.battleships.models.board.EnemyBoard;
import com.kduda.battleships.models.board.PlayerBoard;
import com.kduda.battleships.models.units.Unit;
import com.kduda.battleships.models.units.UnitsFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {
    public VBox enemyBoardArea;
    public VBox playerBoardArea;
    public Label enemyShipsLabel;
    public Label playerShipsLabel;
    public CheckMenuItem soundsCheckItem;

    private Board enemyBoard;
    private Board playerBoard;
    private Unit currentUnit;
    private Random random = new Random();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soundsCheckItem.setSelected(true);
        currentUnit = UnitsFactory.INSTANCE.getNextUnit();
        initializeBoards();
    }

    private void initializeBoards() {
        UnitsFactory.INSTANCE.initializeUnitsFactory();
        currentUnit = UnitsFactory.INSTANCE.getNextUnit();

        enemyBoard = new EnemyBoard(this::enemyBoardClick, this::enemyBoardEntered, this::enemyBoardExited);
        if (enemyBoardArea.getChildren().size() > 1) enemyBoardArea.getChildren().remove(1);
        enemyBoardArea.getChildren().add(enemyBoard);

        playerBoard = new PlayerBoard(this::playerBoardClick, this::playerBoardEntered, this::playerBoardExited);
        if (playerBoardArea.getChildren().size() > 1) playerBoardArea.getChildren().remove(1);
        playerBoardArea.getChildren().addAll(playerBoard);

        enemyShipsLabel.textProperty().bind(enemyBoard.unitsLeftProperty().asString());
        playerShipsLabel.textProperty().bind(playerBoard.unitsLeftProperty().asString());
    }

    private void enemyBoardClick(MouseEvent event) {
        if (!BattleshipsConfig.INSTANCE.isGameRunning) return;

        Cell cell = (Cell) event.getSource();
        if (cell.wasShot()) return;

        boolean wasHit = enemyBoard.shoot(cell);
        BattleshipsConfig.INSTANCE.isEnemyTurn = !wasHit;

        if (enemyBoard.getUnitsLeft() == 0) {
            BattleshipsConfig.INSTANCE.isGameRunning = false;

            SoundPlayer.INSTANCE.gameWon();
            Alert alert = showEndGameModal(true);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) initializeBoards();
            else exitClicked();

        }

        if (BattleshipsConfig.INSTANCE.isEnemyTurn) enemyTurn();
    }


    private void enemyTurn() {
        while (BattleshipsConfig.INSTANCE.isEnemyTurn) {
            int xPosition = this.random.nextInt(14);
            int yPosition = this.random.nextInt(22);

            Cell cell = playerBoard.getCell(xPosition, yPosition);
            if (cell.wasShot()) continue;

            boolean wasHit = playerBoard.shoot(cell);
            BattleshipsConfig.INSTANCE.isEnemyTurn = wasHit;

            if (playerBoard.getUnitsLeft() == 0) {
                BattleshipsConfig.INSTANCE.isGameRunning = false;

                SoundPlayer.INSTANCE.gameLost();
                Alert alert = showEndGameModal(false);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) initializeBoards();
                else exitClicked();
            }
        }

        //delay
        double time = 1000 * random.nextDouble();
        try {
            Thread.sleep((long) time);
        } catch (InterruptedException ignored) {
        }
    }

    private Alert showEndGameModal(boolean hasPlayerWon) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        if (hasPlayerWon) alert.setHeaderText("You Win!");
        else alert.setHeaderText("You Lost!");
        alert.setContentText("Do you want to play again?");
        return alert;
    }

    private void enemyBoardEntered(MouseEvent event) {
        if (!BattleshipsConfig.INSTANCE.isGameRunning)
            return;

        Cell cell = (Cell) event.getSource();
        enemyBoard.validateCell(cell);
        enemyBoard.showShootingHint();
    }

    private void enemyBoardExited(MouseEvent event) {
        if (!BattleshipsConfig.INSTANCE.isGameRunning) return;

        enemyBoard.removeShootingHint();
    }

    private void playerBoardEntered(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) return;

        Cell cell = (Cell) event.getSource();
        playerBoard.showPlacementHint(currentUnit, cell);
    }

    private void playerBoardExited(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) return;

        playerBoard.removePlacementHint();
    }

    private void playerBoardClick(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) return;

        Cell cell = (Cell) event.getSource();

        if (event.getButton() == MouseButton.SECONDARY) {
            playerBoard.removePlacementHint();
            this.currentUnit.rotateUnit();
            playerBoard.showPlacementHint(currentUnit, cell);
            return;
        }

        boolean wasPlacementSuccessful = playerBoard.placeUnit(currentUnit);

        if (wasPlacementSuccessful)
            this.currentUnit = UnitsFactory.INSTANCE.getNextUnit();

        if (currentUnit == null) startGame();
    }

    private void startGame() {
        //TODO: ui changes
        //TODO: zapisanie do pliku
        BattleshipsConfig.INSTANCE.isGameRunning = true;
        placeUnitsOnEnemyBoard();
    }

    private void placeUnitsOnEnemyBoard() {
        UnitsFactory.INSTANCE.initializeUnitsFactory();
        currentUnit = UnitsFactory.INSTANCE.getNextUnit();
        placeUnitsRandomly(enemyBoard);
    }

    private void placeUnitsRandomly(Board board) {
        while (currentUnit != null) {
            board.placeUnitRandomly(currentUnit);
            currentUnit = UnitsFactory.INSTANCE.getNextUnit();
        }

        if (!BattleshipsConfig.INSTANCE.isGameRunning)
            startGame();
    }

    public void rotateUnitClicked() {
        this.currentUnit.rotateUnit();
    }

    public void randomPlacementClicked() {
        placeUnitsRandomly(playerBoard);
    }

    public void exitClicked() {
        Platform.exit();
        System.exit(0);
    }

    public void soundOptionAction(ActionEvent actionEvent) {
        SoundPlayer.INSTANCE.isSoundEnabled = soundsCheckItem.isSelected();
    }
}
