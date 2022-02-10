/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.render;

public class Colors {
    public static final int WHITE = Colors.toRGBA(255, 255, 255, 255);
    public static final int BLACK = Colors.toRGBA(0, 0, 0, 255);
    public static final int RED = Colors.toRGBA(255, 0, 0, 255);
    public static final int GREEN = Colors.toRGBA(0, 255, 0, 255);
    public static final int BLUE = Colors.toRGBA(0, 104, 255, 255);
    public static final int ORANGE = Colors.toRGBA(255, 128, 0, 255);
    public static final int PURPLE = Colors.toRGBA(163, 73, 163, 255);
    public static final int GRAY = Colors.toRGBA(127, 127, 127, 255);
    public static final int YELLOW = Colors.toRGBA(255, 255, 0, 255);
    public static final int LIGHT_BLUE = Colors.toRGBA(0, 180, 255, 255);
    public static final int AQUA = Colors.toRGBA(0, 255, 225, 255);
    public static final int SHADOW = Colors.toRGBA(20, 20, 20, 230);
    public static final int HUDGRAY = Colors.toRGBA(255, 255, 255, 255);

    public static int toRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b << 0) + (a << 24);
    }

    public static int toRGBA(float r, float g, float b, float a) {
        return Colors.toRGBA((int)(r * 255.0f), (int)(g * 255.0f), (int)(b * 255.0f), (int)(a * 255.0f));
    }

    public static int toRGBA(float[] colors) {
        if (colors.length != 4) {
            throw new IllegalArgumentException("colors[] must have a length of 4!");
        }
        return Colors.toRGBA(colors[0], colors[1], colors[2], colors[3]);
    }

    public static int toRGBA(double[] colors) {
        if (colors.length != 4) {
            throw new IllegalArgumentException("colors[] must have a length of 4!");
        }
        return Colors.toRGBA((float)colors[0], (float)colors[1], (float)colors[2], (float)colors[3]);
    }

    public static int[] toRGBAArray(int colorBuffer) {
        return new int[]{colorBuffer >> 16 & 0xFF, colorBuffer >> 8 & 0xFF, colorBuffer & 0xFF, colorBuffer >> 24 & 0xFF};
    }

    public class Color {
        private final int color;

        public int getAsBuffer() {
            return this.color;
        }

        private Color(int color) {
            this.color = color;
        }

        public int getRed() {
            return this.color >> 16 & 0xFF;
        }

        public int getGreen() {
            return this.color >> 8 & 0xFF;
        }

        public int getBlue() {
            return this.color & 0xFF;
        }

        public int getAlpha() {
            return this.color >> 24 & 0xFF;
        }

        public boolean equals(Object obj) {
            return this == obj || obj instanceof Color && this.color == ((Color)obj).color;
        }

        public int hashCode() {
            return Integer.hashCode(this.color);
        }
    }
}

