package com.kduda.battleships.controller;

import com.kduda.battleships.config.BattleshipsConfig;
import com.kduda.battleships.model.board.Board;
import com.kduda.battleships.model.board.Cell;
import com.kduda.battleships.model.board.EnemyBoard;
import com.kduda.battleships.model.board.PlayerBoard;
import com.kduda.battleships.model.unit.Unit;
import com.kduda.battleships.model.unit.UnitsFactory;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public Button rotateUnitButton;
    public Button randomPlacementButton;

    private Board enemyBoard;
    private Board playerBoard;
    private Unit currentUnit;
    private Random random = new Random();

    //region initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soundsCheckItem.setSelected(true);
        currentUnit = UnitsFactory.INSTANCE.getNextUnit();
        initializeNewGame();
        SoundPlayer.INSTANCE.intro();
    }

    private void initializeNewGame() {
        UnitsFactory.INSTANCE.initializeUnitsFactory();
        if (currentUnit == null) currentUnit = UnitsFactory.INSTANCE.getNextUnit();
        initializeUIButtons(false);

        enemyBoard = new EnemyBoard(this::enemyBoardClick, this::enemyBoardEntered, this::enemyBoardExited);
        if (enemyBoardArea.getChildren().size() > 1) enemyBoardArea.getChildren().remove(1);
        enemyBoardArea.getChildren().add(enemyBoard);

        playerBoard = new PlayerBoard(this::playerBoardClick, this::playerBoardEntered, this::playerBoardExited);
        if (playerBoardArea.getChildren().size() > 1) playerBoardArea.getChildren().remove(1);
        playerBoardArea.getChildren().addAll(playerBoard);

        enemyShipsLabel.textProperty().bind(enemyBoard.unitsLeftProperty().asString());
        playerShipsLabel.textProperty().bind(playerBoard.unitsLeftProperty().asString());
    }
    //endregion

    //region mouse handlers
    private void enemyBoardClick(MouseEvent event) {
        if (!BattleshipsConfig.INSTANCE.isGameRunning) {
            SoundPlayer.INSTANCE.error();
            return;
        }

        Cell cell = (Cell) event.getSource();
        if (cell.wasShot()) {
            SoundPlayer.INSTANCE.error();
            return;
        }

        boolean wasHit = enemyBoard.shoot(cell);
        BattleshipsConfig.INSTANCE.isEnemyTurn = !wasHit;

        if (enemyBoard.getUnitsLeft() == 0) {
            BattleshipsConfig.INSTANCE.isGameRunning = false;

            SoundPlayer.INSTANCE.gameWon();
            Alert alert = showEndGameModal(true);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) initializeNewGame();
            else exitClicked();

        }
        if (BattleshipsConfig.INSTANCE.isEnemyTurn) enemyTurn();
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
        cell.stopTimer();
        playerBoard.showPlacementHint(currentUnit, cell);
    }

    private void playerBoardExited(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) return;

        playerBoard.removePlacementHint();
    }

    private void playerBoardClick(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) {
            SoundPlayer.INSTANCE.error();
            return;
        }

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
    //endregion

    private void enemyTurn() {
        while (BattleshipsConfig.INSTANCE.isEnemyTurn) {
            int xPosition = this.random.nextInt(14);
            int yPosition = this.random.nextInt(22);

            Cell cell = playerBoard.getCell(xPosition, yPosition);
            if (cell.wasShot()) continue;

            BattleshipsConfig.INSTANCE.isEnemyTurn = playerBoard.shoot(cell);

            if (playerBoard.getUnitsLeft() == 0) {
                BattleshipsConfig.INSTANCE.isGameRunning = false;

                SoundPlayer.INSTANCE.gameLost();
                Alert alert = showEndGameModal(false);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) initializeNewGame();
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

    private void startGame() {
        initializeUIButtons(true);
        BattleshipsConfig.INSTANCE.isGameRunning = true;
        placeEnemyUnits();
    }

    private void placeEnemyUnits() {
        boolean soundState = SoundPlayer.INSTANCE.isSoundEnabled;
        SoundPlayer.INSTANCE.isSoundEnabled = false;
        UnitsFactory.INSTANCE.initializeUnitsFactory();
        currentUnit = UnitsFactory.INSTANCE.getNextUnit();
        placeUnitsRandomly(enemyBoard);
        SoundPlayer.INSTANCE.isSoundEnabled = soundState;
    }

    private void initializeUIButtons(boolean gameStarted) {
        rotateUnitButton.setDisable(gameStarted);
        randomPlacementButton.setDisable(gameStarted);
    }

    //region UI methods
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

    public void soundOptionAction() {
        SoundPlayer.INSTANCE.isSoundEnabled = soundsCheckItem.isSelected();
    }

    public void newGameClicked() {
        BattleshipsConfig.INSTANCE.isGameRunning = false;
        BattleshipsConfig.INSTANCE.isEnemyTurn = false;
        initializeNewGame();
    }
    //endregion
}
