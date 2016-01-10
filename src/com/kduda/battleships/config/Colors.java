package com.kduda.battleships.config;

import javafx.scene.paint.Color;

public enum Colors {
    WATERFILL(36, 166, 254),
    WATERSTROKE(5, 12, 221),
    LANDFILL(120, 81, 58),
    LANDSTROKE(10, 10, 10),
    HINTVALIDFILL(47, 196, 54),
    HINTVALIDSTROKE(0, 45, 3),
    HINTINVALIDFILL(207, 60, 60),
    HINTINVALIDSTROKE(98, 18, 32),
    MISS(40, 40, 40),
    HIT(204, 110, 50),
    SHIP(178,194,207),
    TANK(95,125,10),
    PLANE(80,80,80);

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