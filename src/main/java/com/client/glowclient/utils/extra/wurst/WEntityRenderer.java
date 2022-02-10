//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.EntityRenderer
 */
package com.client.glowclient.utils.extra.wurst;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;

public final class WEntityRenderer {
    public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking) {
        EntityRenderer.drawNameplate((FontRenderer)fontRendererIn, (String)str, (float)x, (float)y, (float)z, (int)verticalShift, (float)viewerYaw, (float)viewerPitch, (boolean)isThirdPersonFrontal, (boolean)isSneaking);
    }
}

