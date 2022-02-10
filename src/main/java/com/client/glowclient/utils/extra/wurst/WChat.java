//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package com.client.glowclient.utils.extra.wurst;

import net.minecraft.client.Minecraft;

public final class WChat {
    public static void clearMessages() {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(true);
    }
}

