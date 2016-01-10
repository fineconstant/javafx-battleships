package com.kduda.battleships.config;

import javafx.scene.paint.Color;

public enum Colors {
    WATERFILL(36, 166, 254),
    WATERSTROKE(5, 12, 221),
    LANDFILL(120, 81, 58),
    LANDSTROKE(0, 0, 0),
    HINTVALIDFILL(255,0,0),
    HINTVALIDSTROKE(255,0,0),
    HINTINVALIDFILL(255,0,0),
    HINTINVALIDSTROKE(255,0,0);

    private final int r;
    private final int g;
    private final int b;

    Colors(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color getColor() {
        return Color.rgb(r, g, b);
    }
}