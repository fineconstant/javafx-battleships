package com.kduda.battleships.controllers;

import com.kduda.battleships.models.board.CellType;
import javafx.scene.media.AudioClip;

import java.io.File;

public enum SoundPlayer {
    INSTANCE;
    boolean isSoundEnabled = true;

    public void shipPlaced() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/ship_placed.wav").toURI().toString());
        clip.play();
    }

    public void tankPlaced() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/tank_placed.wav").toURI().toString());
        clip.play();
    }

    public void planePlaced() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/plane_placed.wav").toURI().toString());
        clip.play();
    }


    public void hit() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/hit.wav").toURI().toString());
        clip.play();
    }

    public void destroyed() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/destroyed.wav").toURI().toString());
        clip.play();
    }

    public void missSound(CellType type) {
        if (type == CellType.Sea) waterMiss();
        else landMiss();
    }

    private void waterMiss() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/water_miss.wav").toURI().toString());
        clip.play();
    }

    private void landMiss() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/land_miss.mp3").toURI().toString());
        clip.play();
    }

    public void gameWon() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/").toURI().toString());
        clip.play();
    }

    public void gameLost() {
        if (!isSoundEnabled) return;
        AudioClip clip = new AudioClip(new File("sounds/lost_trumpet.mp3").toURI().toString());
        clip.play();

    }
}
