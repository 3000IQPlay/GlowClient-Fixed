/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.render;

import com.client.glowclient.mods.background.ColorManager;
import java.awt.Color;

public class RainbowUtils {
    public static Color getRainbowColor(long offset, float fade) {
        float speed = Float.parseFloat(ColorManager.rainbowSpeed.getDouble() + "E10f");
        float hue = ColorManager.rendering.getMode().equals("Down") ? (float)(System.nanoTime() - offset) / speed % 1.0f : (float)(System.nanoTime() + offset) / speed % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, (float)(ColorManager.saturation.getDouble() / 255.0), (float)(ColorManager.brightness.getDouble() / 255.0))), 16);
        Color c2 = new Color((int)color);
        return new Color((float)c2.getRed() / 255.0f * fade, (float)c2.getGreen() / 255.0f * fade, (float)c2.getBlue() / 255.0f * fade, (float)c2.getAlpha() / 255.0f);
    }
}

