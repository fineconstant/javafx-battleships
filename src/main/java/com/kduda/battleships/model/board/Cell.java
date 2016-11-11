package com.kduda.battleships.model.board;

import com.kduda.battleships.config.BattleshipsConfig;
import com.kduda.battleships.controller.SoundPlayer;
import com.kduda.battleships.model.unit.Ship;
import com.kduda.battleships.model.unit.Tank;
import com.kduda.battleships.model.unit.Unit;
import com.kduda.battleships.model.util.BackgroundFrames;
import com.kduda.battleships.model.util.Colors;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;


public class Cell extends Rectangle {
    private static final int FRAME_TIME_MS = 500;
    private static final int FRAME_WIDTH = 170;
    private static final int FRAME_HEIGHT = 256;
    final Position POSITION;
    private final CellType TYPE;

    private final Board BOARD;
    private boolean wasShot = false;
    private Unit unit = null;
    private Paint currentFill;
    private Paint currentStroke;
    private AnimationTimer animationTimer;
    private BufferedImage backgroundFrames;
    private ImagePattern backgroundPattern;
    private long lastUpdateTime = System.currentTimeMillis();
    private int row = 0;
    private int col = 0;

    Cell(int x, int y, Board board) {
        super(BattleshipsConfig.INSTANCE.cellSize, BattleshipsConfig.INSTANCE.cellSize);

        this.POSITION = new Position(x, y);
        this.BOARD = board;

        if (y > 11) {
            this.TYPE = CellType.Land;
            this.backgroundFrames = BackgroundFrames.INSTANCE.grassFrames;
            setColors(Colors.LANDFILL.getColor(), Colors.LANDSTROKE.getColor());
        } else {
            this.TYPE = CellType.Sea;
            this.backgroundFrames = BackgroundFrames.INSTANCE.waterFrames;
            setColors(Colors.WATERFILL.getColor(), Colors.WATERSTROKE.getColor());
        }

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long frame) {
                if (System.currentTimeMillis() - lastUpdateTime > FRAME_TIME_MS) {
                    if (backgroundFrames == null) return;

                    BufferedImage subimage = backgroundFrames.getSubimage(FRAME_WIDTH * col, FRAME_HEIGHT * row, FRAME_WIDTH, FRAME_HEIGHT);
                    backgroundPattern = new ImagePattern(SwingFXUtils.toFXImage(subimage, null));
                    setFill(backgroundPattern);

                    col++;
                    row++;
                    if (col == 3) col = 0;
                    if (row == 4) row = 0;
                    lastUpdateTime = System.currentTimeMillis();
                }
            }
        };
        animationTimer.start();

        saveCurrentColors();
    }

    public void stopTimer() {
        this.animationTimer.stop();
    }

    void startTimer() {
        this.animationTimer.start();
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    boolean shootCell() {
        this.wasShot = true;
        this.stopTimer();
        if (unit != null) {
            unit.hit();
            setColorsAndSave(Colors.HIT.getColor(), Colors.HIT.getColor());

            if (!unit.isAlive()) {
                SoundPlayer.INSTANCE.destroyed();
                Unit unit = this.getUnit();
                BOARD.destroyUnit(unit);
            } else SoundPlayer.INSTANCE.hit();
            return true;
        } else SoundPlayer.INSTANCE.missSound(this.TYPE);

        setColorsAndSave(Colors.MISS.getColor(), Colors.MISS.getColor());
        return false;
    }

    boolean isEmpty() {
        return this.unit == null;
    }

    boolean isSurfaceValid(Unit unit) {
        if (unit instanceof Ship && this.TYPE == CellType.Land)
            return false;

        //noinspection RedundantIfStatement
        if (unit instanceof Tank && this.TYPE == CellType.Sea)
            return false;

        return true;
    }

    //region colors setters
    void saveCurrentColors() {
        this.currentFill = this.getFill();
        this.currentStroke = this.getStroke();
    }

    void loadSavedColors() {
        this.setFill(this.currentFill);
        this.setStroke(this.currentStroke);
    }

    void setColors(Color fillColor, Color strokeColor) {
        this.setFill(fillColor);
        this.setStroke(strokeColor);
    }

    void setColorsAndSave(Color fillColor, Color strokeColor) {
        setColors(fillColor, strokeColor);
        saveCurrentColors();
    }
    //endregion

    public boolean wasShot() {
        return this.wasShot;
    }

    @Override
    public String toString() {
        return "x = " + this.POSITION.getX() + ", y = " + this.POSITION.getY();
    }
}
