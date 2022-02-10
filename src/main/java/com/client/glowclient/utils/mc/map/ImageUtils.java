/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.mc.map;

import com.client.glowclient.utils.client.Globals;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageUtils {
    public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        int imageType = preserveAlpha ? 1 : 2;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    public static BufferedImage getImageFromUrl(String link) {
        BufferedImage image = null;
        try {
            URL url = new URL(link);
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            Globals.LOGGER.error("Could not download image");
        }
        return image;
    }

    public static int[][] imageToArray(BufferedImage imageIn) {
        int width = imageIn.getWidth();
        int height = imageIn.getHeight();
        int[][] data = new int[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                data[i][j] = imageIn.getRGB(i, j);
            }
        }
        return data;
    }
}

