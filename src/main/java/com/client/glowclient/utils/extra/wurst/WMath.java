//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package com.client.glowclient.utils.extra.wurst;

import net.minecraft.util.math.MathHelper;

public final class WMath {
    public static int clamp(int num, int min, int max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static float clamp(float num, float min, float max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static double clamp(double num, double min, double max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static float sin(float value) {
        return MathHelper.sin((float)value);
    }

    public static float cos(float value) {
        return MathHelper.cos((float)value);
    }

    public static float wrapDegrees(float value) {
        return MathHelper.wrapDegrees((float)value);
    }

    public static double wrapDegrees(double value) {
        return MathHelper.wrapDegrees((double)value);
    }
}

