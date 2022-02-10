//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiTextField
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.utils.base.command.builder;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.base.command.builder.CommandManager;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.Colors;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CommandInjection {
    private static String prevText = "";
    private static String prevSuggestion = "";

    public static void injectDrawScreen(GuiTextField inputField) {
        if (inputField.getText().startsWith(Command.prefix.getString()) && !inputField.getText().equals(Command.prefix.getString())) {
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            int x = inputField.x;
            int y = inputField.y;
            if (!prevText.equalsIgnoreCase(inputField.getText())) {
                prevText = inputField.getText();
                prevSuggestion = CommandManager.getSuggestionFor(prevText);
            }
            Globals.MC.fontRenderer.drawString(prevSuggestion, (float)x, (float)y, Colors.toRGBA(200, 200, 200, 150), false);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
    }

    public static void injectKeyTyped(GuiTextField inputField, int keyCode, CallbackInfo ci) {
        if ((keyCode == 28 || keyCode == 156) && inputField.getText().startsWith(Command.prefix.getString())) {
            Globals.MC.ingameGUI.getChatGUI().addToSentMessages(inputField.getText());
            Globals.MC.displayGuiScreen(null);
            CommandManager.runCommand(inputField.getText());
            ci.cancel();
        }
    }
}

