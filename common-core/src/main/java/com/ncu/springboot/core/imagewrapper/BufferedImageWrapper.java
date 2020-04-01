package com.ncu.springboot.core.imagewrapper;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 包装bufferedImage，使BufferedImage实现可序列化
 */
public class BufferedImageWrapper implements Serializable {
    private int width;
    private int height;
    private int[] pixels;

    public BufferedImageWrapper(BufferedImage bufferedImage) {
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        pixels = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public BufferedImage getImage() {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.setRGB(0, 0, width, height, pixels, 0, width);
        return bi;
    }
}
