//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.MapColor
 */
package com.client.glowclient.utils.mc.map;

import com.client.glowclient.utils.render.Colors;
import net.minecraft.block.material.MapColor;

public class MapColors {
    private static final int[] COLOR_LIST;
    private static final int[] BASE_COLORS;

    public static int getColor(int index) {
        return COLOR_LIST[index];
    }

    public static int colorListLength() {
        return COLOR_LIST.length;
    }

    public static int getBaseColor(int index) {
        return BASE_COLORS[index];
    }

    public static int baseColorListLength() {
        return BASE_COLORS.length;
    }

    static {
        int i;
        int baseColorsLength = 0;
        for (i = MapColor.COLORS.length - 1; i >= 0; --i) {
            if (MapColor.COLORS[i] == null) continue;
            baseColorsLength = i + 1;
            break;
        }
        BASE_COLORS = new int[baseColorsLength];
        COLOR_LIST = new int[baseColorsLength * 4];
        for (i = 0; i < BASE_COLORS.length; ++i) {
            MapColors.BASE_COLORS[i] = MapColor.COLORS[i].colorValue;
        }
        for (i = 0; i < BASE_COLORS.length; ++i) {
            int[] rgb = Colors.toRGBAArray(BASE_COLORS[i]);
            MapColors.COLOR_LIST[i * 4 + 0] = Colors.toRGBA(rgb[0] * 180 / 255, rgb[1] * 180 / 255, rgb[2] * 180 / 255, 0);
            MapColors.COLOR_LIST[i * 4 + 1] = Colors.toRGBA(rgb[0] * 220 / 255, rgb[1] * 220 / 255, rgb[2] * 220 / 255, 0);
            MapColors.COLOR_LIST[i * 4 + 2] = BASE_COLORS[i];
            MapColors.COLOR_LIST[i * 4 + 3] = Colors.toRGBA(rgb[0] * 135 / 255, rgb[1] * 135 / 255, rgb[2] * 135 / 255, 0);
        }
    }
}

