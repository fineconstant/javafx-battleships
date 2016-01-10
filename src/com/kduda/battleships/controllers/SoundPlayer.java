package com.kduda.battleships.controllers;

import com.kduda.battleships.models.board.CellType;
import javafx.scene.media.AudioClip;

import java.net.URISyntaxException;

public enum SoundPlayer {
    INSTANCE;
    private final AudioClip SHIP_PLACED;
    private final AudioClip TANK_PLACED;
    private final AudioClip PLANE_PLACED;
    private final AudioClip HIT;
    private final AudioClip DESTROYED;
    private final AudioClip WATER_MISS;
    private final AudioClip LAND_MISS;
    private final AudioClip GAME_WON;
    private final AudioClip GAME_LOST;
    private final AudioClip INTRO;
    private final AudioClip ERROR;

    boolean isSoundEnabled = true;

    SoundPlayer() {
        this.SHIP_PLACED = getAudioClip("/com/kduda/battleships/assets/audio/ship_placed.wav");
        this.TANK_PLACED = getAudioClip("/com/kduda/battleships/assets/audio/tank_placed.wav");
        this.PLANE_PLACED = getAudioClip("/com/kduda/battleships/assets/audio/plane_placed.wav");
        this.HIT = getAudioClip("/com/kduda/battleships/assets/audio/hit.wav");
        this.DESTROYED = getAudioClip("/com/kduda/battleships/assets/audio/destroyed.wav");
        this.WATER_MISS = getAudioClip("/com/kduda/battleships/assets/audio/water_miss.wav");
        this.LAND_MISS = getAudioClip("/com/kduda/battleships/assets/audio/land_miss.mp3");
        this.GAME_WON = getAudioClip("/com/kduda/battleships/assets/audio/game_won.wav");
        this.GAME_LOST = getAudioClip("/com/kduda/battleships/assets/audio/lost_trumpet.wav");
        this.INTRO = getAudioClip("/com/kduda/battleships/assets/audio/intro.wav");
        this.ERROR = getAudioClip("/com/kduda/battleships/assets/audio/error.wav");
    }

    public void shipPlaced() {
        if (!isSoundEnabled) return;
        SHIP_PLACED.play();
    }

    public void tankPlaced() {
        if (!isSoundEnabled) return;
        TANK_PLACED.play();
    }

    public void planePlaced() {
        if (!isSoundEnabled) return;
        PLANE_PLACED.play();
    }

    public void hit() {
        if (!isSoundEnabled) return;
        HIT.play();
    }

    public void destroyed() {
        if (!isSoundEnabled) return;
        DESTROYED.play();
    }

    public void missSound(CellType type) {
        if (!isSoundEnabled) return;
        if (type == CellType.Sea) WATER_MISS.play();
        else LAND_MISS.play();
    }

    public void gameWon() {
        if (!isSoundEnabled) return;
        GAME_WON.play();
    }

    public void gameLost() {
        if (!isSoundEnabled) return;
        GAME_LOST.play();
    }

    public void intro() {
        if (!isSoundEnabled) return;
        INTRO.play();
    }

    public void error() {
        if (!isSoundEnabled) return;
        ERROR.play();
    }

    private AudioClip getAudioClip(String path) {
        AudioClip clip = null;
        try {
            clip = new AudioClip(getClass().getResource(path).toURI().toString());
        } catch (URISyntaxException ignored) {

        }
        return clip;
    }
}