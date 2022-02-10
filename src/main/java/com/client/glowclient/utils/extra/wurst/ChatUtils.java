//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package com.client.glowclient.utils.extra.wurst;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ChatUtils {
    private static boolean enabled = true;

    public static void setEnabled(boolean enabled) {
        ChatUtils.enabled = enabled;
    }

    public static void component(ITextComponent component) {
        if (enabled) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("\u00ef\u00bf\u00bdc[\u00ef\u00bf\u00bd6Wurst\u00ef\u00bf\u00bdc]\u00ef\u00bf\u00bdf ").appendSibling(component));
        }
    }

    public static void message(String message) {
        ChatUtils.component((ITextComponent)new TextComponentString(message));
    }

    public static void warning(String message) {
        ChatUtils.message("\u00ef\u00bf\u00bdc[\u00ef\u00bf\u00bd6\u00ef\u00bf\u00bdlWARNING\u00ef\u00bf\u00bdc]\u00ef\u00bf\u00bdf " + message);
    }

    public static void error(String message) {
        ChatUtils.message("\u00ef\u00bf\u00bdc[\u00ef\u00bf\u00bd4\u00ef\u00bf\u00bdlERROR\u00ef\u00bf\u00bdc]\u00ef\u00bf\u00bdf " + message);
    }

    public static void success(String message) {
        ChatUtils.message("\u00ef\u00bf\u00bda[\u00ef\u00bf\u00bd2\u00ef\u00bf\u00bdlSUCCESS\u00ef\u00bf\u00bda]\u00ef\u00bf\u00bdf " + message);
    }

    public static void failure(String message) {
        ChatUtils.message("\u00ef\u00bf\u00bdc[\u00ef\u00bf\u00bd4\u00ef\u00bf\u00bdlFAILURE\u00ef\u00bf\u00bdc]\u00ef\u00bf\u00bdf " + message);
    }

    public static void cmd(String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString("\u00ef\u00bf\u00bdc[\u00ef\u00bf\u00bd6Wurst\u00ef\u00bf\u00bdc]\u00ef\u00bf\u00bdf \u00ef\u00bf\u00bd0\u00ef\u00bf\u00bdl<\u00ef\u00bf\u00bdaCMD\u00ef\u00bf\u00bd0\u00ef\u00bf\u00bdl>\u00ef\u00bf\u00bdf " + message));
    }
}

