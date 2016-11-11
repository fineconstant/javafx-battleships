package com.kduda.battleships.model.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum BackgroundFrames {
    INSTANCE;

    public BufferedImage waterFrames = loadImage("animation/water_frames.png");

    public BufferedImage grassFrames = loadImage("animation/grass_frames.png");

    private BufferedImage loadImage(String location) {
        BufferedImage result = null;

        try {
            result = ImageIO.read(ClassLoader.getSystemResource(location));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
