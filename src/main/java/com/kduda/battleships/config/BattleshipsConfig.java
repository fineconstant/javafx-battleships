package com.kduda.battleships.config;

public enum BattleshipsConfig {
    INSTANCE;

    public boolean isGameRunning = false;
    public boolean isEnemyTurn = false;
    public int cellSize;
}
